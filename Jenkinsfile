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
                        idevice_id -l
                        ideviceinstaller -l
                    '''
                }
            }
        }

        stage('Start Appium Server') {
			steps {
				script {
					sh '''
                        # Önce varsa çalışan Appium'u durdur
                        pkill -f appium || true

                        # Appium loglarını temizle
                        rm -f appium.log

                        # Appium'u başlat
                        nohup appium -p ${APPIUM_PORT} --log appium.log --log-timestamp --local-timezone > appium.log 2>&1 &

                        # Server'ın başlamasını bekle
                        echo "Appium server başlatılıyor..."
                        for i in {1..30}; do
                            if curl -s http://localhost:${APPIUM_PORT}/wd/hub/status > /dev/null; then
                                echo "Appium server başarıyla başlatıldı!"
                                break
                            fi
                            if [ $i -eq 30 ]; then
                                echo "Appium server başlatılamadı!"
                                exit 1
                            fi
                            echo "Bekleniyor... ($i/30)"
                            sleep 2
                        done
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
				// Appium'u durdur
                sh 'pkill -f appium || true'

                // Test raporlarını arşivle
                archiveArtifacts artifacts: '**/target/surefire-reports/**/*', allowEmptyArchive: true
                junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true

                // Appium loglarını arşivle
                archiveArtifacts artifacts: 'appium.log', allowEmptyArchive: true
            }
        }
        success {
			echo "Tests completed successfully!"
        }
        failure {
			echo "Tests failed!"
        }
    }
}