#!/bin/bash

# -------------------------------------------------------------------------------------------------
# Functions
# -------------------------------------------------------------------------------------------------
BASEDIR=$(dirname $(readlink -f ${0}))
BASENAME=$(basename ${0})

##
# Display as heading
#
# Arguments:
#   mesg - Messages to be displayed
##
function heading {
  echo ""
  echo "---------------------------------------------------------------------------------------------------"
  for mesg in "$@"
  do
    echo "${mesg}"
  done
  echo "---------------------------------------------------------------------------------------------------"
}

##
# Run command
##
function runCmd {
  echo ""
  echo "Executing: $@"
  eval "$@"
}

# -------------------------------------------------------------------------------------------------
# Parse Arg
# -------------------------------------------------------------------------------------------------
if [[ $# -ne 1 ]]
then
  echo "Usage: ${BASENAME} <target (local|minikube)>"
  exit 1
fi
target="${1}"

# -------------------------------------------------------------------------------------------------
# Main
# -------------------------------------------------------------------------------------------------

if [[ "${target}" == "minikube" ]]
then
  heading "Point docker to minikube docker"
  eval $(minikube -p minikube docker-env)
fi

heading "Build Image"
runCmd sudo -E docker image build --tag cafeduke/tomcat:9 .
