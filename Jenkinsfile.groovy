pipeline {
    agent any

    environment {
        JAVA_HOME = tool name: 'JDK 17', type: 'jdk'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm // 코드 체크아웃 (코드가 scm에 등록되어 있는지 확인하는 단계)
            }
        }

        stage('Project Build') {
            steps {
                // Java 17 설치
                tool name: 'JDK 17', type: 'jdk'

                // 프로젝트 빌드
                sh 'chmod +x gradlew'
                sh './gradlew build'
            }
        }

        stage('Docker Build & Run') {
            steps {
                // Docker Image Build
                sh 'docker build -t flint-back-docker:CD .'
                withCredentials([usernamePassword(credentialsId: 'back', passwordVariable: 'flint', usernameVariable: 'rlgus2738')]) {
                    sh 'docker login -u rlgus2738 -p tktktkfkd5!'
                }
                sh 'docker tag flint-back-docker:CD rlgus2738/flint-back-docker:CD'
                sh 'docker push rlgus2738/flint-back-docker:CD'
            }
        }

        stage('Deploy on Server') {
            steps {
                // 배포할 스크립트 파일 실행
                sh 'sudo chmod +x deploy_script.sh'
                sh './deploy_script.sh'
            }
        }
    }
    post {
        always {
            sh 'docker logout'
        }
    }
}