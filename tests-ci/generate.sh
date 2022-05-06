#!/bin/bash

show_syntax() {
  echo "Usage: $0 <application>" >&2
  exit 1
}

if [ "$#" -ne 1 ]; then
  show_syntax
fi

application=$1

if test -f config/"$application".json; then
  filename=config/"$application".json
elif test -f tests-ci/config/"$application".json; then
  filename=tests-ci/config/"$application".json
else
  echo "The application" "$application" "does not exist!"
  exit 1
fi

callApi() {
  local api="$1"
  status_code=$(curl -o /dev/null -s -w "%{http_code}\n" \
    -X POST \
    -H "accept: */*" \
    -H "Content-Type: application/json" -d @"$filename" \
    "http://localhost:7471""$api")

  if [[ $status_code == '40'* || $status_code == '50'* ]]; then
    echo "Error when calling API:" "$status_code" "$api"
    exit 1
  fi;
}

springboot_mvc() {
  callApi "/api/inits/full"
  callApi "/api/build-tools/maven"
  callApi "/api/developer-tools/github-actions"
  callApi "/api/servers/java/base"
  callApi "/api/servers/java/jacoco-minimum-coverage"
  callApi "/api/servers/spring-boot"
  callApi "/api/servers/spring-boot/web-servers/tomcat"
  callApi "/api/servers/spring-boot/technical-tools/actuator"
}

springboot_undertow() {
  callApi "/api/inits/full"
  callApi "/api/build-tools/maven"
  callApi "/api/developer-tools/github-actions"
  callApi "/api/servers/java/base"
  callApi "/api/servers/java/jacoco-minimum-coverage"
  callApi "/api/servers/spring-boot"
  callApi "/api/servers/spring-boot/web-servers/undertow"
  callApi "/api/servers/spring-boot/technical-tools/actuator"
}

springboot() {
  callApi "/api/inits/full"
  callApi "/api/build-tools/maven"
  callApi "/api/developer-tools/github-actions"
  callApi "/api/servers/java/base"
  callApi "/api/servers/java/jacoco-minimum-coverage"
  callApi "/api/servers/spring-boot"
  callApi "/api/servers/spring-boot/technical-tools/actuator"
}

sonar_back() {
  callApi "/api/developer-tools/sonar/java-backend"
}

sonar_back_front() {
  callApi "/api/developer-tools/sonar/java-backend-and-frontend"
}

if [[ $application == 'springboot' ]]; then
  springboot_mvc
  sonar_back

elif [[ $application == 'fullstack' ]]; then
  springboot_mvc
  sonar_back_front

elif [[ $application == 'fullapp' ]]; then
  springboot_mvc
  sonar_back_front

  callApi "/api/servers/spring-boot/async"
  callApi "/api/servers/spring-boot/technical-tools/devtools"
  callApi "/api/servers/spring-boot/log-tools/aop"
  callApi "/api/servers/spring-boot/log-tools/logstash"
  callApi "/api/servers/spring-boot/banners/jhipster-v7"
  callApi "/api/servers/spring-boot/containers/docker/jib"
  callApi "/api/servers/spring-boot/containers/docker/dockerfile"
  callApi "/api/servers/java/arch"
  callApi "/api/developer-tools/codespaces"

  callApi "/api/servers/spring-boot/security-systems/jwt"
  callApi "/api/servers/spring-boot/security-systems/jwt/basic-auth"
  callApi "/api/servers/spring-boot/api-documentations/springdoc/init-with-security-jwt"
  callApi "/api/servers/spring-boot/features/dummy"

  callApi "/api/servers/spring-boot/databases/postgresql"
  callApi "/api/servers/spring-boot/features/user/postgresql"
  callApi "/api/servers/spring-boot/database-migration-tools/liquibase"
  callApi "/api/servers/spring-boot/database-migration-tools/liquibase/user"

  callApi "/api/servers/spring-boot/caches/ehcache/java-configuration"

  callApi "/api/developer-tools/frontend-maven-plugin"
  callApi "/api/clients/vue"

elif [[ $application == 'oauth2app' ]]; then
  springboot_mvc
  sonar_back

  callApi "/api/servers/spring-boot/security-systems/oauth2"
  callApi "/api/servers/spring-boot/security-systems/oauth2/account"

