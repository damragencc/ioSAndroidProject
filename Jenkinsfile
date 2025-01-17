pipeline {
	agent any

    triggers {
		pollSCM('H/5 * * * *')
    }

    stages {
		stage('Test Jenkins') {
			steps {
				echo "Jenkins çalışıyor!"
                sh 'pwd'
                sh 'ls -la'
            }
        }
    }

    post {
		success {
			echo "Pipeline başarıyla tamamlandı!"
        }
        failure {
			echo "Pipeline'da hata oluştu!"
        }
    }
}