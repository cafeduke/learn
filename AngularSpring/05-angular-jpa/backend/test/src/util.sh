# --------------------------------------------------------------------------------
# Variables
# --------------------------------------------------------------------------------
KEYCLOAK_HOST="localhost:8181"
KEYCLOAK_REALM_NAME="duke-realm"
KEYCLOAK_CLIENT_ID="angular-dukecart-client"
PASSWORD="welcome1"

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
  mesg=$([[ "$ResponseCode" == "200" ]] && echo "[PASS] Request passed." || echo "[FAIL] Request failed.")
  echo "$mesg"
  cat ${fileOut} | jq

  if [[ "$ResponseCode" != "200" ]]
  then
    die "Assertion FAILED. ResponseCode not OK."
  fi
}

function die {
  mesg=${1}
  echo "[FATAL] $mesg"
  exit 1
}

function dieUsage {
  usage
  exit 1
}
