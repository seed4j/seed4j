package com.seed4j.generator.server.springboot.mvc.security.kipe.domain;

import static com.seed4j.module.domain.SeedModule.documentationTitle;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.toSrcMainJava;
import static com.seed4j.module.domain.SeedModule.toSrcTestJava;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class KipeAuthorizationModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/mvc/security/kipe/authorization");
  private static final SeedSource MAIN_SOURCE = SOURCE.append("main");
  private static final SeedSource TEST_SOURCE = SOURCE.append("test");

  private static final String APPLICATION = "application";
  private static final String KIPE_DESTINATION = "shared/kipe";

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String baseName = properties.projectBaseName().capitalized();
    SeedDestination mainDestination = toSrcMainJava().append(properties.packagePath()).append(KIPE_DESTINATION);
    SeedDestination testDestination = toSrcTestJava().append(properties.packagePath()).append(KIPE_DESTINATION);

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
