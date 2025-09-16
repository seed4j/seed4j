package com.seed4j.generator.server.javatool.archunit.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.javaDependency;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class ArchUnitModuleFactory {

  private static final Seed4JSource SOURCE = from("server/javatool/archunit/test");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    Seed4JDestination testDestination = toSrcTestJava().append(packagePath);

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.template("archunit.properties"), to("src/test/resources/archunit.properties"))
        .add(SOURCE.template("AnnotationArchTest.java"), testDestination.append("AnnotationArchTest.java"))
        .add(SOURCE.template("HexagonalArchTest.java"), testDestination.append("HexagonalArchTest.java"))
        .add(SOURCE.template("EqualsHashcodeArchTest.java"), testDestination.append("EqualsHashcodeArchTest.java"))
        .and()
      .javaDependencies()
        .addDependency(archUnitDependency())
        .and()
      .springTestLogger("com.tngtech.archunit", LogLevel.WARN)
      .build();
    // @formatter:on
  }

  private JavaDependency archUnitDependency() {
    return javaDependency()
      .groupId("com.tngtech.archunit")
      .artifactId("archunit-junit5-api")
      .versionSlug("archunit-junit5.version")
      .scope(JavaDependencyScope.TEST)
      .build();
  }
}
