#!/usr/bin/env bash

MIGRATION_KEYSPACE_NAME=seed4jmigration

unset scripts
declare -A scripts

function log {
  echo "[$(date)]: $*"
}

function logDebug {
  ((DEBUG_LOG)) && echo "[DEBUG][$(date)]: $*"
}

function exitWithError() {
  echo "ERROR :
        $*"
  exit 1
}

# load already executed scripts in the `scripts` global variable: dictionary[scriptName->checksum]
function loadExecutedScripts {
  local rows
  # after SELECT, removes blank lines, then column names, then the query summary at the last line
  mapfile -t rows < <(cqlsh -k $MIGRATION_KEYSPACE_NAME -e "select * from schema_version" "$CASSANDRA_CONTACT_POINT" | grep "\S" | tail -n +3 | sed '$d')
  for r in "${rows[@]}"; do
    local scriptName
    local checksum
    scriptName=$(echo "$r" | cut -d '|' -f 1 | sed s'/^[[:space:]]*//' | sed s'/[[:space:]]*$//')
    checksum=$(echo "$r" | cut -d '|' -f 2 | sed s'/^[[:space:]]*//' | sed s'/[[:space:]]*$//')
    scripts+=(["$scriptName"]="$checksum")
  done
}

function exists {
  if [ "$2" != in ]; then
    echo "Incorrect usage."
    echo "Correct usage: exists {key} in {array}"
    return 1
  fi

  eval '[ ${'"$3"'[$1]+exists} ]'
}

function checksumEquals {
  local checksum
  checksum=$(md5sum "$cqlFile" | cut -d ' ' -f 1)
  local foundChecksum=${scripts[${filename}]}

  if [[ "$checksum" == "$foundChecksum" ]]; then
    logDebug "checksum equals for $cqlFile, checksum=$checksum"
    return 0
  else
    logDebug "different checksum found for $cqlFile
        checksum=$checksum
        foundChecksum=$foundChecksum"
    return 1
  fi
}

function isExecuted {
  if exists "$filename" in scripts; then
    if checksumEquals "$cqlFile"; then
      return 0
    else
      exitWithError "$cqlFile has already been executed but has a different checksum logged in the schema_version table.
            scripts must not be changed after being executed.
            to resolve this issue you can:
            - revert the modified script to its initial state and create a new script
            OR
            - delete the script entry from the schema_version table
            "
    fi
  else
    return 1
  fi
}

function executeCqlScript {
  log "execute: $cqlFile"
  cqlsh -f "$cqlFile" "$CASSANDRA_CONTACT_POINT"

  # if execution failed
  if [ $? -ne 0 ]; then
    exitWithError "fail to apply script $filename
        stop applying database changes"
  fi
  logDebug "execution of $cqlFile succeeded"
}

function logExecutedScript {
  local duration=$1
  local checksum
  checksum=$(md5sum "$cqlFile" | cut -d ' ' -f 1)

  logDebug "save $cqlFile execution in schema_version table"
  local query="INSERT INTO schema_version (script_name, checksum, executed_by, executed_on, execution_time, status) VALUES ('$filename', '$checksum', '$USER', toTimestamp(now()), $duration, 'success');"
  cqlsh -k $MIGRATION_KEYSPACE_NAME -e "$query" "$CASSANDRA_CONTACT_POINT"
}

#usage checks
if [ -z "$1" ]; then
  echo "usage: ./execute-cql cqlFile.cql"
  exit 1
fi
if [ -z "$CASSANDRA_CONTACT_POINT" ]; then
  echo "CASSANDRA_CONTACT_POINT environment variable must be defined"
  exit 1
fi

cqlFile=$1
filename=$(basename "$1")

loadExecutedScripts
if isExecuted; then
  logDebug "skipping $cqlFile already executed"
else
  _start=$(date +"%s")
  executeCqlScript
  _end=$(date +"%s")
  duration=$((_end - _start))
  logExecutedScript "$duration"
  log "$cqlFile executed with success in $duration seconds"
fi
