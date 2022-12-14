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
runCmd minikube kubectl -- create configmap htdocs-configmap --from-file=htdocs  -o yaml --dry-run=client "|" minikube kubectl -- apply -f -
runCmd minikube kubectl -- create configmap cgibin-configmap --from-file=cgi-bin -o yaml --dry-run=client "|" minikube kubectl -- apply -f -
runCmd minikube kubectl -- create configmap conf-configmap   --from-file=conf    -o yaml --dry-run=client "|" minikube kubectl -- apply -f -
runCmd minikube kubectl -- apply -f httpd.yml
