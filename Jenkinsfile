pipeline {
    tools {
        maven 'jenkins-mvn'
    }
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/pradeep883/Counter-app.git']]])
            }
        }
        stage('Build Jar') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Docker Image Build') {
            steps {
                sh 'docker build -t counter-app .'
            }
        }
        stage('Push Docker Image to ECR') {
            steps {
                withAWS(credentials: 'pradeep-aws-credentials', region: 'us-east-1') {
                    sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 459074096472.dkr.ecr.us-east-1.amazonaws.com'
                    sh 'docker tag counter-app:latest 459074096472.dkr.ecr.us-east-1.amazonaws.com/counter-app:latest'
                    sh 'docker push 459074096472.dkr.ecr.us-east-1.amazonaws.com/counter-app:latest'
                }
            }
        }
        stage('Integrate Jenkins with EKS Cluster and Deploy App') {
            steps {
                withAWS(credentials: 'pradeep-aws-credentials', region: 'us-east-1') {
                  script {
                    sh ('aws eks update-kubeconfig --name counter-app-cluster --region us-east-1')
                    sh "kubectl apply -f deployment.yaml"
                }
                }
        }
    }
    }
}