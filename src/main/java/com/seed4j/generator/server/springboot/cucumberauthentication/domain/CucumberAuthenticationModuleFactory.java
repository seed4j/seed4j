package com.seed4j.generator.server.springboot.cucumberauthentication.domain;

import static com.seed4j.module.domain.SeedModule.documentationTitle;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.groupId;
import static com.seed4j.module.domain.SeedModule.javaDependency;
import static com.seed4j.module.domain.SeedModule.lineBeforeText;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.path;
import static com.seed4j.module.domain.SeedModule.text;
import static com.seed4j.module.domain.SeedModule.toSrcTestJava;
import static com.seed4j.module.domain.SeedModule.versionSlug;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class CucumberAuthenticationModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/cucumberauthentication");
  private static final SeedSource OAUTH2_SOURCE = SOURCE.append("oauth2");
  private static final SeedSource JWT_SOURCE = SOURCE.append("jwt");

  private static final GroupId JSON_WEBTOKEN_GROUP = groupId("io.jsonwebtoken");
  private static final VersionSlug JSON_WEBTOKEN_VERSION = versionSlug("json-web-token");

  private static final String AUTHENTICATION_STEP = "shared/authentication/infrastructure/primary/AuthenticationSteps.java";

  public SeedModule buildOauth2Module(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String mainClass = properties.projectBaseName().capitalized() + "App";
    String cucumberConfigurationNeedle = "classes = { " + mainClass + ".class";
    String importNeedle = "import " + properties.basePackage().get() + "." + mainClass + ";";
    String packagePath = properties.packagePath();

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Cucumber authentication"), SOURCE.file("cucumber-authentication.md"))
      .javaDependencies()
        .addDependency(jsonWebTokenDependency("jjwt-api"))
        .addDependency(jsonWebTokenDependency("jjwt-impl"))
        .addDependency(jsonWebTokenDependency("jjwt-jackson"))
        .and()
      .mandatoryReplacements()
        .in(path("src/test/java").append(packagePath).append("cucumber/CucumberConfiguration.java"))
          .add(text(cucumberConfigurationNeedle), cucumberTestClasses(cucumberConfigurationNeedle))
          .add(lineBeforeText(importNeedle), securityConfigurationImport(properties))
          .and()
        .and()
      .files()
        .add(
          OAUTH2_SOURCE.template("AuthenticationSteps.java"),
          toSrcTestJava().append(packagePath).append(AUTHENTICATION_STEP)
        )
        .add(
          OAUTH2_SOURCE.template("CucumberAuthenticationConfiguration.java"),
          toSrcTestJava().append(packagePath).append("cucumber/CucumberAuthenticationConfiguration.java")
        )
        .and()
      .build();
    // @formatter:on
  }

  private String cucumberTestClasses(String cucumberConfigurationNeedle) {
    return cucumberConfigurationNeedle + ", TestSecurityConfiguration.class, CucumberAuthenticationConfiguration.class";
  }

  private String securityConfigurationImport(SeedModuleProperties properties) {
    return "import " + properties.basePackage().get() + ".shared.authentication.infrastructure.primary.TestSecurityConfiguration;";
  }

  private JavaDependency jsonWebTokenDependency(String artifactId) {
    return javaDependency()
      .groupId(JSON_WEBTOKEN_GROUP)
      .artifactId(artifactId)
      .versionSlug(JSON_WEBTOKEN_VERSION)
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  public SeedModule buildJWTModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Cucumber authentication"), SOURCE.file("cucumber-authentication.md"))
      .files()
        .add(
          JWT_SOURCE.template("AuthenticationSteps.java"),
          toSrcTestJava().append(properties.packagePath()).append(AUTHENTICATION_STEP)
        )
        .and()
      .build();
    // @formatter:on
  }
}
