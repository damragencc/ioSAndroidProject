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

        stage('Check Dependencies') {
			steps {
				script {
					try {
						// Node.js kontrolü
                        sh 'which node || true'
                        sh '/opt/homebrew/bin/node --version || /usr/local/bin/node --version'
                        echo "Node.js yüklü!"

                        // npm kontrolü
                        sh 'which npm || true'
                        sh '/opt/homebrew/bin/npm --version || /usr/local/bin/npm --version'
                        echo "npm yüklü!"

                        // Appium kontrolü
                        sh 'which appium || true'
                        sh 'appium --version || npm list -g appium'
                        echo "Appium yüklü!"
                    } catch (err) {
						error "Bağımlılık kontrolünde hata: ${err.message}"
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