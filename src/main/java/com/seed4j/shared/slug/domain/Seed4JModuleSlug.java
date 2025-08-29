package com.seed4j.shared.slug.domain;

import static com.seed4j.module.domain.resource.SeedModuleRank.RANK_A;
import static com.seed4j.module.domain.resource.SeedModuleRank.RANK_B;
import static com.seed4j.module.domain.resource.SeedModuleRank.RANK_C;
import static com.seed4j.module.domain.resource.SeedModuleRank.RANK_D;
import static com.seed4j.module.domain.resource.SeedModuleRank.RANK_S;

import com.seed4j.module.domain.resource.SeedModuleRank;
import com.seed4j.module.domain.resource.SeedModuleSlugFactory;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Seed4JModuleSlug implements SeedModuleSlugFactory {
  ANGULAR_CORE("angular-core", RANK_A),
  ANGULAR_HEALTH("angular-health", RANK_C),
  ANGULAR_JWT("angular-jwt", RANK_C),
  ANGULAR_OAUTH_2("angular-oauth2-keycloak", RANK_C),
  APPLICATION_SERVICE_HEXAGONAL_ARCHITECTURE_DOCUMENTATION("application-service-hexagonal-architecture-documentation", RANK_A),
  APPROVAL_TESTS("approval-tests", RANK_B),
  ARCH_UNIT_TS("arch-unit-ts", RANK_C),
  CAFFEINE_CACHE("caffeine-cache", RANK_A),
  CASSANDRA("cassandra", RANK_C),
  CASSANDRA_MIGRATION("cassandra-migration", RANK_C),
  CHECKSTYLE("checkstyle", RANK_A),
  CONSUL("consul", RANK_B),
  CYPRESS_COMPONENT_TESTS("cypress-component-tests", RANK_B),
  CYPRESS_E2E("cypress-e2e", RANK_B),
  DATASOURCE_MARIADB("datasource-mariadb", RANK_B),
  DATASOURCE_MSSQL("datasource-mssql", RANK_C),
  DATASOURCE_MYSQL("datasource-mysql", RANK_A),
  DATASOURCE_POSTGRESQL("datasource-postgresql", RANK_S),
  DOCKERFILE_MAVEN("dockerfile-maven", RANK_C),
  DOCKERFILE_GRADLE("dockerfile-gradle", RANK_C),
  EHCACHE_JAVA_CONFIG("ehcache-java-config", RANK_B),
  EHCACHE_XML_CONFIG("ehcache-xml-config", RANK_C),
  EUREKA_CLIENT("eureka-client", RANK_C),
  FLYWAY("flyway", RANK_C),
  FLYWAY_MARIADB("flyway-mariadb", RANK_C),
  FLYWAY_MSSQL("flyway-mssql", RANK_C),
  FLYWAY_MYSQL("flyway-mysql", RANK_C),
  FLYWAY_POSTGRESQL("flyway-postgresql", RANK_C),
  FRONTEND_MAVEN_PLUGIN("frontend-maven-plugin", RANK_S),
  FRONTEND_MAVEN_PLUGIN_CACHE("frontend-maven-plugin-cache", RANK_B),
  FRONTEND_MAVEN_PLUGIN_MERGE_COVERAGE("frontend-maven-plugin-merge-coverage", RANK_B),
  FRONT_HEXAGONAL_ARCHITECTURE("front-hexagonal-architecture", RANK_C),
  NODE_GRADLE_PLUGIN("node-gradle-plugin", RANK_C),
  GATEWAY("gateway", RANK_C),
  GIT_INFORMATION("git-information", RANK_B),
  GITHUB_ACTIONS_MAVEN("github-actions-maven", RANK_B),
  GITHUB_ACTIONS_GRADLE("github-actions-gradle", RANK_B),
  GITHUB_CODESPACES("github-codespaces", RANK_D),
  GITLAB_CI_MAVEN("gitlab-ci-maven", RANK_B),
  GITLAB_CI_GRADLE("gitlab-ci-gradle", RANK_B),
  GITPOD("gitpod", RANK_D),
  GRADLE_JAVA("gradle-java", RANK_C),
  GRADLE_WRAPPER("gradle-wrapper", RANK_C),
  HIBERNATE_2ND_LEVEL_CACHE("hibernate-2nd-level-cache", RANK_B),
  INFINITEST_FILTERS("infinitest-filters", RANK_A),
  INIT("init", RANK_S),
  INTERNATIONALIZED_ERRORS("internationalized-errors", RANK_B),
  JACOCO("jacoco", RANK_A),
  JACOCO_WITH_MIN_COVERAGE_CHECK("jacoco-with-min-coverage-check", RANK_A),
  JAVA_ARCHUNIT("java-archunit", RANK_A),
  JAVA_BASE("java-base", RANK_S),
  JAVA_ENUMS("java-enums", RANK_B),
  JAVA_MEMOIZERS("java-memoizers", RANK_B),
  JIB("jib", RANK_B),
  JMOLECULES("jmolecules", RANK_B),
  JOOQ_MARIADB("jooq-mariadb", RANK_C),
  JOOQ_MSSQL("jooq-mssql", RANK_C),
  JOOQ_MYSQL("jooq-mysql", RANK_C),
  JOOQ_POSTGRESQL("jooq-postgresql", RANK_C),
  JPA_PAGINATION("jpa-pagination", RANK_A),
  JPA_MARIADB("jpa-mariadb", RANK_B),
  JPA_MSSQL("jpa-mssql", RANK_C),
  JPA_MYSQL("jpa-mysql", RANK_A),
  JPA_POSTGRESQL("jpa-postgresql", RANK_S),
  JQASSISTANT("jqassistant", RANK_B),
  JQASSISTANT_JMOLECULES("jqassistant-jmolecules", RANK_B),
  JQASSISTANT_SPRING("jqassistant-spring", RANK_B),
  JQWIK("jqwik", RANK_C),
  KIPE_AUTHORIZATION("kipe-authorization", RANK_B),
  KIPE_EXPRESSION("kipe-expression", RANK_B),
  LANGCHAIN4J("langchain4j", RANK_C),
  LICENSE_APACHE("license-apache", RANK_B),
  LICENSE_MIT("license-mit", RANK_B),
  LIQUIBASE("liquibase", RANK_S),
  LIQUIBASE_ASYNC("liquibase-async", RANK_B),
  LIQUIBASE_LINTER("liquibase-linter", RANK_B),
  LOGSTASH("logstash", RANK_B),
  LOGS_SPY("logs-spy", RANK_S),
  CYPRESS_MERGE_COVERAGE("cypress-merge-coverage", RANK_B),
  MAVEN_JAVA("maven-java", RANK_S),
  MAVEN_WRAPPER("maven-wrapper", RANK_S),
  MODERNIZER("modernizer", RANK_C),
  MONGOCK("mongock", RANK_C),
  MONGODB("mongodb", RANK_C),
  NEO4J("neo4j", RANK_C),
  NEO4J_MIGRATIONS("neo4j-migrations", RANK_C),
  OPENAPI_CONTRACT("openapi-contract", RANK_B),
  OPENAPI_BACKWARDS_COMPATIBILITY_CHECK("openapi-backwards-compatibility-check", RANK_B),
  OPTIONAL_TYPESCRIPT("optional-typescript", RANK_B),
  PAGINATION_DOMAIN("pagination-domain", RANK_A),
  PLAYWRIGHT_COMPONENT_TESTS("playwright-component-tests", RANK_C),
  PLAYWRIGHT_E2E("playwright-e2e", RANK_C),
  PRETTIER("prettier", RANK_S),
  PROTOBUF("protobuf", RANK_A),
  PROTOBUF_BACKWARDS_COMPATIBILITY_CHECK("protobuf-backwards-compatibility-check", RANK_A),
  REACT_CORE("react-core", RANK_B),
  REACT_I18N("react-i18next", RANK_C),
  REACT_JWT("react-jwt", RANK_C),
  REDIS("redis", RANK_C),
  RENOVATE("renovate", RANK_B),
  REST_PAGINATION("rest-pagination", RANK_A),
  SAMPLE_CASSANDRA_PERSISTENCE("sample-cassandra-persistence", RANK_D),
  SAMPLE_FEATURE("sample-feature", RANK_D),
  SAMPLE_JPA_PERSISTENCE("sample-jpa-persistence", RANK_D),
  SAMPLE_LIQUIBASE_CHANGELOG("sample-liquibase-changelog", RANK_D),
  SAMPLE_MONGODB_PERSISTENCE("sample-mongodb-persistence", RANK_D),
  SAMPLE_NOT_POSTGRESQL_FLYWAY_CHANGELOG("sample-not-postgresql-flyway-changelog", RANK_D),
  SAMPLE_POSTGRESQL_FLYWAY_CHANGELOG("sample-postgresql-flyway-changelog", RANK_D),
  SEED4J_EXTENSION("seed4j-extension", RANK_A),
  SONARQUBE_JAVA_BACKEND("sonarqube-java-backend", RANK_A),
  SONARQUBE_JAVA_BACKEND_AND_FRONTEND("sonarqube-java-backend-and-frontend", RANK_A),
  SONARQUBE_TYPESCRIPT("sonarqube-typescript", RANK_B),
  SPRING_BOOT("spring-boot", RANK_S),
  SPRING_BOOT_ACTUATOR("spring-boot-actuator", RANK_S),
  SPRING_BOOT_ASYNC("spring-boot-async", RANK_A),
  SPRING_BOOT_CACHE("spring-boot-cache", RANK_A),
  SPRING_BOOT_CUCUMBER_MVC("spring-boot-cucumber-mvc", RANK_A),
  SPRING_BOOT_CUCUMBER_WEBFLUX("spring-boot-cucumber-webflux", RANK_C),
  SPRING_BOOT_CUCUMBER_JPA_RESET("spring-boot-cucumber-jpa-reset", RANK_A),
  SPRING_BOOT_CUCUMBER_JWT_AUTHENTICATION("spring-boot-cucumber-jwt-authentication", RANK_C),
  SPRING_BOOT_CUCUMBER_OAUTH_2_AUTHENTICATION("spring-boot-cucumber-oauth2-authentication", RANK_B),
  SPRING_BOOT_DEVTOOLS("spring-boot-devtools", RANK_C),
  SPRING_BOOT_LOCAL_PROFILE("spring-boot-local-profile", RANK_B),
  SPRING_BOOT_DOCKER_COMPOSE("spring-boot-docker-compose", RANK_C),
  SPRING_BOOT_JWT("spring-boot-jwt", RANK_C),
  SPRING_BOOT_JWT_BASIC_AUTH("spring-boot-jwt-basic-auth", RANK_C),
  SPRING_BOOT_KAFKA("spring-boot-kafka", RANK_B),
  SPRING_BOOT_KAFKA_AKHQ("spring-boot-kafka-akhq", RANK_B),
  SPRING_BOOT_KAFKA_SAMPLE_PRODUCER_CONSUMER("spring-boot-kafka-sample-producer-consumer", RANK_D),
  SPRING_BOOT_OAUTH_2("spring-boot-oauth2", RANK_A),
  SPRING_BOOT_OAUTH_2_ACCOUNT("spring-boot-oauth2-account", RANK_B),
  SPRING_BOOT_OAUTH_2_AUTH_0("spring-boot-oauth2-auth0", RANK_D),
  SPRING_BOOT_OAUTH_2_OKTA("spring-boot-oauth2-okta", RANK_D),
  SPRING_BOOT_PULSAR("spring-boot-pulsar", RANK_D),
  SPRING_BOOT_MVC_EMPTY("spring-boot-mvc-empty", RANK_S),
  SPRING_BOOT_THYMELEAF("spring-boot-thymeleaf", RANK_C),
  SPRING_BOOT_LANGCHAIN4J_SAMPLE("spring-boot-langchain4j-sample", RANK_D),
  SPRING_BOOT_TOMCAT("spring-boot-tomcat", RANK_S),
  SPRING_BOOT_UNDERTOW("spring-boot-undertow", RANK_B),
  SPRING_BOOT_WEBFLUX_EMPTY("spring-boot-webflux-empty", RANK_C),
  SPRING_BOOT_WEBFLUX_NETTY("spring-boot-webflux-netty", RANK_C),
  SPRING_CLOUD("spring-cloud", RANK_B),
  SPRINGDOC_JWT("springdoc-jwt", RANK_C),
  SPRINGDOC_MVC_OPENAPI("springdoc-mvc-openapi", RANK_S),
  SPRINGDOC_OAUTH_2("springdoc-oauth2", RANK_B),
  SPRINGDOC_OAUTH_2_AUTH_0("springdoc-oauth2-auth0", RANK_D),
  SPRINGDOC_OAUTH_2_OKTA("springdoc-oauth2-okta", RANK_D),
  SPRINGDOC_WEBFLUX_OPENAPI("springdoc-webflux-openapi", RANK_C),
  THYMELEAF_TEMPLATE("thymeleaf-template", RANK_C),
  THYMELEAF_TEMPLATE_HTMX_WEBJAR("thymeleaf-template-htmx-webjars", RANK_C),
  THYMELEAF_TEMPLATE_ALPINEJS_WEBJAR("thymeleaf-template-alpinejs-webjars", RANK_C),
  THYMELEAF_TEMPLATE_TAILWINDCSS("thymeleaf-template-tailwindcss", RANK_C),
  TIKUI("tikui", RANK_B),
  TS_LOADER("ts-loader", RANK_C),
  TS_PAGINATION_DOMAIN("ts-pagination-domain", RANK_A),
  TS_REST_PAGINATION("ts-rest-pagination", RANK_A),
  WEBJARS_LOCATOR("webjars-locator", RANK_C),
  HTMX_WEBJARS("htmx-webjars", RANK_C),
  ALPINE_JS_WEBJARS("alpinejs-webjars", RANK_C),
  SVELTE_CORE("svelte-core", RANK_D),
  TYPESCRIPT("typescript", RANK_S),
  VUE_CORE("vue-core", RANK_A),
  VUE_I18N("vue-i18next", RANK_C),
  VUE_JWT("vue-jwt", RANK_C),
  VUE_OAUTH2_KEYCLOAK("vue-oauth2-keycloak", RANK_C),
  VUE_PINIA("vue-pinia", RANK_C),
  VUE_ROUTER("vue-router", RANK_C);

  private static final Map<String, Seed4JModuleSlug> moduleSlugMap = Stream.of(values()).collect(
    Collectors.toMap(Seed4JModuleSlug::get, Function.identity())
  );
  private final String slug;
  private final SeedModuleRank rank;

  Seed4JModuleSlug(String slug, SeedModuleRank rank) {
    this.slug = slug;
    this.rank = rank;
  }

  @Override
  public String get() {
    return slug;
  }

  @Override
  public SeedModuleRank rank() {
    return rank;
  }

  public static Optional<SeedModuleRank> getRank(String slug) {
    return fromString(slug).map(Seed4JModuleSlug::rank);
  }

  public static Optional<Seed4JModuleSlug> fromString(String slug) {
    return Optional.ofNullable(moduleSlugMap.get(slug));
  }
}
