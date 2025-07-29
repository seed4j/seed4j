package com.seed4j.generator.server.springboot.mvc.security.kipe.domain;

import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.JHipsterDestination;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class KipeAuthorizationModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/security/kipe/authorization");
  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");

  private static final String APPLICATION = "application";
  private static final String KIPE_DESTINATION = "shared/kipe";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String baseName = properties.projectBaseName().capitalized();
    JHipsterDestination mainDestination = toSrcMainJava().append(properties.packagePath()).append(KIPE_DESTINATION);
    JHipsterDestination testDestination = toSrcTestJava().append(properties.packagePath()).append(KIPE_DESTINATION);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("baseName", baseName)
        .and()
      .documentation(documentationTitle("Kipe authorization"), SOURCE.template("kipe-authorization.md"))
      .files()
        .add(MAIN_SOURCE.template("package-info.java"), mainDestination.append("package-info.java"))
        .add(MAIN_SOURCE.template("ApplicationAuthorizations.java"), mainDestination.append(APPLICATION).append(baseName + "Authorizations.java"))
        .batch(MAIN_SOURCE, mainDestination.append("domain"))
          .addTemplate("Accesses.java")
          .addTemplate("Action.java")
          .addTemplate("Resource.java")
          .addTemplate("RolesAccesses.java")
          .and()
        .add(TEST_SOURCE.template("ApplicationAuthorizationsTest.java"), testDestination.append(APPLICATION).append(baseName + "AuthorizationsTest.java"))
        .add(TEST_SOURCE.template("TestAuthentications.java"), testDestination.append(APPLICATION).append("TestAuthentications.java"))
        .batch(TEST_SOURCE, testDestination.append("domain"))
          .addTemplate("RolesAccessesFixture.java")
          .addTemplate("RolesAccessesTest.java")
          .addTemplate("ActionTest.java")
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
