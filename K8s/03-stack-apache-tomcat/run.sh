#!/bin/bash -e

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
# Main
# -------------------------------------------------------------------------------------------------
cd images/cafeduke-apache
runCmd minikube kubectl -- create configmap apache-conf-configmap  --from-file=conf  -o yaml --dry-run=client "|" minikube kubectl -- apply -f -
cd ${BASEDIR}

runCmd minikube kubectl -- apply -f deploy.yml
runCmd minikube kubectl -- apply -f ingress.yml
