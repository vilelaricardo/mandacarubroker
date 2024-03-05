pipeline {
    agent any

    tools {
        maven '3.9.6'
    }
    stages {
        stage('Checkout'){
            steps{
                checkout scmGit(branches: [[name: 'develop']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/pauloherbt/mandacarubroker-fixed']])
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage('Tests'){
            steps{
                sh 'mvn test'
            }
        }
        stage('Static Code Analysis'){
            steps{
                sh 'mvn checkstyle:checkstyle'
            }
        }
    }
}
