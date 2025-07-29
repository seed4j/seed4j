package com.seed4j.generator.server.javatool.archunit.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.javaDependency;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.to;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.file.JHipsterDestination;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class ArchUnitModuleFactory {

  private static final JHipsterSource SOURCE = from("server/javatool/archunit/test");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    JHipsterDestination testDestination = toSrcTestJava().append(packagePath);

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
