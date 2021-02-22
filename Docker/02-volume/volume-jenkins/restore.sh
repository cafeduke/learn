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
  echo "The Jenkins Home shall be restored from the given path to .zip file"
  echo "Usage: sudo ${BASENAME} <path to .zip file>"
  exit 1
fi

pathToZip=$(readlink -f ${1})
unzip -q ${pathToZip} -d /var/lib/docker/volumes
chown 1000:1000 -R /var/lib/docker/volumes/jenkins_home
echo "Restored file ${pathToZip} at /var/lib/docker/volumes"
