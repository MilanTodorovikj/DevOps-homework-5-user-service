pipeline {
    agent any
    environment {
        HOST_JENKINS_HOME = 'E:/Fakultet/Master/IX semestar/DevOps/Domasni/Domasna 2/jenkins/jenkins_home'
        KUBECONFIG = '/home/jenkins/.kube/config'
    }
    stages {
        stage('Initialize Host Workspace') {
            steps {
                script {
                    def relativePath = WORKSPACE.replaceFirst("^/var/jenkins_home", "")
                    env.HOST_WORKSPACE = "${env.HOST_JENKINS_HOME}${relativePath}"
                    echo "Calculated HOST_WORKSPACE: ${env.HOST_WORKSPACE}"
                }
            }
        }

        stage('Build & Test') {
            options {
                timeout(time: 10, unit: 'MINUTES')
            }
            steps {
                sh '''
                docker run --rm \
                  -v "${HOST_WORKSPACE}:/app" \
                  -v maven-repo:/root/.m2 \
                  -v "${HOST_WORKSPACE}/.mvn/settings.xml:/root/.m2/settings.xml" \
                  -w /app \
                  maven:3.9.9-amazoncorretto-17-alpine \
                  bash -c "mvn clean test"
                '''
            }
        }

        stage('Deploy Artefact') {
            options {
                timeout(time: 10, unit: 'MINUTES')
            }
            when {
                branch 'master'
            }
            steps {
                sh '''
                docker run --rm \
                  --network domasna2_homework2  \
                  -v "${HOST_WORKSPACE}:/app" \
                  -v maven-repo:/root/.m2 \
                  -v "${HOST_WORKSPACE}/.mvn/settings.xml:/root/.m2/settings.xml" \
                  -w /app \
                  maven:3.9.9-amazoncorretto-17-alpine \
                  bash -c "mvn clean deploy"
                '''
            }
        }

        stage('Set Kubernetes Context') {
            options {
                timeout(time: 10, unit: 'MINUTES')
            }
            when {
                branch 'master'
            }
            steps {
                sh '''
                    rm -f /home/jenkins/.kube/config.lock
                    kubectl config use-context minikube
                    kubectl get nodes
                '''
            }
        }

        stage('Build Docker Image') {
            options {
                timeout(time: 10, unit: 'MINUTES')
            }
            when {
                branch 'master'
            }
            steps {
                sh 'eval $(minikube -p minikube docker-env)'
                sh 'docker build -t user-service:1.0.0 .'
            }
        }

        stage('Deploy to Minikube') {
            options {
                timeout(time: 10, unit: 'MINUTES')
            }
            when {
                branch 'master'
            }
            steps {
                sh 'kubectl apply -f k8s/user-service-deployment.yaml'
            }
        }
    }
}
