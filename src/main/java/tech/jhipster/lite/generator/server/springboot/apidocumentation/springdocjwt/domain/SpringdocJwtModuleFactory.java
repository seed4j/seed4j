package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocjwt.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class SpringdocJwtModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/apidocumentation/springdocjwt");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
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
    //@formatter:on
  }
}
