package com.seed4j.generator.server.javatool.javaenum.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class JavaEnumsModuleFactory {

  private static final String BASE_PACKAGE = "shared/enumeration";

  private static final SeedSource SOURCE = from("server/javatool/enums");
  private static final SeedSource MAIN_SOURCE = SOURCE.append("main");
  private static final SeedSource TEST_SOURCE = SOURCE.append("test");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    SeedDestination mainDestination = toSrcMainJava().append(packagePath).append(BASE_PACKAGE);
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
