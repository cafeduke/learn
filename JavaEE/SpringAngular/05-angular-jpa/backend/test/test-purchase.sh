#!/bin/bash -e

# --------------------------------------------------------------------------------
# Functions
# --------------------------------------------------------------------------------

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

# --------------------------------------------------------------------------------
# Parse Arg
# --------------------------------------------------------------------------------

if [[ ${#} -ne 1 ]]
then
  dieUsage
fi

USERNAME="${1}" 

# --------------------------------------------------------------------------------
# Main
# --------------------------------------------------------------------------------


rm -f file.*

# Get access token
# ----------------
h1 "Get access token"
jget-quiet \
  -u "http://${KEYCLOAK_HOST}/realms/${KEYCLOAK_REALM_NAME}/protocol/openid-connect/token" \
  -hdr "Content-Type: application/x-www-form-urlencoded" \
  -P -pb "grant_type=password&client_id=${KEYCLOAK_CLIENT_ID}&username=${USERNAME}&password=${PASSWORD}" \
  -o file.token.out \
  -rco file.token.resp.out 

assertJSONRespOK file.token
ACCESS_TOKEN=$(cat file.token.out | jq -r ".access_token")

# Request secure resource
# -----------------------
h1 "Do purchase"
jget-quiet \
  -u "http://localhost:9090/dukecart/checkout/purchase" \
  -hdr "Content-Type: application/json" \
  -hdr "Authorization: Bearer $ACCESS_TOKEN" \
  -P -pbf purchase.json \
  -sh \
  -ho file.purchase.head.out \
  -o file.purchase.out \
  -rco file.purchase.resp.out

assertJSONRespOK file.purchase
  


