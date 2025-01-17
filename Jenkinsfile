pipeline {
	agent any

    environment {
		PATH = "/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/opt/homebrew/bin:${env.PATH}"
    }

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
						sh 'which node || true'
                        sh '/opt/homebrew/bin/node --version || /usr/local/bin/node --version'
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