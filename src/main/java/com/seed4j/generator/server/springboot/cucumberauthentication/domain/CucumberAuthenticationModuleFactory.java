package com.seed4j.generator.server.springboot.cucumberauthentication.domain;

import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.javaDependency;
import static com.seed4j.module.domain.Seed4JModule.lineBeforeText;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.path;
import static com.seed4j.module.domain.Seed4JModule.text;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;
import static com.seed4j.module.domain.Seed4JModule.versionSlug;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class CucumberAuthenticationModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/cucumberauthentication");
  private static final Seed4JSource OAUTH2_SOURCE = SOURCE.append("oauth2");
  private static final Seed4JSource JWT_SOURCE = SOURCE.append("jwt");

  private static final GroupId JSON_WEBTOKEN_GROUP = groupId("io.jsonwebtoken");
  private static final VersionSlug JSON_WEBTOKEN_VERSION = versionSlug("json-web-token");

  private static final String AUTHENTICATION_STEP = "shared/authentication/infrastructure/primary/AuthenticationSteps.java";

  public Seed4JModule buildOauth2Module(Seed4JModuleProperties properties) {
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

  private String securityConfigurationImport(Seed4JModuleProperties properties) {
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

  public Seed4JModule buildJWTModule(Seed4JModuleProperties properties) {
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
