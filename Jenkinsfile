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
    }

    tools {
        maven "mvn-3.6"
        jdk "jdk-11"
    }

    stages {
        stage("Set environment variables") {
            steps {
                script {
                    pom                  = readMavenPom file:"pom.xml"
                    DOCKER_IMAGE_VERSION = pom.version
                    COMMIT_MESSAGE       = sh script: "git show -s --pretty='%s'", returnStdout: true
                    COMMIT_AUTHOR        = sh script: "git show -s --pretty='%cn <%ce>'", returnStdout: true
                    COMMIT_AUTHOR        = COMMIT_AUTHOR.trim()
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
        stage("Prepare deployments") {
            steps {
                script {
                    def deploymentConfig = readYaml file: ".ci/deployment-config.yaml"
                    def environment      = ""

                    if (env.GIT_BRANCH.equals("prod") || env.GIT_BRANCH.equals("origin/prod")) {
                        environment = deploymentConfig.environments.prod
                    } else {
                        environment = deploymentConfig.environments.dev
                    }

                    sh """ \
                    sed -e 's+{{IMAGE_NAME}}+$DOCKER_IMAGE_TAG:$DOCKER_IMAGE_VERSION+g' \
                        -e 's+{{SERVICE_PORT}}+$environment.servicePort+g' \
                        -e 's+{{NAMESPACE}}+$environment.namespace+g' \
                        .kube/sequence-bank.yaml > .kube/sequence-bank.tmp
                    """
                    sh "mv -f .kube/sequence-bank.tmp .kube/sequence-bank.yaml"
                    sh "cat .kube/sequence-bank.yaml"

                    sh """ \
                    sed -e 's+{{DATABASE_PORT}}+$environment.dbPort+g' \
                        -e 's+{{NAMESPACE}}+$environment.namespace+g' \
                        .kube/sequence-bank-db.yaml > .kube/sequence-bank-db.tmp
                    """
                    sh "mv -f .kube/sequence-bank-db.tmp .kube/sequence-bank-db.yaml"
                    sh "cat .kube/sequence-bank-db.yaml"
                }
            }
        }
        stage("Deploy application") {
            steps {

                withKubeConfig([credentialsId: KUBERNETES_CREDENTIALS]) {
                    sh "kubectl apply -f .kube/"
                }
            }
        }
    }
    post {
       success {
           slackSend (color: '#57BA57',
                      message: """\
           [<${env.BUILD_URL}|Build ${env.BUILD_NUMBER}>] *SUCCESSFUL*\n\n \
           Job: *${env.JOB_NAME}*\n\n \
           Branch: ${GIT_BRANCH}\n \
           Author: ${COMMIT_AUTHOR}\n \
           Message: ${COMMIT_MESSAGE}
           """)
       }
       failure {
           slackSend (color: '#BD0808',
                      message: """\
           [<${env.BUILD_URL}|Build ${env.BUILD_NUMBER}>] *FAILED*\n\n \
           Job: *${env.JOB_NAME}*\n\n \
           Branch: ${GIT_BRANCH}\n \
           Author: ${COMMIT_AUTHOR}\n \
           Message: ${COMMIT_MESSAGE}
           """)
       }
    }
}