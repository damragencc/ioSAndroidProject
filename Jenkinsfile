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

        stage('Check Node.js') {
			steps {
				script {
					try {
						sh 'node --version'
                        echo "Node.js yüklü!"
                    } catch (err) {
						error "Node.js yüklü değil! Lütfen Node.js'i yükleyin."
                    }
                }
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