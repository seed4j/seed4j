#!/bin/bash

show_syntax() {
  echo "Usage: $0 <application>" >&2
  exit 1
}

if [ "$#" -ne 1 ]; then
  show_syntax
fi

if test -f "modulePayload.json"; then
  payloadFile="modulePayload.json"
elif test -f tests-ci/modulePayload.json; then
  payloadFile=tests-ci/modulePayload.json
fi

application=$1

applyModules() {
  for module in $@
  do
    local payload="$(sed "s/APP_NAME/$application/g" $payloadFile)"
    local api="/api/modules/$module/apply-patch"

    echo "curl -o /dev/null -s -w "%{http_code}\n" \
      -X POST \
      -H "accept: */*" \
      -H "Content-Type: application/json" \
      -d "$payload" \
      "http://localhost:7471""$api""

    local status_code=$(curl -o /dev/null -s -w "%{http_code}\n" \
      -X POST \
      -H "accept: */*" \
      -H "Content-Type: application/json" \
      -d "$payload" \
      "http://localhost:7471""$api")

    if [[ $status_code == '40'* || $status_code == '50'* ]]; then
      echo "Error when calling API:" "$status_code" "$api"
      exit 1
    fi;
  done
}

spring_boot_mvc() {
  spring_boot

  applyModules \
  "spring-boot-tomcat" \
}

spring_boot_undertow() {
  applyModules \
  "init" \
  "maven-java" \
  "github-actions" \
  "java-base" \
  "jacoco-check-min-coverage" \
  "spring-boot" \
  "spring-boot-undertow" \
  "spring-boot-actuator"
}

spring_boot() {
  applyModules \
  "init" \
  "maven-java" \
  "github-actions" \
  "java-base" \
  "jacoco-check-min-coverage" \
  "spring-boot" \
  "spring-boot-actuator"
}

sonar_back() {
  applyModules "sonar-java-backend"
}

sonar_back_front() {
  applyModules "sonar-java-backend-and-frontend"
}

cucumber_with_jwt() {
  applyModules \
  "spring-boot-jwt" \
  "spring-boot-jwt-basic-auth" \
  "springdoc-mvc-openapi" \
  "springdoc-jwt" \
  "spring-boot-cucumber" \
  "spring-boot-cucumber-jwt-authentication"
}

if [[ $application == 'spring-boot' ]]; then
  spring_boot_mvc
  sonar_back

elif [[ $application == 'fullstack' ]]; then
  spring_boot_mvc
  sonar_back_front

elif [[ $application == 'fullapp' ]]; then
  spring_boot_mvc
  sonar_back_front

  applyModules \
  "infinitest-filters" \
  "pagination-domain" \
  "rest-pagination" \
  "jpa-pagination" \
  "spring-boot-async" \
  "spring-boot-devtools" \
  "logstash" \
  "jib" \
  "dockerfile" \
  "java-archunit" \
  "github-codespaces" \
  "gitpod" \
  "java-memoizers" \
  "spring-boot-cache" \
  "caffeine-cache"

  cucumber_with_jwt

  applyModules "spring-boot-cucumber-jpa-reset"
  applyModules "application-service-hexagonal-architecture-documentation"

  applyModules "postgresql" "postgresql-dialect" "liquibase"

  applyModules \
  "dummy-feature" \
  "dummy-jpa-persistence" \
  "dummy-liquibase-changelog" \

  applyModules "ehcache-java-config"

  applyModules "frontend-maven-plugin" "vue-core"

elif [[ $application == 'oauth2app' ]]; then
  spring_boot_mvc
  sonar_back

  applyModules \
  "spring-boot-oauth2" \
  "spring-boot-oauth2-account" \
  "springdoc-mvc-openapi" \
  "springdoc-oauth2"

  applyModules \
  "spring-boot-cucumber" \
  "spring-boot-cucumber-oauth2-authentication" \
  "dummy-feature"

elif [[ $application == 'mysqlapp' ]]; then
  spring_boot_mvc
  sonar_back

  applyModules "mysql" "liquibase"

  cucumber_with_jwt

  applyModules "spring-boot-cucumber-jpa-reset"

  applyModules \
  "dummy-feature" \
  "dummy-jpa-persistence" \
  "dummy-liquibase-changelog" \

  applyModules "ehcache-xml-config"

elif [[ $application == 'mariadbapp' ]]; then
  spring_boot_mvc
  sonar_back

  applyModules "springdoc-mvc-openapi"
  applyModules "mariadb" "liquibase"
  applyModules "ehcache-xml-config"

