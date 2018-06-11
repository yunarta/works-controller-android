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
        stage('Select') {
            parallel {
                stage('Checkout') {
                    when {
                        expression {
                            notIntegration()
                        }
                    }

                    steps {
                        checkout scm
                        seedReset()
                    }
                }

                stage('Integrate') {
                    when {
                        expression {
                            isIntegration()
                        }
                    }

                    steps {
                        echo "Execute integration"
                        stopUnless(isStartedBy("upstream"))
                    }
                }
            }
        }

        stage("Test & Analyze") {
            when {
                expression {
                    notIntegration() && notRelease()
                }
            }

            options {
                retry(2)
            }

            steps {
                seedGrow("test")

                echo "Build for test and analyze"
                sh '''./gradlew detektCheck -q'''
                androidEmulator command: "start", avd: "android-19"

                sh """echo "Execute test"
                        $ANDROID_HOME/platform-tools/adb install -r test-app/test-butler-app-1.3.2.apk
                        ./gradlew cleanTest jacocoTestReport -PignoreFailures=${
                    seedEval("test", [1: "true", "else": "false"])
                }"""
            }
            post {
                always {
                    androidEmulator command: "stop"
                }
            }
        }

        stage("Publish Test & Analyze") {
            when {
                expression {
                    notIntegration() && notRelease()
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

        stage("Build") {
            when {
                expression {
                    notIntegration()
                }
            }

            steps {
                echo "Build"
                sh './gradlew worksGeneratePublication'
            }
        }

        stage("Compare") {
            parallel {
                stage("Snapshot") {
                    when {
                        expression {
                            notIntegration() && notRelease()
                        }
                    }

                    steps {
                        echo "Compare snapshot"
                        compareArtifact("snapshot", "integrate/snapshot")
                    }
                }

                stage("Release") {
                    when {
                        expression {
                            notIntegration() && isRelease()
                        }
                    }

                    steps {
                        echo "Compare release"
                        compareArtifact("release", "integrate/release")
                    }
                }
            }
        }

        stage("Publish") {
            parallel {
                stage("Snapshot") {
                    when {
                        expression {
                            notIntegration() && notRelease() && doPublish()
                        }
                    }

                    steps {
                        echo "Publishing snapshot"
                        publish("snapshot")
                    }
                }

                stage("Release") {
                    when {
                        expression {
                            notIntegration() && isRelease() && doPublish()
                        }
                    }

                    steps {
                        echo "Publishing release"
                        publish("release")
                    }
                }
            }
        }
    }

    post {
        success {
            notifyDownstream()
        }
    }
}

def compareArtifact(String repo, String job) {
    bintrayDownload([
            dir       : ".compare",
            credential: "mobilesolutionworks.jfrog.org",
            pkg       : readProperties(file: 'library/module.properties'),
            repo      : "mobilesolutionworks/${repo}",
            src       : "library/build/libs"
    ])

    def update = bintrayCompare([
            dir       : ".compare",
            credential: "mobilesolutionworks.jfrog.org",
            pkg       : readProperties(file: 'library/module.properties'),
            repo      : "mobilesolutionworks/${repo}",
            src       : "library/build/libs"
    ])

    if (fileExists(".notify")) {
        sh "rm .notify"
    }

    if (update) {
        writeFile file: ".notify", text: job
    }
}

def doPublish() {
    return fileExists(".notify")
}

def notifyDownstream() {
    if (fileExists(".notify")) {

        def job = readFile file: ".notify"
        def encodedJob = java.net.URLEncoder.encode(job, "UTF-8")

        build job: "github/yunarta/works-controller-android/${encodedJob}", propagate: false, wait: false
    }
}

def publish(String repo) {
    def who = env.JENKINS_WHO ?: "anon"
    if (who == "works") {
        bintrayPublish([
                credential: "mobilesolutionworks.jfrog.org",
                pkg       : readProperties(file: 'library/module.properties'),
                repo      : "mobilesolutionworks/${repo}",
                src       : "library/build/libs"
        ])
    }
}

def codeCoverage() {
    withCredentials([[$class: 'StringBinding', credentialsId: "codecov-token", variable: "CODECOV_TOKEN"]]) {
        sh "curl -s https://codecov.io/bash | bash -s - -f build/reports/jacocoTestReport/jacocoTestReport.xml"
    }
}
