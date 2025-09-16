package com.seed4j.generator.server.springboot.mvc.security.kipe.domain;

import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class KipeExpressionModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/mvc/security/kipe/expression");
  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");

  private static final String KIPE_DESTINATION = "shared/kipe";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    Seed4JDestination mainDestination = toSrcMainJava().append(properties.packagePath()).append(KIPE_DESTINATION);
    Seed4JDestination testDestination = toSrcTestJava().append(properties.packagePath()).append(KIPE_DESTINATION);

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
