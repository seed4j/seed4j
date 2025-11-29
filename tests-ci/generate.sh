#!/usr/bin/env bash

show_syntax() {
  echo "Usage: $0 <application> [<java-build-tool> <spring-configuration-format> <node-package-manager>]" >&2
  exit 1
}

if [ "$#" -eq 1 ]; then
  application=$1
  java_build_tool=maven
  configuration_format=yaml
  node_package_manager=npm
elif [ "$#" -eq 4 ]; then
  application=$1
  java_build_tool=$2
  configuration_format=$3
  node_package_manager=$4
else
  show_syntax
fi

if test -f "modulePayload.json"; then
  payloadFile="modulePayload.json"
elif test -f tests-ci/modulePayload.json; then
  payloadFile=tests-ci/modulePayload.json
fi

apply_modules() {
  for module in $@; do
    local payload="$(sed -e "s/APP_NAME/$application/g;s/SPRING_CONFIG_FORMAT/$configuration_format/g;s/NODE_PACKAGE_MANAGER/$node_package_manager/g" $payloadFile)"
    local api="/api/modules/$module/apply-patch"

    echo "Apply module:" $module

    local status_code=$(curl -o /dev/null -s -w "%{http_code}\n" \
      -X POST \
      -H "accept: */*" \
      -H "Content-Type: application/json" \
      -d "$payload" \
      "http://localhost:1339""$api")

    if [[ $status_code == '40'* || $status_code == '50'* ]]; then
      echo "Error when calling API:" "$status_code" "$api"
      exit 1
    fi
  done
}

init_server() {
  apply_modules \
    "init" \
    "${java_build_tool}-wrapper" \
    "${java_build_tool}-java" \
    "modernizer"
}

spring_boot() {
  apply_modules \
    "github-actions-${java_build_tool}" \
    "java-base" \
    "checkstyle" \
    "jacoco-with-min-coverage-check" \
    "spring-boot" \
    "logs-spy"
}

spring_boot_mvc() {
  spring_boot

  apply_modules \
    "spring-boot-tomcat" \
    "spring-boot-actuator"
}

spring_boot_webflux() {
  spring_boot

  apply_modules \
    "spring-boot-webflux-netty" \
    "spring-boot-actuator"
}

sonar_back() {
  apply_modules "sonarqube-java-backend"
}

sonar_back_front() {
  apply_modules "sonarqube-java-backend-and-frontend"
}

frontend_server_plugin() {
  if [[ $java_build_tool == 'maven' ]]; then
    apply_modules \
      "frontend-maven-plugin" \
      "frontend-maven-plugin-cache"
  else
    apply_modules "node-gradle-plugin"
  fi
}

