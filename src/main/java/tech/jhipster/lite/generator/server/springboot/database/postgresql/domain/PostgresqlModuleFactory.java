package tech.jhipster.lite.generator.server.springboot.database.postgresql.domain;

import static tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonModuleBuilder.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.docker.domain.DockerImage;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.module.domain.JHipsterDestination;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.LogLevel;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class PostgresqlModuleFactory {

  private static final String DEST_SECONDARY = "technical/infrastructure/secondary/postgresql";
  public static final String ORG_POSTGRESQL = "org.postgresql";

  private final DockerImages dockerImages;

  public PostgresqlModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    DockerImage dockerImage = dockerImages.get("postgres");
    JHipsterSource source = from("server/springboot/database/" + DatabaseType.POSTGRESQL.id());
    JHipsterDestination databasePath = toSrcMainJava().append(properties.basePackage().path()).append(DEST_SECONDARY);

    return sqlCommonModuleBuilder(
      properties,
      DatabaseType.POSTGRESQL,
      dockerImage,
      documentationTitle("Postgresql"),
      artifactId("postgresql")
    )
      .files()
      .add(source.template("FixedPostgreSQL10Dialect.java"), databasePath.append("FixedPostgreSQL10Dialect.java"))
      .add(
        source.template("FixedPostgreSQL10DialectTest.java"),
        toSrcTestJava().append(properties.basePackage().path()).append(DEST_SECONDARY).append("FixedPostgreSQL10DialectTest.java")
      )
      .and()
      .javaDependencies()
      .addDependency(groupId(ORG_POSTGRESQL), artifactId("postgresql"))
      .and()
      .springMainProperties()
      .set(propertyKey("spring.datasource.url"), propertyValue("jdbc:postgresql://localhost:5432/" + properties.projectBaseName().name()))
      .set(propertyKey("spring.datasource.username"), propertyValue(properties.projectBaseName().name()))
      .set(propertyKey("spring.datasource.driver-class-name"), propertyValue("org.postgresql.Driver"))
      .set(
        propertyKey("spring.jpa.database-platform"),
        propertyValue(properties.basePackage().basePackage() + ".technical.infrastructure.secondary.postgresql.FixedPostgreSQL10Dialect")
      )
      .and()
      .springTestProperties()
      .set(
        propertyKey("spring.datasource.url"),
        propertyValue(
          "jdbc:tc:postgresql:" + dockerImage.version() + ":///" + properties.projectBaseName().name() + "?TC_TMPFS=/testtmpfs:rw"
        )
      )
      .and()
      .springMainLogger(ORG_POSTGRESQL, LogLevel.WARN)
      .springTestLogger(ORG_POSTGRESQL, LogLevel.WARN)
      .springTestLogger("org.jboss.logging", LogLevel.WARN)
      .build();
  }
}
