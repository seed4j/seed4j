package com.seed4j.shared.slug.domain;

import com.seed4j.module.domain.resource.SeedFeatureSlugFactory;

public enum Seed4JFeatureSlug implements SeedFeatureSlugFactory {
  ANGULAR_AUTHENTICATION("angular-authentication"),
  AUTHENTICATION("authentication"),
  AUTHENTICATION_SPRINGDOC("authentication-springdoc"),
  JCACHE("jcache"),
  CLIENT_CORE("client-core"),
  CLIENT_INTERNATIONALIZATION("client-internationalization"),
  CUCUMBER_AUTHENTICATION("cucumber-authentication"),
  DATASOURCE("datasource"),
  DATABASE_MIGRATION("database-migration"),
  DOCKERFILE("dockerfile"),
  E2E_TESTS("e2e-tests"),
  FRONTEND_COMPONENT_TESTS("frontend-component-tests"),
  FRONTEND_JAVA_BUILD_TOOL_PLUGIN("frontend-java-build-tool-plugin"),
  GITHUB_ACTIONS("github-actions"),
  GITLAB_CI("gitlab-ci"),
  DEPENDENCIES_UPDATES("dependencies-updates"),
  CODE_COVERAGE_CLIENT("code-coverage-client"),
  CODE_COVERAGE_JAVA("code-coverage-java"),
  JAVA_BUILD_TOOL("java-build-tool"),
  JAVA_BUILD_TOOL_WRAPPER("java-build-tool-wrapper"),
  JOOQ("jooq"),
  JPA_PERSISTENCE("jpa-persistence"),
  LICENSE("license"),
  OAUTH_PROVIDER("oauth-provider"),
  OAUTH_PROVIDER_SPRINGDOC("oauth-provider-springdoc"),
  SAMPLE_PERSISTENCE("sample-persistence"),
  SAMPLE_SCHEMA("sample-schema"),
  SERVICE_DISCOVERY("service-discovery"),
  SONARQUBE("sonarqube"),
  SPRING_BOOT_CUCUMBER("spring-boot-cucumber"),
  SPRING_SERVER("spring-server"),
  SPRING_MVC_SERVER("spring-mvc-server"),
  SPRINGDOC("springdoc"),
  VUE_AUTHENTICATION("vue-authentication");

  private final String slug;

  Seed4JFeatureSlug(String slug) {
    this.slug = slug;
  }

  @Override
  public String get() {
    return slug;
  }
}