elif [[ $application == 'mysqlapp' ]]; then
  springboot_mvc
  sonar_back

  callApi "/api/servers/spring-boot/api-documentations/springdoc/init"
  callApi "/api/servers/spring-boot/features/dummy"

  callApi "/api/servers/spring-boot/databases/mysql"
  callApi "/api/servers/spring-boot/features/user/mysql"
  callApi "/api/servers/spring-boot/database-migration-tools/liquibase"
  callApi "/api/servers/spring-boot/database-migration-tools/liquibase/user"

  callApi "/api/servers/spring-boot/caches/ehcache/xml-configuration"

elif [[ $application == 'mariadbapp' ]]; then
  springboot_mvc
  sonar_back

  callApi "/api/servers/spring-boot/api-documentations/springdoc/init"
  callApi "/api/servers/spring-boot/features/dummy"

  callApi "/api/servers/spring-boot/databases/mariadb"
  callApi "/api/servers/spring-boot/features/user/mariadb"
  callApi "/api/servers/spring-boot/database-migration-tools/liquibase"
  callApi "/api/servers/spring-boot/database-migration-tools/liquibase/user"

  callApi "/api/servers/spring-boot/caches/ehcache/xml-configuration"

elif [[ $application == 'flywayapp' ]]; then
  springboot_mvc
  sonar_back

  callApi "/api/servers/spring-boot/databases/postgresql"
  callApi "/api/servers/spring-boot/features/user/postgresql"
  callApi "/api/servers/spring-boot/database-migration-tools/flyway"
  callApi "/api/servers/spring-boot/database-migration-tools/flyway/user"

elif [[ $application == 'undertowapp' ]]; then
  springboot_undertow
  sonar_back

  callApi "/api/servers/spring-boot/databases/mysql"
  callApi "/api/servers/spring-boot/features/user/mysql"
  callApi "/api/servers/spring-boot/database-migration-tools/flyway"
  callApi "/api/servers/spring-boot/database-migration-tools/flyway/user"

  callApi "/api/servers/spring-boot/caches/simple"

elif [[ $application == 'eurekaapp' ]]; then
  springboot_mvc
  sonar_back

  callApi "/api/servers/spring-boot/distributed-systems/spring-cloud/eureka-client"
  callApi "/api/servers/spring-boot/distributed-systems/spring-cloud/config-client"

elif [[ $application == 'consulapp' ]]; then
  springboot_undertow
  sonar_back

  callApi "/api/servers/spring-boot/distributed-systems/spring-cloud/consul"

elif [[ $application == 'mongodbapp' ]]; then
  springboot_mvc
  sonar_back

  callApi "/api/servers/spring-boot/databases/mongodb"
  callApi "/api/servers/spring-boot/features/user/mongodb"
  callApi "/api/servers/spring-boot/database-migration-tools/mongock"

elif [[ $application == 'gradleapp' ]]; then
  callApi "/api/inits/full"
  callApi "/api/build-tools/gradle"
  callApi "/api/servers/java/base"

elif [[ $application == 'angularapp' ]]; then
  springboot_mvc
  sonar_back_front

  callApi "/api/developer-tools/frontend-maven-plugin"
  callApi "/api/clients/angular"

  callApi "/api/servers/spring-boot/security-systems/jwt"
  callApi "/api/servers/spring-boot/security-systems/jwt/basic-auth"
  callApi "/api/clients/angular/jwt"

elif [[ $application == 'reactapp' ]]; then
  springboot_mvc
  sonar_back_front

  callApi "/api/developer-tools/frontend-maven-plugin"
  callApi "/api/clients/react/styles"
  callApi "/api/clients/cypress"

elif [[ $application == 'vueapp' ]]; then
  springboot_mvc
  sonar_back_front

  callApi "/api/developer-tools/frontend-maven-plugin"
  callApi "/api/clients/vue/styles"
  callApi "/api/clients/vue/stores/pinia"
  callApi "/api/clients/cypress"

elif [[ $application == 'svelteapp' ]]; then
  springboot_mvc
  sonar_back_front

  callApi "/api/developer-tools/frontend-maven-plugin"
  callApi "/api/clients/svelte/styles"

elif [[ $application == 'kafkaapp' ]]; then
  springboot_mvc
  sonar_back

  callApi "/api/servers/spring-boot/brokers/kafka"
  callApi "/api/servers/spring-boot/brokers/kafka/dummy-producer"
  callApi "/api/servers/spring-boot/brokers/kafka/akhq"

elif [[ $application == 'reactiveapp' ]]; then
  springboot
  sonar_back

  callApi "/api/servers/spring-boot/reactive-servers/netty"

else
  echo "*** Unknown configuration..."
  exit 1
fi

echo ""
cat "$filename"
echo ""
sleep 5
