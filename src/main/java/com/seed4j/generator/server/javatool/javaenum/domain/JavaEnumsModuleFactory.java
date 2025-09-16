package com.seed4j.generator.server.javatool.javaenum.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class JavaEnumsModuleFactory {

  private static final String BASE_PACKAGE = "shared/enumeration";

  private static final Seed4JSource SOURCE = from("server/javatool/enums");
  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    Seed4JDestination mainDestination = toSrcMainJava().append(packagePath).append(BASE_PACKAGE);
    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .batch(MAIN_SOURCE, mainDestination.append("domain"))
          .addTemplate("Enums.java")
          .addTemplate("UnmappableEnumException.java")
          .and()
        .add(MAIN_SOURCE.template("package-info.java"), mainDestination.append("package-info.java"))
        .add(TEST_SOURCE.template("EnumsTest.java"), toSrcTestJava().append(packagePath).append(BASE_PACKAGE).append("domain").append("EnumsTest.java"))
        .and()
      .build();
    // @formatter:on
  }
}
