#!/usr/bin/env bash

show_syntax() {
  echo "Usage: $0 <application> <java-build-tool> <spring-configuration-format>" >&2
  exit 1
}

if [ "$#" -ne 3 ]; then
  show_syntax
fi

if test -f "modulePayload.json"; then
  payloadFile="modulePayload.json"
elif test -f tests-ci/modulePayload.json; then
  payloadFile=tests-ci/modulePayload.json
fi

application=$1
java_build_tool=$2
configuration_format=$3

applyModules() {
  for module in $@; do
    local payload="$(sed -e "s/APP_NAME/$application/g;s/SPRING_CONFIG_FORMAT/$configuration_format/g" $payloadFile)"
    local api="/api/modules/$module/apply-patch"

    echo "Apply module:" $module

    local status_code=$(curl -o /dev/null -s -w "%{http_code}\n" \
      -X POST \
      -H "accept: */*" \
      -H "Content-Type: application/json" \
      -d "$payload" \
      "http://localhost:7471""$api")

    if [[ $status_code == '40'* || $status_code == '50'* ]]; then
      echo "Error when calling API:" "$status_code" "$api"
      exit 1
    fi
  done
}

init_server() {
  applyModules \
    "init" \
    "${java_build_tool}-wrapper" \
    "${java_build_tool}-java"
}

spring_boot() {
  applyModules \
    "github-actions-${java_build_tool}" \
    "java-base" \
    "checkstyle" \
    "approval-tests" \
    "jqwik" \
    "protobuf" \
    "protobuf-backwards-compatibility-check" \
    "jacoco-with-min-coverage-check" \
    "spring-boot" \
    "logs-spy"
}

spring_boot_mvc() {
  spring_boot

  applyModules \
    "spring-boot-tomcat" \
    "spring-boot-actuator"
}

spring_boot_undertow() {
  spring_boot

  applyModules \
    "spring-boot-undertow" \
    "spring-boot-actuator"
}

spring_boot_webflux() {
  spring_boot

  applyModules \
    "spring-boot-webflux-netty" \
    "spring-boot-actuator"
}

sonar_back() {
  applyModules "sonar-qube-java-backend"
}

sonar_back_front() {
  applyModules "sonar-qube-java-backend-and-frontend"
}

frontend_server_plugin() {
  if [[ $java_build_tool == 'maven' ]]; then
    applyModules "frontend-maven-plugin"
  else
    applyModules "node-gradle-plugin"
  fi
}

cucumber_with_jwt() {
  applyModules \
    "spring-boot-jwt" \
    "spring-boot-jwt-basic-auth" \
    "springdoc-mvc-openapi" \
    "springdoc-jwt" \
    "spring-boot-cucumber-mvc" \
    "spring-boot-cucumber-jwt-authentication"
}

