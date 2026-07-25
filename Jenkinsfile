pipeline {
    agent { label 'agent1' }
    
    tools {
        // Configured in Manage Jenkins -> Global Tool Configuration
        maven 'maven-3'
        jdk 'jdk-17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Compile & Package') {
            steps {
                echo 'Injecting secure database configurations...'
                sh 'cp /jenkins_config/persistence.properties src/main/resources/persistence.properties'
                echo 'Building Java WAR package...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                echo 'Deploying WAR package to Tomcat webapps...'
                // Copy target/wsarachai.war directly to the shared deploy_webapps directory
                sh 'cp target/wsarachai.war /deploy_webapps/wsarachai.war'
                echo 'Deployment complete!'
            }
        }
    }
}
