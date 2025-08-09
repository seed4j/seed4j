package com.seed4j.generator.server.springboot.apidocumentation.springdocjwt.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SpringdocJwtModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/apidocumentation/springdocjwt");

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(
          SOURCE.template("SpringdocJWTConfiguration.java"),
          toSrcMainJava()
            .append(properties.packagePath())
            .append("wire/springdoc/infrastructure/primary")
            .append("SpringdocJWTConfiguration.java")
        )
        .and()
      .build();
    // @formatter:on
  }
}
