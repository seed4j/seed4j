package com.seed4j.generator.server.springboot.dbmigration.mongock.domain;

import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.javaDependency;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.propertyKey;
import static com.seed4j.module.domain.JHipsterModule.propertyValue;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.javadependency.JavaDependencyType;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class MongockModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/dbmigration/mongock");

  private static final GroupId MONGOCK_GROUP = groupId("io.mongock");

  private static final String MONGOCK_SECONDARY = "wire/mongock/infrastructure/secondary";

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    // @formatter:off
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
      .build();
    // @formatter:on
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
