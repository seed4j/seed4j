package tech.jhipster.lite.generator.server.springboot.database.mariadb.domain;

import static tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonModuleBuilder.sqlCommonModuleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class MariaDBModuleFactory {

  private final DockerImages dockerImages;

  public MariaDBModuleFactory(DockerImages dockerImages) {
    Assert.notNull("dockerImages", dockerImages);

    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    return sqlCommonModuleBuilder(
      properties,
      DatabaseType.MARIADB,
      dockerImages.get("mariadb"),
      documentationTitle("MariaDB"),
      artifactId("mariadb")
    )
      .javaDependencies()
      .addDependency(groupId("org.mariadb.jdbc"), artifactId("mariadb-java-client"))
      .and()
      .springMainProperties()
      .set(propertyKey("spring.datasource.url"), propertyValue("jdbc:mariadb://localhost:3306/" + properties.projectBaseName().name()))
      .set(propertyKey("spring.datasource.username"), propertyValue("root"))
      .set(propertyKey("spring.datasource.driver-class-name"), propertyValue("org.mariadb.jdbc.Driver"))
      .and()
      .build();
  }
}
