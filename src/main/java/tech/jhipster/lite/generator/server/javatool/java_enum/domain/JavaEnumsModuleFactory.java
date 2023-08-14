package tech.jhipster.lite.generator.server.javatool.java_enum.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JavaEnumsModuleFactory {

  private static final String BASE_PACKAGE = "common/domain";

  private static final JHipsterSource SOURCE = from("server/javatool/enums");
  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .batch(MAIN_SOURCE, toSrcMainJava().append(packagePath).append(BASE_PACKAGE))
          .addTemplate("Enums.java")
          .addTemplate("UnmappableEnumException.java")
          .and()
        .add(TEST_SOURCE.template("EnumsTest.java"), toSrcTestJava().append(packagePath).append(BASE_PACKAGE).append("EnumsTest.java"))
        .and()
      .build();
    //@formatter:on
  }
}
