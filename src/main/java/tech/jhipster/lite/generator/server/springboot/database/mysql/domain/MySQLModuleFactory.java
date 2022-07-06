package tech.jhipster.lite.generator.server.springboot.database.mysql.domain;

import static tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonModuleBuilder.sqlCommonModuleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class MySQLModuleFactory {

  private final DockerImages dockerImages;

  public MySQLModuleFactory(DockerImages dockerImages) {
    Assert.notNull("dockerImages", dockerImages);

    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    return sqlCommonModuleBuilder(
      properties,
      DatabaseType.MYSQL,
      dockerImages.get("mysql"),
      documentationTitle("MySQL"),
      artifactId("mysql")
    )
      .javaDependencies()
      .addDependency(groupId("mysql"), artifactId("mysql-connector-java"))
      .and()
      .springMainProperties()
      .set(propertyKey("spring.datasource.url"), propertyValue("jdbc:mysql://localhost:3306/" + properties.projectBaseName().name()))
      .set(propertyKey("spring.datasource.username"), propertyValue("root"))
      .set(propertyKey("spring.datasource.driver-class-name"), propertyValue("com.mysql.jdbc.Driver"))
      .and()
      .build();
  }
}