elif [[ $application == 'mssqlapp' ]]; then
  spring_boot_mvc
  sonar_back

  applyModules "springdoc-mvc-openapi"
  applyModules "mssql"

elif [[ $application == 'flywayapp' ]]; then
  spring_boot_mvc
  sonar_back

  applyModules "postgresql" "flyway"

  cucumber_with_jwt

  applyModules "spring-boot-cucumber-jpa-reset"

  applyModules \
  "dummy-feature" \
  "dummy-jpa-persistence" \
  "dummy-postgresql-flyway-changelog" \

elif [[ $application == 'undertowapp' ]]; then
  spring_boot_undertow
  sonar_back

  applyModules \
  "mysql" \
  "flyway" \
  "flyway-mysql"

  cucumber_with_jwt
  applyModules "spring-boot-cucumber-jpa-reset"

  applyModules \
  "dummy-feature" \
  "dummy-jpa-persistence" \
  "dummy-not-postgresql-flyway-changelog" \

  applyModules "spring-boot-cache"

elif [[ $application == 'eurekaapp' ]]; then
  spring_boot_mvc
  sonar_back

  applyModules \
  "eureka-client" \
  "spring-cloud" \

elif [[ $application == 'consulapp' ]]; then
  spring_boot_undertow
  sonar_back

  applyModules "consul"

elif [[ $application == 'gatewayapp' ]]; then
  spring_boot
  sonar_back

  applyModules \
  "spring-boot-webflux-netty" \
  "eureka-client" \
  "spring-cloud" \
  "gateway"

elif [[ $application == 'mongodbapp' ]]; then
  spring_boot_mvc
  sonar_back

  applyModules "mongodb" "mongock"

  cucumber_with_jwt

  applyModules \
  "dummy-feature" \
  "dummy-mongodb-persistence"

elif [[ $application == 'neo4japp' ]]; then
  spring_boot_mvc
  sonar_back

  applyModules "neo4j" "neo4j-migrations"

  cucumber_with_jwt

elif [[ $application == 'angularapp' ]]; then
  spring_boot_mvc
  sonar_back_front

  applyModules \
  "frontend-maven-plugin" \
  "angular-core" \

  cucumber_with_jwt

  applyModules "angular-jwt" "angular-health"

elif [[ $application == 'angularoauth2app' ]]; then
  spring_boot_mvc
  sonar_back_front

  applyModules \
  "frontend-maven-plugin" \
  "angular-core" \
  "cypress"

  applyModules \
  "spring-boot-oauth2" \
  "spring-boot-oauth2-account" \
  "springdoc-mvc-openapi" \
  "springdoc-oauth2"

  applyModules "angular-oauth2"

elif [[ $application == 'reactapp' ]]; then
  spring_boot_mvc
  sonar_back_front

  applyModules \
  "frontend-maven-plugin" \
  "react-core" \
  "cypress"

  cucumber_with_jwt

  applyModules "react-jwt"

elif [[ $application == 'vueapp' ]]; then
  spring_boot_mvc
  sonar_back_front

  applyModules \
  "frontend-maven-plugin" \
  "vue-core" \
  "vue-pinia" \
  "cypress"

elif [[ $application == 'svelteapp' ]]; then
  spring_boot_mvc
  sonar_back_front

  applyModules \
  "frontend-maven-plugin" \
  "prettier" \
  "svelte-core"

elif [[ $application == 'kafkaapp' ]]; then
  spring_boot_mvc
  sonar_back

   applyModules "spring-boot-kafka" "spring-boot-kafka-akhq"

elif [[ $application == 'pulsarapp' ]]; then
  spring_boot_mvc
  sonar_back

  applyModules "spring-boot-pulsar"

elif [[ $application == 'reactiveapp' ]]; then
  spring_boot
  sonar_back

  applyModules \
  "spring-boot-webflux-netty" \
  "spring-boot-actuator" \
  "springdoc-webflux-openapi"

elif [[ $application == 'customjhlite' ]]; then
  spring_boot
  sonar_back

  applyModules "custom-jhlite"

elif [[ $application == 'typescriptapp' ]]; then
  applyModules \
    "init" \
    "typescript" \
    "optional-typescript"

else
  echo "*** Unknown configuration..."
  exit 1
fi

echo ""
cat "$payloadFile"
echo ""
sleep 5
