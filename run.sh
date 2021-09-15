#!/bin/bash
#================================================================
# HEADER
#================================================================
#% SYNOPSIS
#+    ${SCRIPT_NAME} <STAGE> [args ...]
#%
#% DESCRIPTION
#%    Script to manually run the Jenkinsfile pipeline without a
#%    Jenkins environment.
#%
#% OPTIONS
#%    -h, --help                    Print this help
#%    -v, --verbose                 Write verbose log output
#%    -vvv, --trace                 Write trace log output
#%
#% EXAMPLES
#%    ${SCRIPT_NAME}
#%    ${SCRIPT_NAME} test -v
#%    ${SCRIPT_NAME} build --trace
#%
#================================================================
#- IMPLEMENTATION
#-    version         ${SCRIPT_NAME} (yggdrasil.io) 1.0.0
#-    author          John Lahr
#-
#================================================================
#  HISTORY
#     2021/09/14 : johnlahr : Script creation
#
#================================================================
#  DEBUG OPTION
#    set -n  # Uncomment to check your syntax without execution
#    set -x  # Uncomment to debug this shell script
#
#================================================================
# END_OF_HEADER
#================================================================

#-----------------------------------------------------------------------------#
set -o pipefail
#-----------------------------------------------------------------------------#
TRACE=false
VERBOSE=false

MVN_VERBOSITY_SUFFIX=""

CHECKSTYLE_DOWNLOAD_URL="https://github.com/checkstyle/checkstyle/releases/download/checkstyle-9.0/checkstyle-9.0-all.jar"
STAGE=""
VALID_STAGES=("all" "initialize" "lint" "build" "test" "unittest" "functionaltest")
#-----------------------------------------------------------------------------#
COLOR_NONE='\033[0m'

COLOR_BLACK='\033[0;30m'
COLOR_RED='\033[0;31m'
COLOR_GREEN='\033[0;32m'
COLOR_ORANGE='\033[0;33m'
COLOR_BLUE='\033[0;34m'
COLOR_PURPLE='\033[0;35m'
COLOR_CYAN='\033[0;36m'
COLOR_LIGHTGRAY='\033[0;37m'

COLOR_DARKGRAY='\033[1;30m'
COLOR_LIGHTRED='\033[1;31m'
COLOR_LIGHTGREEN='\033[1;32m'
COLOR_YELLOW='\033[1;33m'
COLOR_LIGHTBLUE='\033[1;34m'
COLOR_LIGHTPURPLE='\033[1;35m'
COLOR_LIGHTCYAN='\033[1;36m'
COLOR_WHITE='\033[1;37m'

function logError() {
    set +e
    echo -e "[${COLOR_RED}ERROR${COLOR_NONE}] $1"
    set -e
}

function logInfo() {
    set +e
    echo -e "[${COLOR_GREEN}INFO${COLOR_NONE}] $1"
    set -e
}

function logTrace() {
    set +e
    [ $TRACE == "true" ] && echo -e "[${COLOR_DARKGRAY}TRACE${COLOR_NONE}] $1"
    set -e
}

function logVerbose() {
    set +e
    [ $VERBOSE == "true" ] && echo -e "[${COLOR_LIGHTGRAY}VERBOSE${COLOR_NONE}] $1"
    set -e
}

function logWarning() {
    set +e
    echo -e "[${COLOR_ORANGE}WARNING${COLOR_NONE}] $1"
    set -e
}
#-----------------------------------------------------------------------------#

#-----------------------------------------------------------------------------#
# Displays help menu
#-----------------------------------------------------------------------------#
function show-help() {
    cat << EOF
USAGE:
    $(basename $0) <STAGE> [args...]

DESCRIPTION:
    Script to manually run the Jenkinsfile pipeline without a
    Jenkins environment.

OPTIONS
    -h, --help                    Print this help
    -v, --verbose                 Write verbose log output
    -vvv, --trace                 Write trace log output

EXAMPLES
    $(basename $0)               # Run all stages
    $(basename $0) test -v       # Run only the test stage, enable verbose logs
    $(basename $0) build --trace # Run only the build stage, enable trace logs
EOF
}

