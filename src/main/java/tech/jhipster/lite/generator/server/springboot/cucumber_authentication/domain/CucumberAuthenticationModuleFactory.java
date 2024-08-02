package tech.jhipster.lite.generator.server.springboot.cucumber_authentication.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class CucumberAuthenticationModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/cucumberauthentication");
  private static final JHipsterSource OAUTH2_SOURCE = SOURCE.append("oauth2");
  private static final JHipsterSource JWT_SOURCE = SOURCE.append("jwt");

  private static final GroupId JSON_WEBTOKEN_GROUP = groupId("io.jsonwebtoken");
  private static final VersionSlug JSON_WEBTOKEN_VERSION = versionSlug("json-web-token");

  private static final String AUTHENTICATION_STEP = "shared/authentication/infrastructure/primary/AuthenticationSteps.java";

  public JHipsterModule buildOauth2Module(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String mainClass = properties.projectBaseName().capitalized() + "App";
    String cucumberConfigurationNeedle = "classes = { " + mainClass + ".class";
    String importNeedle = "import " + properties.basePackage().get() + "." + mainClass + ";";
    String packagePath = properties.packagePath();

    //@formatter:off
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
    //@formatter:on
  }

  private String cucumberTestClasses(String cucumberConfigurationNeedle) {
    return cucumberConfigurationNeedle + ", TestSecurityConfiguration.class, CucumberAuthenticationConfiguration.class";
  }

  private String securityConfigurationImport(JHipsterModuleProperties properties) {
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

  public JHipsterModule buildJWTModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Cucumber authentication"), SOURCE.file("cucumber-authentication.md"))
      .files()
        .add(
          JWT_SOURCE.template("AuthenticationSteps.java"),
          toSrcTestJava().append(properties.packagePath()).append(AUTHENTICATION_STEP)
        )
        .and()
      .build();
    //@formatter:on
  }
}
