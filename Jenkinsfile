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
                        # Tüm Appium process'lerini bul ve durdur
                        ps aux | grep appium | grep -v grep | awk '{print $2}' | xargs kill -9 || true

                        # Port'u kontrol et ve temizle
                        lsof -ti:${APPIUM_PORT} | xargs kill -9 || true

                        # Biraz bekle
                        sleep 2

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

                        # Server'ın çalıştığını son bir kez kontrol et
                        if ! curl -s http://localhost:${APPIUM_PORT}/wd/hub/status > /dev/null; then
                            echo "Appium server çalışmıyor!"
                            exit 1
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