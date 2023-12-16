pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        withMaven(mavenLocalRepo: '.repository') {
          sh "mvn -B -DskipTests clean package"
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
          sh "mvn verify -P it"
        }
      }
    }
    stage('Deployment') {
      steps {
        script {
          echo "Git branch : ${env.GIT_BRANCH}"
          if (env.GIT_BRANCH == "main") {
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