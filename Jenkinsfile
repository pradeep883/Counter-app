pipeline {
    tools {
        maven 'Maven3'
    }
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: '<GIT_REPO_URL>']]])
            }
        }
        stage('Build Jar') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Docker Image Build') {
            steps {
                sh 'docker build -t <IMAGE_NAME> .'
            }
        }
        stage('Push Docker Image to ECR') {
            steps {
                withAWS(credentials: 'pradeep-aws-credentials', region: 'us-east-1') {
                    sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 459074096472.dkr.ecr.us-east-1.amazonaws.com'
                    sh 'docker build -t counter-app .'
                    sh 'docker tag counter-app:latest 459074096472.dkr.ecr.us-east-1.amazonaws.com/counter-app:latest'
                    sh 'docker push 459074096472.dkr.ecr.us-east-1.amazonaws.com/counter-app:latest'
                }
            }
        }
        stage('Integrate Jenkins with EKS Cluster and Deploy App') {
            steps {
                withAWS(credentials: 'pradeep-aws-credentials', region: 'us-east-1') {
                  script {
                    sh ('aws eks update-kubeconfig --name <EKS_CLUSTER_NAME> --region us-east-1')
                    sh "kubectl apply -f <K8S_DEPLOY_FILE>.yaml"
                }
                }
        }
    }
    }
}