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
# Main
# -------------------------------------------------------------------------------------------------
status=$(sudo docker container ls --filter=name='duke_tomcat' | grep duke_tomcat)
if [[ "${status}" == "" ]]
then
  heading "Create container"
  runCmd sudo docker container create --name duke_tomcat -p 18801:18801 -p 17701:17701 cafeduke/tomcat:9
fi

heading "Start container"
runCmd sudo docker container start duke_tomcat

