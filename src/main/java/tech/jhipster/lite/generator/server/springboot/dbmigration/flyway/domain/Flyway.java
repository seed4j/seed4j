package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain;

import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

public class Flyway {

  public static final Boolean DEFAULT_FLYWAY_ENABLED = true;
  public static final String DEFAULT_SQL_FILES_FOLDER = "db/migration";
  public static final String DEFAULT_FLYWAY_LOCATIONS = "classpath:" + DEFAULT_SQL_FILES_FOLDER;

  private Flyway() {
    // Cannot be instantiated
  }

  public static Dependency flywayDependency() {
    return Dependency.builder().groupId("org.flywaydb").artifactId("flyway-core").version("\\${flyway.version}").build();
  }

  public static Dependency additionalFlywayMysqlDependency() {
    return Dependency.builder().groupId("org.flywaydb").artifactId("flyway-mysql").version("\\${flyway.version}").build();
  }
}
