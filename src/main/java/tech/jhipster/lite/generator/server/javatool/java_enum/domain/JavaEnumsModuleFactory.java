package tech.jhipster.lite.generator.server.javatool.java_enum.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JavaEnumsModuleFactory {

  private static final String BASE_PACKAGE = "shared/enumeration";

  private static final JHipsterSource SOURCE = from("server/javatool/enums");
  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    JHipsterDestination mainDestination = toSrcMainJava().append(packagePath).append(BASE_PACKAGE);
    //@formatter:off
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
    //@formatter:on
  }
}
