pipeline {
	agent any

    stages {
		stage('Test') {
			steps {
				echo "Repository bağlantısı test ediliyor..."
                sh 'pwd'
                sh 'ls -la'
            }
        }
    }

    post {
		always {
			echo "Pipeline tamamlandı!"
        }
    }
}