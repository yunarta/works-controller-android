buildCount = env.DEFAULT_HISTORY_COUNT ?: "5"

pipeline {
    agent {
        node {
            label 'android && emulator'
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

        stage("Coverage, Analyze and Test") {
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
                        ./gradlew cleanTest jacocoTestReport -PignoreFailures=${seedEval("test", [1: "true", "else": "false"])}"""
            }

            post {
                always {
                    androidEmulator command: "stop"
                }
            }
        }

        stage("Publish CAT") {
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
                    notIntegration() && notFeatureBranch()
                }
            }

            parallel {
                stage("Snapshot") {
                    when {
                        expression {
                            notRelease()
                        }
                    }

                    steps {
                        updateVersion()
                        sh './gradlew clean worksGeneratePublication'
                    }
                }

                stage("Release") {
                    when {
                        expression {
                            isRelease()
                        }
                    }

                    steps {
                        androidEmulator command: "start", avd: "android-19"
                        sh """$ANDROID_HOME/platform-tools/adb install -r test-app/test-butler-app-1.3.2.apk
                        ./gradlew cleanTest connectedAndroidTest worksGeneratePublication -PignoreFailures=false"""
                    }

                    post {
                        always {
                            androidEmulator command: "stop"
                        }
                    }
                }
            }
        }

        stage("Compare") {
            when {
                expression {
                    notIntegration() && notFeatureBranch()
                }
            }


            parallel {
                stage("Snapshot") {
                    when {
                        expression {
                            notRelease()
                        }
                    }

                    steps {
                        echo "Compare snapshot"
                        compareArtifact("snapshot", "integrate/snapshot", false)
                    }
                }

                stage("Release") {
                    when {
                        expression {
                            isRelease()
                        }
                    }

                    steps {
                        echo "Compare release"
                        compareArtifact("release", "integrate/release", true)
                    }
                }
            }
        }

        stage("Publish") {
            when {
                expression {
                    doPublish()
                }
            }

            parallel {
                stage("Snapshot") {
                    when {
                        expression {
                            notIntegration() && notRelease()
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
                            notIntegration() && isRelease()
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

def updateVersion() {
    bintrayDownloadMatches repository: "mobilesolutionworks/snapshot",
            packageInfo: readYaml(file: 'library/module.yaml'),
            credential: "mobilesolutionworks.jfrog.org"

    def properties = readYaml(file: 'library/module.yaml')
    def incremented = versionIncrementQualifier()
    if (incremented != null) {
        properties.version = incremented
    } else {
        properties.version = properties.version + "-BUILD-1"
    }

    sh "rm library/module.yaml"
    writeYaml file: 'library/module.yaml', data: properties
}


def compareArtifact(String repo, String job, boolean download) {
    if (download) {
        bintrayDownloadMatches repository: "mobilesolutionworks/${repo}",
                packageInfo: readYaml(file: 'library/module.yaml'),
                credential: "mobilesolutionworks.jfrog.org"
    }

    def same = bintrayCompare repository: "mobilesolutionworks/${repo}",
            packageInfo: readYaml(file: 'library/module.yaml'),
            credential: "mobilesolutionworks.jfrog.org",
            path: "library/build/libs"

    if (fileExists(".jenkins/notify")) {
        sh "rm .jenkins/notify"
    }

    if (same) {
        echo "Artifact output is identical, no integration needed"
    } else {
        writeFile file: ".jenkins/notify", text: job
    }
}

def doPublish() {
    return fileExists(".jenkins/notify")
}

def notifyDownstream() {
    if (fileExists(".jenkins/notify")) {

        def job = readFile file: ".jenkins/notify"
        def encodedJob = java.net.URLEncoder.encode(job, "UTF-8")

        build job: "github/yunarta/works-controller-android/${encodedJob}", propagate: false, wait: false
    }
}

def publish(String repo) {
    def who = env.JENKINS_WHO ?: "anon"
    if (who == "works") {
        bintrayPublish([
                credential: "mobilesolutionworks.jfrog.org",
                pkg       : readYaml(file: 'library/module.yaml'),
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