#-----------------------------------------------------------------------------#
# Initializes global variables
#
# @example
#   initializeGlobalVariables $*
#
# @arg $* All variables provided to the surrounding script file
#-----------------------------------------------------------------------------#
function initializeGlobalVariables() {
    set -e

    for arg in $*; do
        [ $arg == "-v" ] && VERBOSE="true"
        [ $arg == "--verbose" ] && VERBOSE="true"
        [ $arg == "-vvv" ] && VERBOSE="true" && TRACE="true"
        [ $arg == "--trace" ] && VERBOSE="true" && TRACE="true"
    done

    [[ $VERBOSE == "false" ]] && MVN_VERBOSITY_SUFFIX=" -q"
    [[ $TRACE == "true" ]] && MVN_VERBOSITY_SUFFIX=" -X"
    [[ $TRACE == "true" ]] && set -x

    if [[ $# -eq 3 || $# -eq 2 && $VERBOSE -eq "false" ]]; then
        STAGE=$1
    else
        STAGE="all"
    fi

    logVerbose "Verbose logging is ${COLOR_GREEN}ENABLED${COLOR_NONE}"
    logTrace "Trace logging is ${COLOR_GREEN}ENABLED${COLOR_NONE}"

    logVerbose "STAGE: ${COLOR_WHITE}$STAGE${COLOR_NONE}"

    set +e
}

#-----------------------------------------------------------------------------#
# Runs the Initialize stage of the Jenkinsfile pipeline.
#
# @example
#   initialize
#-----------------------------------------------------------------------------#
function initialize() {
    logVerbose "Executing Initialize stage"
    logInfo "Cleaning workspace"
    git clean -d --force -x$QUIET_SUFFIX
    logVerbose "Cleaning of workspace ${COLOR_LIGHTGREEN}SUCCESSFUL${COLOR_NONE}"
}

#-----------------------------------------------------------------------------#
# Runs the Lint stage of the Jenkinsfile pipeline.
#
# @example
#   lint
#-----------------------------------------------------------------------------#
function lint() {
    logVerbose "Executing Lint stage"
    local checkstyleJar="${CHECKSTYLE_DOWNLOAD_URL##*/}"
    logTrace "checkstyleJar: ${COLOR_WHITE}$checkstyleJar${COLOR_NONE}"

    if [[ ! $checkstyleJar =~ .*".jar" ]]; then
        logError "Expected to download CheckStyle JAR file, but got $checkstyleJar instead"
        exit 1
    fi

    logVerbose "Downloading CheckStyle JAR file"
    curl --URL $CHECKSTYLE_DOWNLOAD_URL --location --output lib/$checkstyleJar > /dev/null 2>&1
    logVerbose "Download of CheckStyle JAR file ${COLOR_LIGHTGREEN}SUCCESSFUL${COLOR_NONE}"

    logInfo "Performing linting checks"
    java -jar lib/$checkstyleJar -c checkstyle.xml src/
    logInfo "Project linting ${COLOR_LIGHTGREEN}PASSED${COLOR_NONE}"
}

#-----------------------------------------------------------------------------#
# Runs the Build stage of the Jenkinsfile pipeline.
#
# @example
#   build
#-----------------------------------------------------------------------------#
function build() {
    logVerbose "Executing Build stage"
    logInfo "Building project"
    logTrace "Maven build command: mvn clean install -ff$MVN_VERBOSITY_SUFFIX"
    mvn clean install -ff$MVN_VERBOSITY_SUFFIX
    [[ $VERBOSE -eq "false" ]] && logInfo "Build ${COLOR_LIGHTGREEN}SUCCESSFUL${COLOR_NONE}"
}

#-----------------------------------------------------------------------------#
# Runs the Unit Tests stage of the Jenkinsfile pipeline.
#
# @example
#   unitTest
#-----------------------------------------------------------------------------#
function unitTest() {
    logVerbose "Executing Unit Tests stage"
    logInfo "Executing unit tests"
    mvn test$MVN_VERBOSITY_SUFFIX
    [[ $VERBOSE -eq "false" ]] && logInfo "Unit tests ${COLOR_LIGHTGREEN}PASSED${COLOR_NONE}"
}

#-----------------------------------------------------------------------------#
# Runs the Functional Tests stage of the Jenkinsfile pipeline.
#
# @example
#   functionalTest
#-----------------------------------------------------------------------------#
function functionalTest() {
    logVerbose "Executing Functional Tests stage"
    logInfo "Executing functional tests"
    mvn exec:java -Dexec.mainClass=assignment.Main$MVN_VERBOSITY_SUFFIX
    [[ $VERBOSE -eq "false" ]] && logInfo "Functional tests ${COLOR_LIGHTGREEN}PASSED${COLOR_NONE}"
}
#-----------------------------------------------------------------------------#

#-----------------------------------------------------------------------------#
case $1 in
    help|-h|--help|-help)
        show-help
        ;;
    initialize)
        initializeGlobalVariables $*
        initialize
        ;;
    lint)
        initializeGlobalVariables $*
        lint
        ;;
    build)
        initializeGlobalVariables $*
        build
        ;;
    test)
        initializeGlobalVariables $*
        unittest
        functionalTest
        ;;
    unittest)
        initializeGlobalVariables $*
        unitTest
        ;;
    functionaltest)
        initializeGlobalVariables $*
        functionalTest
        ;;
    ""|-v|--verbose|-vvv|--trace)
        initializeGlobalVariables $*
        initialize
        lint
        build
        unitTest
        functionalTest
        logInfo "Overall pipeline ${COLOR_LIGHTGREEN}SUCCESSFUL${COLOR_NONE}"
        ;;
    *)
        logError "The specified stage is not valid: [${COLOR_WHITE}${1}${COLOR_NONE}]"
        exit 1
    ;;
esac

exit 0
#-----------------------------------------------------------------------------#
