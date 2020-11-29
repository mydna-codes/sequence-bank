pipeline {

    agent any

    environment {
        // Global variables
        KUBERNETES_CREDENTIALS = "k8s-kubeconfig"
        NEXUS_CREDENTIALS      = "mydnacodes-nexus-user"
        DOCKER_CREDENTIALS     = "mydnacodes-docker-user"
        // Local variables
        DOCKER_IMAGE_TAG       = "mydnacodes/sequence-bank"
        DOCKER_IMAGE_VERSION   = ""
        DOCKER_IMAGE           = ""
        COMMIT_AUTHOR          = ""
        COMMIT_MESSAGE         = ""
        COMMIT_BRANCH          = ""
    }

    tools {
        maven "mvn-3.6"
        jdk "jdk-11"
    }

    stages {
        stage("Clone git") {
            steps {
                COMMIT_BRANCH = "${GIT_BRANCH.split("/")[1]}"

                git branch: COMMIT_BRANCH,
                    credentialsId: "github",
                    url: "https://github.com/mydna-codes/sequence-bank.git"
            }
        }
        stage("Set environment variables") {
            steps {
                script {
                    pom                  = readMavenPom file:"pom.xml"
                    DOCKER_IMAGE_VERSION = pom.version

                    COMMIT_AUTHOR  = sh "git show -s --format='%cn <%ce>')"
                    COMMIT_MESSAGE = sh "git show -s --format='%s')"
                }
            }
        }
        stage("Package application") {
            steps {
                sh "mvn clean package -DskipTests=true"
            }
        }
        stage("Build docker image") {
            steps {
                script {
                    dockerImage = docker.build DOCKER_IMAGE_TAG
                }
            }
        }
        stage("Publish docker image") {
            steps {
                script {
                    docker.withRegistry("", DOCKER_CREDENTIALS) {
                        dockerImage.push("$DOCKER_IMAGE_VERSION")
                        dockerImage.push("latest")
                    }
                }
            }
        }
        stage("Clean docker images") {
            steps {
                sh "docker rmi $DOCKER_IMAGE_TAG:$DOCKER_IMAGE_VERSION"
                sh "docker rmi $DOCKER_IMAGE_TAG:latest"
            }
        }
        stage("Deploy libraries") {
           steps {
               withCredentials([usernamePassword(credentialsId: NEXUS_CREDENTIALS, passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
                   sh "mvn clean deploy -DskipTests=true -Dnexus.username=$USERNAME -Dnexus.password=$PASSWORD --settings .ci/settings.xml -P lib"
               }
           }
        }
        stage("Clean maven packages") {
            steps {
                sh "mvn clean"
            }
        }
        stage("Deploy application") {
            steps {
                sh "sed 's+{{IMAGE_NAME}}+$DOCKER_IMAGE_TAG:$DOCKER_IMAGE_VERSION+g' .kube/sequence-bank.yaml > .kube/sequence-bank.yaml"

                withKubeConfig([credentialsId: KUBERNETES_CREDENTIALS]) {
                    sh "kubectl apply -f .kube/"
                }
            }
        }
    }
    post {
       success {
           slackSend (color: '#57BA57', message: "[<${env.BUILD_URL}|Build ${env.BUILD_NUMBER}>] *SUCCESSFUL*\n\nJob: *${env.JOB_NAME}*\nBranch: ${COMMIT_BRANCH}\nCommit author:${COMMIT_AUTHOR}\nCommit message: ${COMMIT_MESSAGE}")
       }
       failure {
           slackSend (color: '#BD0808', message: "[<${env.BUILD_URL}|Build ${env.BUILD_NUMBER}>] *FAILED*\n\nJob: *${env.JOB_NAME}*\nBranch: ${COMMIT_BRANCH}\nCommit author:${COMMIT_AUTHOR}\nCommit message: ${COMMIT_MESSAGE}")
       }
    }
}