pipeline {
	agent any

    tools {
		maven 'Maven 3.9.6'
        jdk 'JDK 17'
    }

    stages {
		stage('Build') {
			steps {
				echo 'Building the Java application...'
                sh 'mvn clean install'
            }
        }

        stage('Test') {
			steps {
				echo 'Running tests...'
                sh 'mvn test'
            }
        }
    }

    post {
		success {
			echo 'Pipeline başarıyla tamamlandı!'
        }
        failure {
			echo 'Pipeline başarısız oldu!'
        }
    }
}