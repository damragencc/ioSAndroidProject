pipeline {
	agent any

    environment {
		PATH = "/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/opt/homebrew/bin:${env.PATH}"
        APP_PATH = "${WORKSPACE}/app/build/outputs/apk/debug/app-debug.apk"
        DEVICE_NAME = "Damra iPhone'u"
        PLATFORM_NAME = "iOS"
        PLATFORM_VERSION = "16.7.10"
        UDID = "67eabf5127c7b0384912007f02ffe655f7de75a3"
        APPIUM_PORT = "4723"
    }

    triggers {
		pollSCM('* * * * *')
    }

    stages {
		stage('Check Dependencies') {
			steps {
				script {
					sh '''
                        node -v
                        npm -v
                        appium -v

                        echo "iOS cihaz kontrolü yapılıyor..."
                        DEVICE_ID=$(idevice_id -l)
                        if [ -z "$DEVICE_ID" ]; then
                            echo "HATA: iOS cihaz bulunamadı!"
                            exit 1
                        else
                            echo "iOS cihaz bulundu: $DEVICE_ID"
                        fi
                    '''
                }
            }
        }

        stage('Run iOS Tests') {
			steps {
				script {
					sh '''
                        cd ${WORKSPACE}
                        mvn clean test -Dplatform.name=${PLATFORM_NAME} \
                            -Dplatform.version=${PLATFORM_VERSION} \
                            -Ddevice.name="${DEVICE_NAME}" \
                            -Dudid=${UDID} \
                            -Dapp.path=${APP_PATH}
                    '''
                }
            }
        }
    }

    post {
		always {
			script {
				// Test raporlarını arşivle
                archiveArtifacts artifacts: '**/target/surefire-reports/**/*', allowEmptyArchive: true
                junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true
            }
        }
        success {
			echo "Tests completed successfully!"
        }
        failure {
			echo "Tests faileddddddddddd!"
        }
    }
}