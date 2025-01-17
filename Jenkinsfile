pipeline {
	agent any

    triggers {
		pollSCM('H/5 * * * *')  // Her 5 dakikada bir kontrol et (yük dengelemeli)
    }

    environment {
		// iOS test ortamı için gerekli değişkenler - Test Webhook
        APP_PATH = "/Users/damragenc/Desktop/ioSAndroidProject/app/build/outputs/apk/debug/app-debug.apk"
        DEVICE_NAME = "Damra iPhone'u"
        PLATFORM_NAME = "iOS"
        PLATFORM_VERSION = "16.7.10"
        UDID = "67eabf5127c7b0384912007f02ffe655f7de75a3"
    }

    stages {
		stage('Start Appium Server') {
			steps {
				script {
					sh '''
                        # Eğer çalışan bir Appium server varsa durdur
                        pkill -f appium || true

                        # Appium server'ı başlat
                        appium --log appium.log &

                        # Server'ın başlaması için bekle
                        sleep 10
                    '''
                }
            }
        }

        stage('Run iOS Tests') {
			steps {
				script {
					sh '''
                        # Test klasörüne git
                        cd /Users/damragenc/Desktop/ioSAndroidProject

                        # Testleri çalıştır
                        mvn test
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

                // Test raporlarını arşivle
                archiveArtifacts artifacts: '**/target/surefire-reports/*.xml', allowEmptyArchive: true
                junit '**/target/surefire-reports/*.xml'
            }
        }
        success {
			echo "iOS testleri başarıyla tamamlandı!"
        }
        failure {
			echo "iOS testlerinde hata oluştuu!"
        }
    }
}