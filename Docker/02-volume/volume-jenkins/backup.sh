#!/bin/bash -e
BASEDIR=$(dirname $(readlink -f ${0}))
BASENAME=$(basename ${0})

if [[ $(id -u) -ne 0 ]]
then
  echo "Run with sudo access"
  exit 1
fi

if [[ ${#} -ne 1 ]]
then
  echo "The Jenkins Home shall be backed up in the given path to .zip file"
  echo "Usage: sudo ${BASENAME} <path to .zip file>"
  exit 1
fi

pathToZip=${1}

rm -f ${pathToZip}/jenkins_home.zip 
cd /var/lib/docker/volumes
zip ${pathToZip}/jenkins_home.zip -r jenkins_home -q
