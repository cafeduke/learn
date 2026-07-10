#!/bin/bash -e

# --------------------------------------------------------------------------------
# Functions
# --------------------------------------------------------------------------------
BASEDIR=$(dirname $(readlink -f ${0}))
BASENAME=$(basename ${0})

function h1 {
  local mesg=${1}
  echo ""
  echo "--------------------------------------------------------------------------------"
  echo "$mesg"
  echo "--------------------------------------------------------------------------------"
}

function assertJSONRespOK {
  local fileRespCode="${1}.resp.out"
  local fileOut="${1}.out"

  source ${fileRespCode}
  if [[ "${ResponseCode}" == "200" ]]
  then
    cat ${fileOut} | jq
  else
    echo "ERROR: Request failed with response code ${ResponseCode}"
    cat ${fileOut}
    exit 1
  fi
}

function dieUsage {
  echo "Usage: bash ${0} <username>"
  exit 1
}

# --------------------------------------------------------------------------------
# Variables
# --------------------------------------------------------------------------------
KEYCLOAK_HOST="localhost:8181"
KEYCLOAK_REALM_NAME="duke-realm"
KEYCLOAK_CLIENT_ID="angular-dukecart-client"
PASSWORD="welcome1"

T_WORK=$(readlink -f $BASEDIR/../work)
TEST_PREFIX="test.dukecart.purchase"

# --------------------------------------------------------------------------------
# Parse Arg
# --------------------------------------------------------------------------------

if [[ ${#} -eq 1 && ("${1}" == "-h" || "${1}" == "--help") ]]
then
  dieUsage
fi

if [[ ${#} -ne 2 ]]
then
  dieUsage
fi

USERNAME="${1}"
PATH_JSON="${2}"

# --------------------------------------------------------------------------------
# Main
# --------------------------------------------------------------------------------

# Clean
mkdir -p $T_WORK
cd $T_WORK
rm -f ${TEST_PREFIX}.*

# Get access token
# ----------------
h1 "Get access token"
jget-quiet \
  -u "http://${KEYCLOAK_HOST}/realms/${KEYCLOAK_REALM_NAME}/protocol/openid-connect/token" \
  -hdr "Content-Type: application/x-www-form-urlencoded" \
  -P -pb "grant_type=password&client_id=${KEYCLOAK_CLIENT_ID}&username=${USERNAME}&password=${PASSWORD}" \
  -o ${TEST_PREFIX}.token.out \
  -rco ${TEST_PREFIX}.token.resp.out

assertJSONRespOK ${TEST_PREFIX}.token
ACCESS_TOKEN=$(cat ${TEST_PREFIX}.token.out | jq -r ".access_token")

# Request secure resource
# -----------------------
h1 "Do purchase"
jget-quiet \
  -u "http://localhost:9090/dukecart/checkout/purchase" \
  -hdr "Content-Type: application/json" \
  -hdr "Authorization: Bearer $ACCESS_TOKEN" \
  -P -pbf ${PATH_JSON} \
  -sh \
  -ho ${TEST_PREFIX}.p1.head.out \
  -o ${TEST_PREFIX}.p1.out \
  -rco ${TEST_PREFIX}.p1.resp.out

assertJSONRespOK ${TEST_PREFIX}.p1



