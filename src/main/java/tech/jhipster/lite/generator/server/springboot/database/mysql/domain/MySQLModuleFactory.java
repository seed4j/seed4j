package tech.jhipster.lite.generator.server.springboot.database.mysql.domain;

import static tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonModuleBuilder.sqlCommonModuleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.generator.server.springboot.database.common.domain.DatabaseType;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class MySQLModuleFactory {

  private static final String MYSQL = "mysql";
  private static final String MYSQL_GROUPID = "com.mysql";
  private static final String MYSQL_ARTIFACTID = "mysql-connector-j";

  private final DockerImages dockerImages;

  public MySQLModuleFactory(DockerImages dockerImages) {
    Assert.notNull("dockerImages", dockerImages);

    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    return sqlCommonModuleBuilder(properties, DatabaseType.MYSQL, dockerImages.get(MYSQL), documentationTitle("MySQL"), artifactId(MYSQL))
      .javaDependencies()
      .addDependency(javaDependency().groupId(MYSQL_GROUPID).artifactId(MYSQL_ARTIFACTID).scope(JavaDependencyScope.RUNTIME).build())
      .and()
      .springMainProperties()
      .set(propertyKey("spring.datasource.url"), propertyValue("jdbc:mysql://localhost:3306/" + properties.projectBaseName().name()))
      .set(propertyKey("spring.datasource.username"), propertyValue("root"))
      .set(propertyKey("spring.datasource.driver-class-name"), propertyValue("com.mysql.cj.jdbc.Driver"))
      .and()
      .build();
  }
}
