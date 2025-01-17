pipeline {
	agent any

    triggers {
		pollSCM('H/5 * * * *')
    }

    environment {
		// Global PATH'i güncelle
        PATH = "/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:${env.PATH}"
        // Proje yolu için workspace kullan
        PROJECT_ROOT = "${WORKSPACE}"
        APP_PATH = "${PROJECT_ROOT}/app/build/outputs/apk/debug/app-debug.apk"
        DEVICE_NAME = "Damra iPhone'u"
        PLATFORM_NAME = "iOS"
        PLATFORM_VERSION = "16.7.10"
        UDID = "67eabf5127c7b0384912007f02ffe655f7de75a3"
    }

    stages {
		stage('Setup Environment') {
			steps {
				script {
					// Node.js ve npm'in yüklü olduğundan emin ol
                    sh '''
                        node -v
                        npm -v

                        # Appium'u global olarak yükle (eğer yüklü değilse)
                        npm list -g appium || npm install -g appium
                    '''
                }
            }
        }

        stage('Start Appium Server') {
			steps {
				script {
					sh '''
                        # Çalışan Appium process'lerini kontrol et ve durdur
                        pkill -f appium || true

                        # Appium server'ı başlat
                        nohup appium > appium.log 2>&1 &

                        # Server'ın başlaması için bekle
                        sleep 10

                        # Appium'un çalıştığını kontrol et
                        ps aux | grep appium
                    '''
                }
            }
        }

        stage('Run iOS Tests') {
			steps {
				script {
					sh '''
                        # Proje dizininde olduğumuzdan emin ol
                        cd ${PROJECT_ROOT}

                        # Maven test
                        mvn clean test
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

                // Test raporlarını arşivle (eğer varsa)
                archiveArtifacts artifacts: '**/target/surefire-reports/*.xml', allowEmptyArchive: true
                junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
            }
        }
        success {
			echo "iOS testleri başarıyla tamamlandı!"
        }
        failure {
			echo "iOS testlerinde hata oluştu!"
        }
    }
}