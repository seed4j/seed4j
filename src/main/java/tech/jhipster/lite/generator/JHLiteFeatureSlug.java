package tech.jhipster.lite.generator;

import tech.jhipster.lite.module.domain.resource.JHipsterFeatureSlugFactory;

public enum JHLiteFeatureSlug implements JHipsterFeatureSlugFactory {
  ANGULAR_AUTHENTICATION("angular-authentication"),
  AUTHENTICATION("authentication"),
  BANNER("banner"),
  JCACHE("jcache"),
  CLIENT_CORE("client-core"),
  CUCUMBER_AUTHENTICATION("cucumber-authentication"),
  DATABASE_MIGRATION("database-migration"),
  DUMMY_PERSISTENCE("dummy-persistence"),
  DUMMY_SCHEMA("dummy-schema"),
  FRONT_BROWSER_TEST("front-browser-test"),
  JAVA_BUILD_TOOL("java-build-tool"),
  JPA_PERSISTENCE("jpa-persistence"),
  SERVICE_DISCOVERY("service-discovery"),
  SPRING_SERVER("spring-server"),
  SPRINGDOC("springdoc"),
  WEB_ERROR_MANAGEMENT("web-error-management");

  private final String slug;

  JHLiteFeatureSlug(String slug) {
    this.slug = slug;
  }

  @Override
  public String get() {
    return slug;
  }
}
