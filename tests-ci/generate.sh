#!/bin/bash

show_syntax() {
  echo "Usage: $0 <config>" >&2
  exit 1
}

if [ "$#" -ne 1 ]; then
  show_syntax
fi

filename=$1

callApi() {
  local api="$1"
  curl -X POST \
    -H "accept: */*" \
    -H "Content-Type: application/json" -d @config/$filename.json \
    "http://localhost:7471"$api
}

if [[ $filename == 'tomcat-postgresql-ehcache' ]]; then
  callApi "/api/projects/init"
  callApi "/api/build-tools/maven"
  callApi "/api/servers/java/base"
  callApi "/api/servers/java/jacoco-minimum-coverage"
  callApi "/api/servers/spring-boot"
  callApi "/api/servers/spring-boot/devtools"
  callApi "/api/servers/spring-boot/banner/jhipster-v7"
  callApi "/api/servers/spring-boot/mvc/web/tomcat"
  callApi "/api/servers/spring-boot/mvc/security/jwt"
  callApi "/api/servers/spring-boot/mvc/security/jwt/basic-auth"
  callApi "/api/servers/spring-boot/databases/postgresql"
  callApi "/api/servers/spring-boot/databases/migration/liquibase"
  callApi "/api/servers/spring-boot/aop/logging"
  callApi "/api/servers/spring-boot/logging/logstash/tcp"
  callApi "/api/servers/spring-boot/async"
  callApi "/api/servers/spring-boot/cache/ehcache"
  callApi "/api/servers/sonar"

elif [[ $filename == 'undertow-mysql-simplecache' ]]; then
  callApi "/api/projects/init"
  callApi "/api/build-tools/maven"
  callApi "/api/servers/java/base"
  callApi "/api/servers/java/jacoco-minimum-coverage"
  callApi "/api/servers/spring-boot"
  callApi "/api/servers/spring-boot/devtools"
  callApi "/api/servers/spring-boot/banner/jhipster-v3"
  callApi "/api/servers/spring-boot/mvc/web/undertow"
  callApi "/api/servers/spring-boot/mvc/security/jwt"
  callApi "/api/servers/spring-boot/mvc/security/jwt/basic-auth"
  callApi "/api/servers/spring-boot/databases/mysql"
  callApi "/api/servers/spring-boot/databases/migration/liquibase"
  callApi "/api/servers/spring-boot/aop/logging"
  callApi "/api/servers/spring-boot/logging/logstash/tcp"
  callApi "/api/servers/spring-boot/async"
  callApi "/api/servers/spring-boot/cache/simple"
  callApi "/api/servers/sonar"

else
  echo "*** Unknown configuration..."
  exit 1
fi

echo "Waiting 5sec..."
sleep 5
