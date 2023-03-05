package tech.jhipster.lite.generator.server.springboot.mvc.security.kipe.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class KipeExpressionModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/security/kipe/expression");
  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    JHipsterDestination mainDestination = toSrcMainJava().append(properties.packagePath()).append("kipe");
    JHipsterDestination testjavaDestination = toSrcTestJava().append(properties.packagePath()).append("kipe");

    //@formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Kipe expression"), SOURCE.template("kipe-expression.md"))
      .files()
        .add(MAIN_SOURCE.template("package-info.java"), mainDestination.append("package-info.java"))
        .batch(MAIN_SOURCE, mainDestination.append("application"))
          .addTemplate("AccessChecker.java")
          .addTemplate("AccessContext.java")
          .addTemplate("AccessContextFactory.java")
          .addTemplate("AccessEvaluator.java")
          .addTemplate("ElementAccessContext.java")
          .addTemplate("KipeConfiguration.java")
          .addTemplate("KipeMethodSecurityExpressionHandler.java")
          .addTemplate("KipeMethodSecurityExpressionRoot.java")
          .addTemplate("NullElementAccessContext.java")
          .addTemplate("ObjectAccessChecker.java")
          .and()
        .batch(TEST_SOURCE, testjavaDestination.append("application"))
          .addTemplate("AccessCheckerIntTest.java")
          .addTemplate("AccessContextFactoryTest.java")
          .addTemplate("AccessEvaluatorTest.java")
          .addTemplate("KipeApplicationService.java")
          .addTemplate("KipeDummyAccessChecker.java")
          .addTemplate("KipeIntTest.java")
          .addTemplate("ObjectAccessCheckerTest.java")
          .and()
        .batch(TEST_SOURCE, testjavaDestination.append("domain"))
          .addTemplate("KipeDummy.java")
          .addTemplate("KipeDummyChild.java")
          .and()
        .and()
      .build();
    //@formatter:on
  }
}
