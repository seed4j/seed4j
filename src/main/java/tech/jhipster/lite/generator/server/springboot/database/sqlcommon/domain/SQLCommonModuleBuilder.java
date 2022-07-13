package tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain;

import static tech.jhipster.lite.common.domain.WordUtils.*;
import static tech.jhipster.lite.generator.server.springboot.common.domain.SpringBoot.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.docker.domain.DockerImage;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.module.domain.DocumentationTitle;
import tech.jhipster.lite.module.domain.JHipsterDestination;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class SQLCommonModuleBuilder {

  private static final String ORG_HIBERNATE = "org.hibernate";
  private static final String FALSE = "false";
  private static final String TRUE = "true";

  private SQLCommonModuleBuilder() {
    // Cannot be instantiated
  }

  public static JHipsterModuleBuilder sqlCommonModuleBuilder(
    JHipsterModuleProperties properties,
    DatabaseType databaseType,
    DockerImage dockerImage,
    DocumentationTitle documentationTitle,
    ArtifactId testContainerArtifactId
  ) {
    Assert.notNull("properties", properties);
    Assert.notNull("databaseType", databaseType);
    Assert.notNull("dockerImage", dockerImage);
    Assert.notNull("documentationTitle", documentationTitle);
    Assert.notNull("testContainerArtifactId", testContainerArtifactId);

    String databaseId = databaseType.id();
    JHipsterSource source = from("server/springboot/database/" + databaseType.id());
    JHipsterDestination mainDestination = toSrcMainJava()
      .append(properties.basePackage().path())
      .append("technical/infrastructure/secondary/")
      .append(databaseId);

    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .put("applicationName", properties.projectBaseName().capitalized())
        .put("srcMainDocker", "src/main/docker") // To be used in <databaseId>>.md file
        .put(databaseId + "DockerImageWithVersion", dockerImage.fullName()) // To be used in <databaseId>.yml docker-compose file
        .and()
      .documentation(documentationTitle, source.template(databaseId + ".md"))
      .files()
        .add(source.template("DatabaseConfiguration.java"), mainDestination.append("DatabaseConfiguration.java"))
        .add(source.template(databaseId + ".yml"), toSrcMainDocker().append(databaseId + ".yml"))
        .and()
      .javaDependencies()
        .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter-data-jpa"))
        .addDependency(groupId("com.zaxxer"), artifactId("HikariCP"))
        .addDependency(groupId(ORG_HIBERNATE), artifactId("hibernate-core"))
        .addDependency(testContainer(testContainerArtifactId))
        .and()
      .springMainProperties()
        .set(propertyKey("spring.datasource.password"), propertyValue(""))
        .set(propertyKey("spring.datasource.type"), propertyValue("com.zaxxer.hikari.HikariDataSource"))
        .set(propertyKey("spring.datasource.hikari.poolName"), propertyValue("Hikari"))
        .set(propertyKey("spring.datasource.hikari.auto-commit"), propertyValue(FALSE))
        .set(propertyKey("spring.data.jpa.repositories.bootstrap-mode"), propertyValue("deferred"))
        .set(propertyKey("spring.jpa.hibernate.ddl-auto"), propertyValue("none"))
        .set(
          propertyKey("spring.jpa.hibernate.naming.implicit-strategy"),
          propertyValue("org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy")
        )
        .set(
          propertyKey("spring.jpa.hibernate.naming.physical-strategy"),
          propertyValue("org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy")
        )
        .set(propertyKey("spring.jpa.open-in-view"), propertyValue(FALSE))
        .set(propertyKey("spring.jpa.properties.hibernate.connection.provider_disables_autocommit"), propertyValue(TRUE))
        .set(propertyKey("spring.jpa.properties.hibernate.generate_statistics"), propertyValue(FALSE))
        .set(propertyKey("spring.jpa.properties.hibernate.id.new_generator_mappings"), propertyValue(TRUE))
        .set(propertyKey("spring.jpa.properties.hibernate.jdbc.batch_size"), propertyValue("25"))
        .set(propertyKey("spring.jpa.properties.hibernate.jdbc.time_zone"), propertyValue("UTC"))
        .set(propertyKey("spring.jpa.properties.hibernate.order_inserts"), propertyValue(TRUE))
        .set(propertyKey("spring.jpa.properties.hibernate.order_updates"), propertyValue(TRUE))
        .set(propertyKey("spring.jpa.properties.hibernate.query.fail_on_pagination_over_collection_fetch"), propertyValue(TRUE))
        .set(propertyKey("spring.jpa.properties.hibernate.query.in_clause_parameter_padding"), propertyValue(TRUE))
        .and()
      .springTestProperties()
        .set(
          propertyKey("spring.datasource.url"),
          propertyValue("jdbc:tc:" + dockerImage.fullName() + ":///" + properties.projectBaseName().name())
        )
        .set(propertyKey("spring.datasource.username"), propertyValue(properties.projectBaseName().name()))
        .set(propertyKey("spring.datasource.password"), propertyValue(""))
        .set(propertyKey("spring.datasource.driver-class-name"), propertyValue("org.testcontainers.jdbc.ContainerDatabaseDriver"))
        .set(propertyKey("spring.datasource.hikari.maximum-pool-size"), propertyValue("2"))
        .and()
      .optionalReplacements()
        .in("src/main/resources/logback-spring.xml")
          .add(text(NEEDLE_LOGBACK_LOGGER), logger("org.hibernate.validator", Level.WARN))
          .add(text(NEEDLE_LOGBACK_LOGGER), logger(ORG_HIBERNATE, Level.WARN))
          .add(text(NEEDLE_LOGBACK_LOGGER), logger("org.hibernate.ejb.HibernatePersistence", Level.OFF))
          .and()
        .in("src/test/resources/logback.xml")
          .add(text(NEEDLE_LOGBACK_LOGGER), logger("org.hibernate.validator", Level.WARN))
          .add(text(NEEDLE_LOGBACK_LOGGER), logger(ORG_HIBERNATE, Level.WARN))
          .add(text(NEEDLE_LOGBACK_LOGGER), logger("org.hibernate.ejb.HibernatePersistence", Level.OFF))
          .add(text(NEEDLE_LOGBACK_LOGGER), logger("com.github.dockerjava", Level.WARN))
          .add(text(NEEDLE_LOGBACK_LOGGER), logger("org.testcontainers", Level.WARN))
          .and().
        and();
    //@formatter:on
  }

  private static JavaDependency testContainer(ArtifactId testContainerArtifactI) {
    return javaDependency()
      .groupId("org.testcontainers")
      .artifactId(testContainerArtifactI)
      .versionSlug("testcontainers")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  public static String logger(String loggerName, Level level) {
    return String.format("<logger name=\"%s\" level=\"%s\" />", loggerName, level.toString()) + LF + "  " + NEEDLE_LOGBACK_LOGGER;
  }
}
