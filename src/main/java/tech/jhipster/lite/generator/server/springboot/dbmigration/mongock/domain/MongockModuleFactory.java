package tech.jhipster.lite.generator.server.springboot.dbmigration.mongock.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyType;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class MongockModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/dbmigration/mongock");

  private static final GroupId MONGOCK_GROUP = groupId("io.mongock");

  private static final String MONGOCK_SECONDARY = "wire/mongock/infrastructure/secondary";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    //@formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependencyManagement(mongockBom())
        .addDependency(MONGOCK_GROUP, artifactId("mongock-springboot"))
        .addDependency(MONGOCK_GROUP, artifactId("mongodb-springdata-v4-driver"))
        .and()
      .documentation(documentationTitle("Mongock"), SOURCE.append("mongock.md"))
      .files()
        .add(
          SOURCE.template("MongockDatabaseConfiguration.java"),
          toSrcMainJava().append(packagePath).append(MONGOCK_SECONDARY).append("MongockDatabaseConfiguration.java")
        )
        .and()
      .springMainProperties()
        .set(propertyKey("mongock.migration-scan-package"), propertyValue(properties.basePackage().get()))
        .and()
      .springTestProperties()
        .set(propertyKey("mongock.migration-scan-package"), propertyValue(properties.basePackage().get()))
        .and()
      .build();
    //@formatter:on
  }

  private JavaDependency mongockBom() {
    return javaDependency()
      .groupId(MONGOCK_GROUP)
      .artifactId("mongock-bom")
      .versionSlug("mongock")
      .scope(JavaDependencyScope.IMPORT)
      .type(JavaDependencyType.POM)
      .build();
  }
}