if [[ $application == 'spring-boot' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

elif [[ $application == 'fullstack' ]]; then
  init_server
  spring_boot_mvc
  sonar_back_front

elif [[ $application == 'fullapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back_front

  applyModules \
    "ts-pagination-domain" \
    "ts-rest-pagination" \
    "prettier" \
    "ts-loader" \
    "infinitest-filters" \
    "pagination-domain" \
    "rest-pagination" \
    "jpa-pagination" \
    "spring-boot-async" \
    "spring-boot-devtools" \
    "logstash" \
    "jib" \
    "dockerfile-${java_build_tool}" \
    "java-archunit" \
    "git-information" \
    "github-codespaces" \
    "gitpod" \
    "java-memoizers" \
    "java-enums" \
    "spring-boot-local-profile" \
    "internationalized-errors" \
    "spring-boot-cache" \
    "caffeine-cache" \
    "jmolecules" \
    "jqassistant" \
    "jqassistant-jmolecules" \
    "jqassistant-spring" \
    "license-apache" \
    "renovate" \
    "front-hexagonal-architecture"

  cucumber_with_jwt

  applyModules "spring-boot-cucumber-jpa-reset"
  applyModules "application-service-hexagonal-architecture-documentation"

  applyModules "postgresql" "liquibase" "liquibase-async"

  applyModules \
    "kipe-expression" \
    "kipe-authorization" \
    "sample-feature" \
    "sample-jpa-persistence" \
    "sample-liquibase-changelog"

  applyModules "ehcache-java-config"

  applyModules "hibernate-2nd-level-cache"

  frontend_server_plugin
  applyModules \
    "vue-core" \
    "cypress-component-tests" \
    "playwright-e2e"

elif [[ $application == 'oauth2app' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  applyModules \
    "java-memoizers" \
    "license-mit"

  applyModules \
    "spring-boot-oauth2" \
    "spring-boot-oauth2-account" \
    "springdoc-mvc-openapi" \
    "springdoc-oauth2"

  applyModules \
    "spring-boot-cucumber-mvc" \
    "spring-boot-cucumber-oauth2-authentication" \
    "kipe-expression" \
    "kipe-authorization" \
    "sample-feature"

elif [[ $application == 'mysqlapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  applyModules "mysql" "liquibase"

  cucumber_with_jwt

  applyModules "spring-boot-cucumber-jpa-reset"

  applyModules \
    "spring-boot-local-profile" \
    "kipe-expression" \
    "kipe-authorization" \
    "sample-feature" \
    "sample-jpa-persistence" \
    "sample-liquibase-changelog"

  applyModules "ehcache-xml-config"

elif [[ $application == 'mariadbapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  applyModules "springdoc-mvc-openapi"
  applyModules "mariadb" "liquibase"
  applyModules "ehcache-xml-config"

elif [[ $application == 'mssqlapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  applyModules "springdoc-mvc-openapi"
  applyModules "mssql"

elif [[ $application == 'flywayapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  applyModules "postgresql" "flyway" "flyway-postgresql"

  cucumber_with_jwt

  applyModules "spring-boot-cucumber-jpa-reset"

  applyModules \
    "kipe-expression" \
    "kipe-authorization" \
    "sample-feature" \
    "sample-jpa-persistence" \
    "sample-postgresql-flyway-changelog"

elif [[ $application == 'undertowapp' ]]; then
  init_server
  spring_boot_undertow
  sonar_back

  applyModules \
    "mysql" \
    "flyway" \
    "flyway-mysql"

  cucumber_with_jwt
  applyModules "spring-boot-cucumber-jpa-reset"

  applyModules \
    "kipe-expression" \
    "kipe-authorization" \
    "sample-feature" \
    "sample-jpa-persistence" \
    "sample-not-postgresql-flyway-changelog"

  applyModules "spring-boot-cache"

elif [[ $application == 'eurekaapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  applyModules \
    "eureka-client" \
    "spring-cloud"

elif [[ $application == 'consulapp' ]]; then
  init_server
  spring_boot_undertow
  sonar_back

  applyModules "consul"

elif [[ $application == 'gatewayapp' ]]; then
  init_server
  spring_boot_webflux
  sonar_back

  applyModules \
    "eureka-client" \
    "spring-cloud" \
    "gateway"

elif [[ $application == 'mongodbapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  applyModules "mongodb" "mongock"

  cucumber_with_jwt

  applyModules \
    "kipe-expression" \
    "kipe-authorization" \
    "sample-feature" \
    "sample-mongodb-persistence"

elif [[ $application == 'redisapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  applyModules "redis"

  cucumber_with_jwt

elif [[ $application == 'cassandraapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back
  cucumber_with_jwt

  applyModules \
    "cassandra" \
    "cassandra-migration" \
    "kipe-expression" \
    "kipe-authorization" \
    "sample-feature" \
    "sample-cassandra-persistence"

elif [[ $application == 'neo4japp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  applyModules "neo4j" "neo4j-migrations"

  cucumber_with_jwt

elif [[ $application == 'angularapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back_front

  frontend_server_plugin
  applyModules \
    "angular-core" \
    "cypress-component-tests"

  cucumber_with_jwt

  applyModules "angular-jwt" "angular-health"

elif [[ $application == 'angularoauth2app' ]]; then
  init_server
  spring_boot_mvc
  sonar_back_front

  applyModules \
    "java-memoizers"

  frontend_server_plugin
  applyModules "angular-core"

  applyModules \
    "spring-boot-oauth2" \
    "spring-boot-oauth2-account" \
    "springdoc-mvc-openapi" \
    "springdoc-oauth2"

  applyModules "angular-oauth2"

elif [[ $application == 'reactapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back_front

  frontend_server_plugin
  applyModules \
    "react-core" \
    "cypress-component-tests"

  cucumber_with_jwt

  applyModules "react-jwt"

elif [[ $application == 'vueapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back_front

  frontend_server_plugin
  applyModules \
    "vue-core" \
    "vue-pinia" \
    "playwright-component-tests" \
    "cypress-e2e"

elif [[ $application == 'svelteapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back_front

  frontend_server_plugin
  applyModules \
    "prettier" \
    "svelte-core"

elif [[ $application == 'kafkaapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  applyModules "spring-boot-kafka" "spring-boot-kafka-akhq"

elif [[ $application == 'pulsarapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  applyModules "spring-boot-pulsar"

elif [[ $application == 'reactiveapp' ]]; then
  init_server
  spring_boot_webflux
  sonar_back

  applyModules \
    "springdoc-webflux-openapi"

elif [[ $application == 'customjhlite' ]]; then
  init_server
  spring_boot
  sonar_back

  applyModules "custom-jhlite"

elif [[ $application == 'typescriptapp' ]]; then
  applyModules \
    "init" \
    "typescript" \
    "optional-typescript"

elif [[ $application == 'thymeleafapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  applyModules \
    "spring-boot-thymeleaf" \
    "thymeleaf-template" \
    "thymeleaf-template-tailwindcss" \
    "webjars-locator" \
    "htmx-webjars" \
    "thymeleaf-template-htmx-webjars"

else
  echo "*** Unknown configuration..."
  exit 1
fi

echo "*** Waiting 5 seconds..."
sleep 5
