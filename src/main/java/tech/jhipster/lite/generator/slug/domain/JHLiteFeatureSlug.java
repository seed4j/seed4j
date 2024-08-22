package tech.jhipster.lite.generator.slug.domain;

import tech.jhipster.lite.module.domain.resource.JHipsterFeatureSlugFactory;

public enum JHLiteFeatureSlug implements JHipsterFeatureSlugFactory {
  ANGULAR_AUTHENTICATION("angular-authentication"),
  AUTHENTICATION("authentication"),
  AUTHENTICATION_SPRINGDOC("authentication-springdoc"),
  JCACHE("jcache"),
  CLIENT_CORE("client-core"),
  CLIENT_INTERNATIONALIZATION("client-internationalization"),
  CUCUMBER_AUTHENTICATION("cucumber-authentication"),
  DATABASE_MIGRATION("database-migration"),
  DOCKERFILE("dockerfile"),
  FRONT_BROWSER_TEST("front-browser-test"),
  FRONTEND_JAVA_BUILD_TOOL_PLUGIN("frontend-java-build-tool-plugin"),
  GITHUB_ACTIONS("github-actions"),
  GITLAB_CI("gitlab-ci"),
  DEPENDENCIES_UPDATES("dependencies-updates"),
  CODE_COVERAGE_JAVA("code-coverage-java"),
  JAVA_BUILD_TOOL("java-build-tool"),
  JAVA_BUILD_TOOL_WRAPPER("java-build-tool-wrapper"),
  JPA_PERSISTENCE("jpa-persistence"),
  LICENSE("license"),
  OAUTH_PROVIDER("oauth-provider"),
  OAUTH_PROVIDER_SPRINGDOC("oauth-provider-springdoc"),
  SAMPLE_PERSISTENCE("sample-persistence"),
  SAMPLE_SCHEMA("sample-schema"),
  SERVICE_DISCOVERY("service-discovery"),
  SONAR_QUBE_JAVA("sonar-qube-java"),
  SPRING_BOOT_CUCUMBER("spring-boot-cucumber"),
  SPRING_SERVER("spring-server"),
  SPRING_MVC_SERVER("spring-mvc-server"),
  SPRINGDOC("springdoc");

  private final String slug;

  JHLiteFeatureSlug(String slug) {
    this.slug = slug;
  }

  @Override
  public String get() {
    return slug;
  }
}
