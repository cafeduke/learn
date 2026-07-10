#!/bin/bash

# --------------------------------------------------------------------------------
# Functions
# --------------------------------------------------------------------------------
BASEDIR=$(dirname $(readlink -f ${0}))
BASENAME=$(basename ${0})

source "${BASEDIR}/util.sh"

function usage {
  echo "Test CRUD on customer profile"
  echo ""
  echo "Usage: ${BASENAME}"
  exit 0
}

# --------------------------------------------------------------------------------
# Variables
# --------------------------------------------------------------------------------
KEYCLOAK_HOST="localhost:8181"
KEYCLOAK_REALM_NAME="duke-realm"
KEYCLOAK_CLIENT_ID="angular-dukecart-client"
PASSWORD="welcome1"

TEST_ROOT=$(readlink -f $BASEDIR/../)
DIR_WORK="${TEST_ROOT}/work"
DIR_DATA="${TEST_ROOT}/data"

TEST_PREFIX="test_customer_profile"
# --------------------------------------------------------------------------------
# Parse Arg
# --------------------------------------------------------------------------------

if [[ ${#} -eq 1 && ("${1}" == "-h" || "${1}" == "--help") ]]
then
  dieUsage
fi

PROFILE_USERNAME="dukeapple"
PROFILE_PREFIX="p1"
PROFILE_V1_JSON="${TEST_ROOT}/data/customer-profile.${PROFILE_PREFIX}.v1.json"
PROFILE_V2_JSON="${TEST_ROOT}/data/customer-profile.${PROFILE_PREFIX}.v2.json"

# --------------------------------------------------------------------------------
# Main
# --------------------------------------------------------------------------------

# Clean
mkdir -p ${DIR_WORK}
cd ${DIR_WORK}
rm -f ${TEST_PREFIX}.*

# Get access token
# ----------------
h1 "Get access token"
jget-quiet \
  -u "http://${KEYCLOAK_HOST}/realms/${KEYCLOAK_REALM_NAME}/protocol/openid-connect/token" \
  -hdr "Content-Type: application/x-www-form-urlencoded" \
  -P -pb "grant_type=password&client_id=${KEYCLOAK_CLIENT_ID}&username=${PROFILE_USERNAME}&password=${PASSWORD}" \
  -o ${TEST_PREFIX}.token.out \
  -rco ${TEST_PREFIX}.token.resp.out

assertJSONRespOK ${TEST_PREFIX}.token
ACCESS_TOKEN=$(cat ${TEST_PREFIX}.token.out | jq -r ".access_token")

# Create Profile
# --------------
# h1 "Create profile ${PROFILE_PREFIX}.v1"
# jget-quiet \
#   -u "http://localhost:9090/dukecart/customer-profile/create" \
#   -hdr "Content-Type: application/json" \
#   -hdr "Authorization: Bearer $ACCESS_TOKEN" \
#   -P -pbf ${PROFILE_V1_JSON} \
#   -sh \
#   -ho ${TEST_PREFIX}.create.${PROFILE_PREFIX}.v1.head.out \
#   -o ${TEST_PREFIX}.create.${PROFILE_PREFIX}.v1.out \
#   -rco ${TEST_PREFIX}.create.${PROFILE_PREFIX}.v1.resp.out

# assertJSONRespOK ${TEST_PREFIX}.create.${PROFILE_PREFIX}.v1


# Read Profile
# --------------
h1 "Read profile ${PROFILE_PREFIX}.v1"
jget-quiet \
  -u "http://localhost:9090/dukecart/customer/"


# Update Profile
# --------------
# h1 "Update profile ${PROFILE_PREFIX}.v1 to ${PROFILE_PREFIX}.v2"
# jget-quiet \
#   -u "http://localhost:9090/dukecart/customer-profile/update" \
#   -hdr "Content-Type: application/json" \
#   -hdr "Authorization: Bearer $ACCESS_TOKEN" \
#   -P -pbf ${PROFILE_V2_JSON} \
#   -sh \
#   -ho ${TEST_PREFIX}.update.${PROFILE_PREFIX}.v2.head.out \
#   -o ${TEST_PREFIX}.update.${PROFILE_PREFIX}.v2.out \
#   -rco ${TEST_PREFIX}.update.${PROFILE_PREFIX}.v2.resp.out

# assertJSONRespOK ${TEST_PREFIX}.update.${PROFILE_PREFIX}.v2


# Delete Profile
# --------------
# h1 "Delete profile ${PROFILE_PREFIX}.v1"
# jget-quiet \
#   -u "http://localhost:9090/dukecart/customer-profile/delete/${PROFILE_USERNAME}" \
#   -hdr "Content-Type: application/json" \
#   -hdr "Authorization: Bearer $ACCESS_TOKEN" \
#   -delete \
#   -sh \
#   -ho ${TEST_PREFIX}.delete.${PROFILE_PREFIX}.v1.head.out \
#   -o ${TEST_PREFIX}.delete.${PROFILE_PREFIX}.v1.out \
#   -rco ${TEST_PREFIX}.delete.${PROFILE_PREFIX}.v1.resp.out

# assertJSONRespOK ${TEST_PREFIX}.delete.${PROFILE_PREFIX}.v1
