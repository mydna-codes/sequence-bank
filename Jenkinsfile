pipeline {

    agent any

    environment {
        kubeconfigId = "kubernetes-kubeconfig"
        nexusCredentials = "mydnacodes-nexus-user"
        dockerCredentials = "mydnacodes-docker-user"
        dockerImageTag = "mydnacodes/sequence-bank"
        dockerImage = ""
        version = ""
        commitAuthor = ""
    }

    tools {
        maven "mvn-3.6"
        jdk "jdk-11"
    }

    stages {
        stage("Cloning git") {
            steps {
                git branch: "master",
                    credentialsId: "github",
                    url: "https://github.com/mydna-codes/sequence-bank.git"
            }
        }
        stage("Set environment variables") {
            steps {
                script {
                    pom = readMavenPom file:"pom.xml"
                    version = pom.version
                    sh "git --no-pager show -s --format='%ae' > COMMIT_INFO"
                    commitAuthor = readFile("COMMIT_INFO").trim()
                }
            }
        }
        stage("Packaging application") {
            steps {
                sh "mvn clean package -DskipTests=true"
            }
        }
        stage("Building docker image") {
            steps {
                script {
                    dockerImage = docker.build dockerImageTag
                }
            }
        }
        stage("Publishing docker image") {
            steps {
                script {
                    docker.withRegistry("", dockerCredentials) {
                        dockerImage.push("$version")
                        dockerImage.push("latest")
                    }
                }
            }
        }
        stage("Cleaning up docker images") {
            steps {
                sh "docker rmi $dockerImageTag:$version"
                sh "docker rmi $dockerImageTag:latest"
            }
        }
        stage("Deploying libraries") {
           steps {
               withCredentials([usernamePassword(credentialsId: nexusCredentials, passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
                   sh "mvn clean deploy -DskipTests=true -Dnexus.username=$USERNAME -Dnexus.password=$PASSWORD --settings .ci/settings.xml -P lib"
               }
           }
        }
        stage("Deploying application") {
            steps {
                withKubeConfig([credentialsId: kubeconfigId]) {
                    sh 'kubectl get pods'
                }
            }
        }
        stage("Cleaning maven packages") {
            steps {
                sh "mvn clean"
            }
        }
    }
    post {
       success {
           slackSend (color: '#57BA57', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' from ${commitAuthor} (${env.BUILD_URL})")
       }
       failure {
           slackSend (color: '#BD0808', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' from ${commitAuthor} (${env.BUILD_URL})")
       }
    }
}