pipeline {
    agent any
//     options {
//         timeout(time: 1, unit: 'HOURS')
//     }
//     environment {
//         SOURCECODE_JENKINS_CREDENTIAL_ID = 'ci_cd'
//         SOURCE_CODE_URL = 'https://github.com/project-matching/project-matching-backend.git'
//         RELEASE_BRANCH = 'main'
//     }
    stages {
        stage('Init') {
            steps {
                echo 'clear'
//                 sh 'docker stop $(docker ps -aq)'
//                 sh 'docker rm $(docker ps -aq)'
//                 deleteDir()
            }
        }

//         stage('clone') {
//             steps {
//                 git url: "$SOURCE_CODE_URL",
//                     branch: "$RELEASE_BRANCH",
//                     credentialsId: "$SOURCECODE_JENKINS_CREDENTIAL_ID"
//                 sh "ls -al"
//             }
//         }

        stage('backend dockerizing') {
            steps {
                sh "pwd"
                sh "chmod +x gradlew"
                sh "./gradlew clean"
                sh "./gradlew bootJar"

                sh "docker build -t backend ."

            }
        }

        stage('deploy') {
            steps {
                sh '''
                  docker run -d -p 9090:9090 backend
                '''
            }
        }
    }
}