cucumber_with_jwt() {
  apply_modules \
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

elif [[ $application == fullapp* ]]; then
  init_server
  spring_boot_mvc
  sonar_back_front

  apply_modules \
    "application-service-hexagonal-architecture-documentation" \
    "approval-tests" \
    "jqwik" \
    "protobuf" \
    "protobuf-backwards-compatibility-check" \
    "ts-pagination-domain" \
    "ts-rest-pagination" \
    "ts-loader" \
    "infinitest-filters" \
    "pagination-domain" \
    "rest-pagination" \
    "jpa-pagination" \
    "spring-boot-async" \
    "spring-boot-devtools" \
    "openapi-contract" \
    "openapi-backwards-compatibility-check" \
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
  apply_modules "spring-boot-cucumber-jpa-reset"

  apply_modules \
    "datasource-postgresql" \
    "jpa-postgresql" \
    "liquibase" \
    "liquibase-linter" \
    "liquibase-async"

  apply_modules \
    "kipe-expression" \
    "kipe-authorization" \
    "sample-feature" \
    "sample-jpa-persistence" \
    "sample-liquibase-changelog"

  apply_modules \
    "ehcache-java-config" \
    "hibernate-2nd-level-cache"

  frontend_server_plugin
  apply_modules \
    "prettier" \
    "typescript" \
    "vue-core" \
    "tikui" \
    "cypress-component-tests" \
    "playwright-e2e"

elif [[ $application == 'oauth2app' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  apply_modules \
    "java-memoizers" \
    "license-mit"

  apply_modules \
    "spring-boot-oauth2" \
    "spring-boot-oauth2-account" \
    "springdoc-mvc-openapi" \
    "springdoc-oauth2"

  apply_modules \
    "spring-boot-cucumber-mvc" \
    "spring-boot-cucumber-oauth2-authentication" \
    "kipe-expression" \
    "kipe-authorization" \
    "sample-feature"

elif [[ $application == 'mysqlapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  apply_modules \
    "datasource-mysql" \
    "jpa-mysql" \
    "liquibase"

  cucumber_with_jwt
  apply_modules "spring-boot-cucumber-jpa-reset"

  apply_modules \
    "spring-boot-local-profile" \
    "kipe-expression" \
    "kipe-authorization" \
    "sample-feature" \
    "sample-jpa-persistence" \
    "sample-liquibase-changelog"

  apply_modules "ehcache-xml-config"

elif [[ $application == 'mariadbapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  apply_modules "springdoc-mvc-openapi"
  apply_modules \
    "datasource-mariadb" \
    "jpa-mariadb" \
    "liquibase"
  apply_modules "ehcache-xml-config"

elif [[ $application == 'mssqlapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  apply_modules "springdoc-mvc-openapi"
  apply_modules \
    "datasource-mssql" \
    "jpa-mssql"

elif [[ $application == 'flywayapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  apply_modules \
    "datasource-postgresql" \
    "jpa-postgresql" \
    "flyway" \
    "flyway-postgresql"

  cucumber_with_jwt
  apply_modules "spring-boot-cucumber-jpa-reset"

  apply_modules \
    "kipe-expression" \
    "kipe-authorization" \
    "sample-feature" \
    "sample-jpa-persistence" \
    "sample-postgresql-flyway-changelog"

elif [[ $application == 'eurekaapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  apply_modules \
    "eureka-client" \
    "spring-cloud"

elif [[ $application == 'consulapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  apply_modules "consul"

elif [[ $application == 'gatewayapp' ]]; then
  init_server
  spring_boot_webflux
  sonar_back

  apply_modules \
    "eureka-client" \
    "spring-cloud" \
    "gateway"

elif [[ $application == 'mongodbapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  apply_modules "mongodb" "mongock"

  cucumber_with_jwt

  apply_modules \
    "kipe-expression" \
    "kipe-authorization" \
    "sample-feature" \
    "sample-mongodb-persistence"

elif [[ $application == 'redisapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  apply_modules "redis"

  cucumber_with_jwt

elif [[ $application == 'cassandraapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back
  cucumber_with_jwt

  apply_modules \
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

  apply_modules \
    "neo4j" \
    "neo4j-migrations"

  cucumber_with_jwt

elif [[ $application == 'angularjwtapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back_front

  frontend_server_plugin
  apply_modules \
    "prettier" \
    "angular-core" \
    "angular-i18n" \
    "angular-tailwind" \
    "tikui" \
    "cypress-component-tests"

  cucumber_with_jwt

  apply_modules \
    "angular-jwt" \
    "angular-health"

elif [[ $application == 'angularoauth2app' ]]; then
  init_server
  spring_boot_mvc
  sonar_back_front

  apply_modules \
    "java-memoizers"

  frontend_server_plugin
  apply_modules \
    "prettier" \
    "angular-core"

  apply_modules \
    "spring-boot-oauth2" \
    "spring-boot-oauth2-account" \
    "springdoc-mvc-openapi" \
    "springdoc-oauth2"

  apply_modules "angular-oauth2-keycloak"

elif [[ $application == 'reactapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back_front

  frontend_server_plugin
  apply_modules \
    "prettier" \
    "typescript" \
    "react-core" \
    "react-i18next" \
    "tikui" \
    "cypress-component-tests"

  cucumber_with_jwt

  apply_modules "react-jwt"

elif [[ $application == 'vuejwtapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back_front

  frontend_server_plugin
  apply_modules \
    "prettier" \
    "typescript" \
    "vue-core" \
    "vue-router" \
    "vue-i18next" \
    "vue-pinia" \
    "vue-jwt" \
    "tikui" \
    "playwright-component-tests" \
    "cypress-e2e"

  cucumber_with_jwt

elif [[ $application == 'vueoauth2app' ]]; then
  init_server
  spring_boot_mvc
  sonar_back_front

  frontend_server_plugin
  apply_modules \
    "prettier" \
    "typescript" \
    "vue-core" \
    "vue-router" \
    "vue-i18next" \
    "vue-pinia" \
    "vue-oauth2-keycloak" \
    "tikui" \
    "playwright-component-tests" \
    "cypress-e2e"

elif [[ $application == 'svelteapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back_front

  frontend_server_plugin
  apply_modules \
    "prettier" \
    "svelte-core"

elif [[ $application == 'kafkaapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  apply_modules \
    "spring-boot-kafka" \
    "spring-boot-kafka-akhq"

elif [[ $application == 'pulsarapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  apply_modules "spring-boot-pulsar"

elif [[ $application == 'reactiveapp' ]]; then
  init_server
  spring_boot_webflux
  sonar_back

  apply_modules \
    "springdoc-webflux-openapi"

elif [[ $application == 'seed4jextension' ]]; then
  init_server
  spring_boot
  sonar_back

  apply_modules "seed4j-extension"

elif [[ $application == 'typescriptapp' ]]; then
  apply_modules \
    "init" \
    "prettier" \
    "typescript" \
    "optional-typescript" \
    "sonarqube-typescript"

elif [[ $application == 'archunitts' ]]; then
  apply_modules \
    "init" \
    "prettier" \
    "typescript" \
    "vue-core" \
    "arch-unit-ts"

elif [[ $application == 'thymeleafapp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  apply_modules \
    "spring-boot-thymeleaf" \
    "thymeleaf-template" \
    "thymeleaf-template-tailwindcss" \
    "webjars-locator" \
    "htmx-webjars" \
    "thymeleaf-template-htmx-webjars"

elif [[ $application == 'langchain4japp' ]]; then
  init_server
  spring_boot_mvc
  sonar_back

  apply_modules \
    "langchain4j" \
    "spring-boot-langchain4j-sample"

else
  echo "*** Unknown configuration..." "$application"
  exit 1
fi

echo "*** Waiting 5 seconds..."
sleep 5
