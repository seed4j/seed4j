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

if [[ $filename == 'full-default' ]]; then
  callApi "/api/projects/init"
  callApi "/api/build-tools/maven"
  callApi "/api/servers/java/base"
  callApi "/api/servers/java/jacoco-minimum-coverage"
  callApi "/api/servers/spring-boot"

  callApi "/api/servers/spring-boot/async"
  callApi "/api/servers/spring-boot/devtools"
  callApi "/api/servers/spring-boot/logging/aop"
  callApi "/api/servers/spring-boot/logging/logstash"
  callApi "/api/servers/spring-boot/banner/jhipster-v7"
  callApi "/api/servers/sonar/java-backend-and-frontend"
  callApi "/api/servers/spring-boot/docker/jib"

  callApi "/api/servers/spring-boot/mvc/web/tomcat"
  callApi "/api/servers/spring-boot/mvc/web/actuator"
  callApi "/api/servers/spring-boot/mvc/security/jwt"
  callApi "/api/servers/spring-boot/mvc/security/jwt/basic-auth"
  callApi "/api/servers/spring-boot/mvc/springdoc/init-with-security-jwt"
  callApi "/api/servers/spring-boot/mvc/dummy"

  callApi "/api/servers/spring-boot/databases/postgresql"
  callApi "/api/servers/spring-boot/user/postgresql"
  callApi "/api/servers/spring-boot/databases/migration/liquibase/init"
  callApi "/api/servers/spring-boot/databases/migration/liquibase/user"

  callApi "/api/servers/spring-boot/cache/ehcache/java-configuration"

  callApi "/api/frontend-maven-plugin"
  callApi "/api/vite/vue"
  callApi "/api/vite/vue/styled"

elif [[ $filename == 'tomcat-mysql-ehcachexml' ]]; then
  callApi "/api/projects/init"
  callApi "/api/build-tools/maven"
  callApi "/api/servers/java/base"
  callApi "/api/servers/java/jacoco-minimum-coverage"
  callApi "/api/servers/spring-boot"

  callApi "/api/servers/sonar/java-backend"

  callApi "/api/servers/spring-boot/mvc/web/tomcat"
  callApi "/api/servers/spring-boot/mvc/springdoc/init"

  callApi "/api/servers/spring-boot/databases/mysql"
  callApi "/api/servers/spring-boot/databases/migration/flyway/init"
  callApi "/api/servers/spring-boot/databases/migration/flyway/user"

  callApi "/api/servers/spring-boot/mvc/security/jwt"
  callApi "/api/servers/spring-boot/mvc/security/jwt/basic-auth"
  callApi "/api/servers/spring-boot/mvc/springdoc/init-with-security-jwt"

  callApi "/api/servers/spring-boot/cache/ehcache/xml-configuration"

  callApi "/api/frontend-maven-plugin"
  callApi "/api/angular"

elif [[ $filename == 'undertow-simplecache' ]]; then
  callApi "/api/projects/init"
  callApi "/api/build-tools/maven"
  callApi "/api/servers/java/base"
  callApi "/api/servers/java/jacoco-minimum-coverage"
  callApi "/api/servers/spring-boot"

  callApi "/api/servers/spring-boot/banner/jhipster-v3"

  callApi "/api/servers/spring-boot/mvc/web/undertow"
  callApi "/api/servers/spring-boot/mvc/dummy"

  callApi "/api/servers/spring-boot/databases/mysql"
  callApi "/api/servers/spring-boot/user/mysql"
  callApi "/api/servers/spring-boot/databases/migration/liquibase/init"
  callApi "/api/servers/spring-boot/databases/migration/liquibase/user"
  callApi "/api/servers/spring-boot/cache/simple"

elif [[ $filename == 'tomcat-configserver' ]]; then
  callApi "/api/projects/init"
  callApi "/api/build-tools/maven"
  callApi "/api/servers/java/base"
  callApi "/api/servers/java/jacoco-minimum-coverage"
  callApi "/api/servers/spring-boot"

  callApi "/api/servers/spring-boot/mvc/web/tomcat"
  callApi "/api/servers/spring-boot/mvc/web/actuator"
  callApi "/api/servers/spring-boot/spring-cloud/config-client"

elif [[ $filename == 'undertow-consul' ]]; then
  callApi "/api/projects/init"
  callApi "/api/build-tools/maven"
  callApi "/api/servers/java/base"
  callApi "/api/servers/java/jacoco-minimum-coverage"
  callApi "/api/servers/spring-boot"

  callApi "/api/servers/spring-boot/mvc/web/undertow"
  callApi "/api/servers/spring-boot/mvc/web/actuator"
  callApi "/api/servers/spring-boot/spring-cloud/consul"

elif [[ $filename == 'tomcat-mongodb' ]]; then
  callApi "/api/projects/init"
  callApi "/api/build-tools/maven"
  callApi "/api/servers/java/base"
  callApi "/api/servers/java/jacoco-minimum-coverage"
  callApi "/api/servers/spring-boot"

  callApi "/api/servers/spring-boot/mvc/web/tomcat"
  callApi "/api/servers/spring-boot/mvc/web/actuator"
  callApi "/api/servers/spring-boot/databases/mongodb"

else
  echo "*** Unknown configuration..."
  exit 1
fi

echo "Waiting 5sec..."
sleep 5
