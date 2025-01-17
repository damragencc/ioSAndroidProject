pipeline {
	agent any

    environment {
		// iOS testleri için gerekli ortam değişkenleri
        APP_PATH = "/path/to/your/app.ipa"   // iOS uygulamanızın yolu
        DEVICE_NAME = "Damra iPhone’u"       // Cihaz adı
        PLATFORM_NAME = "iOS"                // Platform adı (iOS)
        PLATFORM_VERSION = "16.7.10"         // iOS sürümü
        UDID = "67eabf5127c7b0384912007f02ffe655f7de75a3" // Cihazın UDID'si
    }

    stages {
		stage('Checkout') {
			steps {
				// GitHub reposundan kodu çekiyoruz
                git 'https://github.com/damragencc/ioSAndroidProject.git'
            }
        }

        stage('Install Dependencies') {
			steps {
				// Node.js ve Appium’un kurulumunu yapıyoruz
                script {
					sh '''
                    npm install -g appium
                    npm install -g appium-doctor
                    appium-doctor
                    '''
                }
            }
        }

        stage('Run Appium iOS Tests') {
			steps {
				// Maven veya Gradle ile Java testlerini çalıştırıyoruz
                script {
					sh '''
                    mvn clean install
                    mvn test
                    '''
                }
            }
        }

        stage('Post Build Actions') {
			steps {
				// Test sonuçlarını raporluyoruz
                junit '**/target/test-*.xml'
            }
        }
    }

    post {
		always {
			// Build tamamlandığında yapılacak işlemler
            echo "Testler tamamlandı!"
        }
    }
}