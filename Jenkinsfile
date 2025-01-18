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
		// Her 5 dakikada bir kontrol et
        pollSCM('*/5 * * * *')
    }

    stages {
		stage('Check Dependencies') {
			steps {
				script {
					sh '''
                        node -v
                        npm -v
                        appium -v
                    '''
                }
            }
        }

        stage('Start Appium Server') {
			steps {
				script {
					sh '''
                        pkill -f appium || true
                        nohup appium > appium.log 2>&1 &
                        sleep 5
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
			echo "Tests completed successfullyy!"
        }
        failure {
			echo "Tests failed!"
        }
    }
}