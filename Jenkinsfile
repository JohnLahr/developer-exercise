#!usr/bin/env groovy
import groovy.transform.Field

@Field List<String> NODE_LABELS =  ["linux", "jdk1.8", "maven"]
@Field String CHECKSTYLE_DOWNLOAD_URL = "https://github.com/checkstyle/checkstyle/releases/download/checkstyle-9.0/checkstyle-9.0-all.jar"

/// PIPELINE ///////////////////////////////////////////////////////////////////

pipeline {
  agent {
    node {
      label NODE_LABELS.join('&&')
    }
  }

  options {
    buildDiscarder(logRotator(numToKeepStr: '5'))
    skipDefaultCheckout()
    timeout(time: 5, unit: 'MINUTES')
  }

  stages {
    stage('Initialize') {
      steps {
        script {
          // Clean the workspace
          sh 'git clean -d --force -x'

          // Default checkout
          checkout scm
        }
      }
    }

    stage('Lint') {
      steps {
        script {
          def checkstyleJar = CHECKSTYLE_DOWNLOAD_URL.split('/').last()

          // Verify filename
          try {
            assert (checkstyleJar ==~ /.+\.jar$/)
          }
          catch (AssertionError e) {
            die "Expected to download CheckStyle JAR file, but got $checkstyleJar instead"
          }

          // Download CheckStyle JAR
          def downloadCmd = [
            "curl",
            "--URL $CHECKSTYLE_DOWNLOAD_URL",
            "--location",
            "--output lib/$checkstyleJar",
            "> /dev/null 2>&1"
          ]
          def downloadCmdErrMsg = 'Failed to download CheckStyle JAR'

          cmd(downloadCmd, downloadCmdErrMsg)

          // Run linter
          def lintCmd = [
              "java",
              "-jar lib/$checkstyleJar",
              "-c checkstyle.xml",
              "src/"
          ]
          def lintCmdErrMsg = 'One or more linting errors were found'

          cmd(lintCmd, lintCmdErrMsg)
        }
      }
    }

    stage('Build') {
      steps {
        script {
          def buildCmd       = 'mvn clean install -ff'
          def buildCmdErrMsg = 'Project failed to build'

          cmd(buildCmd, buildCmdErrMsg)
        }
      }
    }

    stage('Test') {
      failFast false
      parallel {
        stage('Unit Tests') {
          steps {
            script {
              def unitTestCmd       = 'mvn test'
              def unitTestCmdErrMsg = 'One or more unit tests failed'

              cmd(unitTestCmd, unitTestCmdErrMsg)
            }
          }
        }
        stage('Functional Tests') {
          steps {
            script {
              def functionalTestCmd       = 'mvn exec:java -Dexec.mainClass=assignment.Main'
              def functionalTestCmdErrMsg = 'One or more functional tests failed'

              cmd(functionalTestCmd, functionalTestCmdErrMsg)
            }
          }
        }
      }
    }
  }
}

/// END PIPELINE ///////////////////////////////////////////////////////////////

/**
 * Safely execute a shell command with proper error handling.
 *
 * @param commandParts Command to be executed (as List to be assembled as space-delimited String)
 * @param errorMessage Error message to be displayed in the event of a failure
 */
void cmd(List<String> commandParts, String errorMessage) {
  def command = commandParts.join(' ') as String
  cmd(command, errorMessage)
}


/**
 * Safely execute a shell command with proper error handling.
 *
 * @param command      Command to be executed
 * @param errorMessage Error message to be displayed in the event of a failure
 */
void cmd(String command, String errorMessage) {
  try {
    sh command
  }
  catch (AssertionError e) {
    die errorMessage
  }
  catch (hudson.AbortException e) {
    die errorMessage
  }
  catch (Exception e) {
    die e
  }
}

/**
 * Execute a controlled, immediate pipeline failure.
 *
 * @param s Message to be displayed as the build failure
 */
void die(String s) {
  currentBuild.result = 'FAILURE'
  error s
}


/**
 * Execute a controlled, immediate pipeline failure.
 *
 * @param s Throwable cause from which a message will be displayed as the build failure
 */
void die(Throwable t) {
  currentBuild.result = 'FAILURE'
  echo getStackTrace(t)
  error t.message
}

/**
 * Prints a formatted stack trace to the Jenkins console.
 * NOTE: This is only necessary because the native Throwable.printStackTrace()
 * gets swallowed by the Jenkins console.
 *
 * @param t Throwable from which a stack trace will be printed
 * @return Stack trace formatted as a multiline String
 */
String getStackTrace(Throwable t) {
  StringWriter sw = new StringWriter()
  PrintWriter pw = new PrintWriter(sw)

  t.printStackTrace(pw)

  sw.toString()
}
