buildCount = env.DEFAULT_HISTORY_COUNT ?: "5"

pipeline {
    agent {
        node {
            label 'android'
        }
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: buildCount))
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage("Build") {
            parallel {
                stage("Test & Analyze") {
                    when {
                        not {
                            branch "release/*"
                        }
                    }
                    options {
                        retry(2)
                    }
                    steps {
                        echo "Build for test and analyze"
                        sh '''./gradlew detektCheck -q'''
                        androidEmulator command: "start", avd: "android-19"
                        
                        sh '''echo "Execute test"
                        wget https://dl.bintray.com/linkedin/maven/com/linkedin/testbutler/test-butler-app/1.3.2/test-butler-app-1.3.2.apk -O test-butler-app.apk
                        $ANDROID_HOME/platform-tools/adb install -r test-butler-app.apk
                        ./gradlew cleanTest jacocoTestReport -PignoreFailures=true'''
                    }
                    post {
                        always {
                            androidEmulator command: "stop"
                        }
                    }
                }

                stage("Release") {
                    options {
                        retry(2)
                    }
                    when { branch 'release/*' }
                    steps {
                        echo "Build for release"

                        sh './gradlew clean test worksCreatePublication -PignoreFailures=false'
                    }
                }
            }
        }

        stage("Publish") {
            parallel {
                stage("Test & Analyze") {
                    when {
                        not {
                            branch "release/*"
                        }
                    }
                    steps {
                        echo "Publishing test and analyze result"

                        jacoco execPattern: 'build/jacoco/*.exec', classPattern: 'library/build/tmp/kotlin-classes/debug', sourcePattern: ''
                        junit allowEmptyResults: true, testResults: '**/androidTest-results/connected/**/*.xml,**/test-results/**/*.xml'
                        checkstyle canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/detekt-report.xml', unHealthy: ''

                        codeCoverage()
                    }
                }

                stage("Release") {
                    when { branch 'release/*' }
                    steps {
                        echo "Publishing release"

                        // publish()
                    }
                }
            }
        }
    }
}

def publish() {
    def who = env.JENKINS_WHO ?: "anon"
    if (who == "works") {
        bintrayPublish([
                credential: "mobilesolutionworks.jfrog.org",
                pkg       : readJSON(file: 'works-publish/module.json'),
                repo      : "mobilesolutionworks/release",
                src       : "works-publish/build/libs"
        ])
    }
}

def codeCoverage() {
    withCredentials([[$class: 'StringBinding', credentialsId: "codecov-token", variable: "CODECOV_TOKEN"]]) {
        sh "curl -s https://codecov.io/bash | bash -s - -f build/reports/jacocoTestReport/jacocoTestReport.xml"
    }
}
