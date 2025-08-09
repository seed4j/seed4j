package com.seed4j.generator.server.springboot.mvc.security.kipe.domain;

import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class KipeExpressionModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/mvc/security/kipe/expression");
  private static final SeedSource MAIN_SOURCE = SOURCE.append("main");
  private static final SeedSource TEST_SOURCE = SOURCE.append("test");

  private static final String KIPE_DESTINATION = "shared/kipe";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    SeedDestination mainDestination = toSrcMainJava().append(properties.packagePath()).append(KIPE_DESTINATION);
    SeedDestination testDestination = toSrcTestJava().append(properties.packagePath()).append(KIPE_DESTINATION);

    // @formatter:off
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
        .batch(TEST_SOURCE, testDestination.append("application"))
          .addTemplate("AccessCheckerIT.java")
          .addTemplate("AccessContextFactoryTest.java")
          .addTemplate("AccessEvaluatorTest.java")
          .addTemplate("KipeApplicationService.java")
          .addTemplate("KipeDummyAccessChecker.java")
          .addTemplate("KipeIT.java")
          .addTemplate("ObjectAccessCheckerTest.java")
          .and()
        .batch(TEST_SOURCE, testDestination.append("domain"))
          .addTemplate("KipeDummy.java")
          .addTemplate("KipeDummyChild.java")
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
