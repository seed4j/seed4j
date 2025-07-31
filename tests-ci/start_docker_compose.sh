#!/usr/bin/env bash

set -e

show_syntax() {
  echo "Usage: $0 <folder>" >&2
  exit 1
}

FOLDER_APP=$1
if [[ $FOLDER_APP == '' ]]; then
  show_syntax
fi

#-------------------------------------------------------------------------------
# Start docker container
#-------------------------------------------------------------------------------
cd "$FOLDER_APP"
if [ -a docker-compose.yml ]; then
  docker compose up -d
  echo "*** wait 20sec"
  sleep 20
  docker ps -a
else
  echo "No 'docker-compose.yml' file found — this application does not require containerized components to be launched."
fi
