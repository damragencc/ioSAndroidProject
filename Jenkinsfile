pipeline {
	agent any

    environment {
		PATH = "/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/opt/homebrew/bin:${env.PATH}"
        APP_PATH = "${WORKSPACE}/app/build/outputs/apk/debug/app-debug.apk"
        DEVICE_NAME = "Damra iPhone'u"
        PLATFORM_NAME = "iOS"
        PLATFORM_VERSION = "16.7.10"
        UDID = "67eabf5127c7b0384912007f02ffe655f7de75a3"
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

        stage('Start Appium Server') {
			steps {
				script {
					sh '''
                        # Varsa çalışan Appium instance'ını durdur
                        pkill -f appium || true

                        # Appium server'ı başlat
                        nohup appium > appium.log 2>&1 &

                        # Server'ın başlaması için bekle
                        sleep 5

                        # Appium'un çalıştığını kontrol et
                        ps aux | grep appium
                    '''
                }
            }
        }

        stage('Prepare Test Environment') {
			steps {
				script {
					sh '''
                        # iOS cihaz bağlantısını kontrol et
                        idevice_id -l | grep ${UDID} || (echo "iOS cihaz bağlı değil!" && exit 1)

                        # WDA durumunu kontrol et
                        ideviceinstaller -l | grep WebDriverAgentRunner || echo "WDA yüklü değil"
                    '''
                }
            }
        }

        stage('Run iOS Tests') {
			steps {
				script {
					sh '''
                        cd ${WORKSPACE}

                        # Maven test komutunu workspace'de çalıştır
                        mvn clean test \
                            -Dplatform.name=${PLATFORM_NAME} \
                            -Dplatform.version=${PLATFORM_VERSION} \
                            -Ddevice.name="${DEVICE_NAME}" \
                            -Dudid=${UDID} \
                            -Dapp.path=${APP_PATH} \
                            -Dsurefire.reportsDirectory=${WORKSPACE}/target/surefire-reports
                    '''
                }
            }
        }
    }

    post {
		always {
			script {
				// Appium server'ı durdur
                sh 'pkill -f appium || true'

                // Test raporlarının yerini kontrol et
                sh 'ls -la ${WORKSPACE}/target/surefire-reports || true'

                // Test raporlarını her durumda arşivle
                archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: true
                junit allowEmptyResults: true, testResults: 'target/surefire-reports/**/*.xml'

                // Test log dosyalarını da arşivle
                archiveArtifacts artifacts: 'appium.log', allowEmptyArchive: true
            }
        }
        success {
			echo "Pipeline başarıyla tamamlandı!"
            script {
				echo "Test raporları başarıyla oluşturuldu"
                // Rapor dosyalarının içeriğini göster
                sh 'cat target/surefire-reports/*.txt || true'
            }
        }
        failure {
			echo "Pipeline'da hata oluştu!"
        }
    }
}