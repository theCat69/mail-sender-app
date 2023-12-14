pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh "mvn clean install -DskipTests"
      }
    }
    stage('UT') {
      steps {
        sh "mvn test -P ut"
      }
    }
    stage('IT') {
      steps {
        sh "mvn verify -P it"
      }
    }
    stage('Deployment') {
      steps {
        script {
          echo "Git branch : ${env.GIT_BRANCH}"
          if(env.GIT_BRANCH == "main") {
            echo "Deploying ..."
            echo "Deploy success"
          } else {
            echo "No deployment for this build"
          }
        }
      }
    }
  }
}