JENKINS_SCRIPTS = 'jenkins-scripts'

pipeline {
  agent any
  environment {
    APP_PATH = '/home/javarunner/mail-sender-app'
  }
  stages {
    stage('Build') {
      steps {
        withMaven(mavenLocalRepo: '.repository') {
          sh "mvn -B -DskipTests clean package -P prod"
        }
      }
    }
    stage('UT') {
      steps {
        withMaven(mavenLocalRepo: '.repository') {
          sh "mvn test -P ut"
        }
      }
    }
    stage('IT') {
      steps {
        withMaven(mavenLocalRepo: '.repository') {
          sh "mvn test -P it"
        }
      }
    }
    stage('Deployment') {
      steps {
        script {
          echo "Git branch : ${env.GIT_BRANCH}"
          if (env.GIT_BRANCH == "main") {
            echo "Deploying ..."
            sh "chmod -R 775 ${JENKINS_SCRIPTS}"
            sh "./${JENKINS_SCRIPTS}/stop.sh"
            sh "./${JENKINS_SCRIPTS}/deploy.sh"
            sh "./${JENKINS_SCRIPTS}/start.sh"
            echo "Deploy success"
          } else {
            echo "No deployment for this build"
          }
        }
      }
    }
  }
}