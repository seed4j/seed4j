#!/bin/bash

set -e

show_syntax() {
  echo "Usage: $0 <folder>" >&2
  exit 1
}

JHI_FOLDER_APP=$1
if [[ $JHI_FOLDER_APP == '' ]]; then
  show_syntax
fi

#-------------------------------------------------------------------------------
# Start docker container
#-------------------------------------------------------------------------------
cd "$JHI_FOLDER_APP"
if [ -a src/main/docker/keycloak.yml ]; then
  docker compose -f src/main/docker/keycloak.yml up -d
  echo "*** wait 20sec"
  sleep 20
fi
if [ -a src/main/docker/kafka.yml ]; then
  docker compose -f src/main/docker/kafka.yml up -d
fi
if [ -a src/main/docker/consul.yml ]; then
  docker compose -f src/main/docker/consul.yml up -d
fi
if [ -a src/main/docker/mongodb.yml ]; then
  docker compose -f src/main/docker/mongodb.yml up -d
fi
if [ -a src/main/docker/cassandra.yml ]; then
  docker compose -f src/main/docker/cassandra.yml up -d
fi
if [ -a src/main/docker/mysql.yml ]; then
  docker compose -f src/main/docker/mysql.yml up -d
fi
if [ -a src/main/docker/postgresql.yml ]; then
  docker compose -f src/main/docker/postgresql.yml up -d
fi
if [ -a src/main/docker/mariadb.yml ]; then
  docker compose -f src/main/docker/mariadb.yml up -d
fi
if [ -a src/main/docker/neo4j.yml ]; then
  docker compose -f src/main/docker/neo4j.yml up -d
fi
if [ -a src/main/docker/mssql.yml ]; then
  docker compose -f src/main/docker/mssql.yml up -d
  echo "*** wait 40sec"
  sleep 40
fi
if [ -a src/main/docker/jhipster-registry.yml ]; then
  docker compose -f src/main/docker/jhipster-registry.yml up -d
fi
echo "*** wait 20sec"
sleep 20
docker ps -a
