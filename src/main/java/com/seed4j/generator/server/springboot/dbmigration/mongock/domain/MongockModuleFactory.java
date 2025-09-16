package com.seed4j.generator.server.springboot.dbmigration.mongock.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.javaDependency;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.javadependency.JavaDependencyType;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class MongockModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/dbmigration/mongock");

  private static final GroupId MONGOCK_GROUP = groupId("io.mongock");

  private static final String MONGOCK_SECONDARY = "wire/mongock/infrastructure/secondary";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
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
