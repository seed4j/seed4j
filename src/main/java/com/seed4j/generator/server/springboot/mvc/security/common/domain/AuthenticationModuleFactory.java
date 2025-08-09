package com.seed4j.generator.server.springboot.mvc.security.common.domain;

import static com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.lineBeforeText;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.path;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;

import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.module.domain.replacement.TextNeedleBeforeReplacer;
import com.seed4j.shared.error.domain.Assert;

public final class AuthenticationModuleFactory {

  private static final String AUTHENTICATION_DESTINATION = "shared/authentication";

  private static final SeedSource SOURCE = from("server/springboot/mvc/security/common");

  private static final SeedSource MAIN_SOURCE = SOURCE.append("main");
  private static final SeedSource TEST_SOURCE = SOURCE.append("test");

  private static final GroupId SPRING_GROUP = groupId("org.springframework.boot");

  private static final String APPLICATION = "application";
  private static final String DOMAIN = "domain";
  private static final String PRIMARY = "infrastructure/primary";

  private static final TextNeedleBeforeReplacer IMPORT_NEEDLE = lineBeforeText(
    "import org.springframework.boot.test.context.SpringBootTest;"
  );
  private static final TextNeedleBeforeReplacer ANNOTATION_NEEDLE = lineBeforeText("@Target(ElementType.TYPE)");

  private AuthenticationModuleFactory() {}

  public static JHipsterModuleBuilder authenticationModuleBuilder(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.basePackage().path();
    SeedDestination mainDestination = toSrcMainJava().append(packagePath).append(AUTHENTICATION_DESTINATION);
    SeedDestination testDestination = toSrcTestJava().append(packagePath).append(AUTHENTICATION_DESTINATION);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("baseName", properties.projectBaseName().capitalized())
        .and()
      .javaDependencies()
        .addDependency(SPRING_GROUP, artifactId("spring-boot-starter-security"))
        .addDependency(springSecurityTest())
        .and()
      .files()
        .add(MAIN_SOURCE.template("package-info.java"), mainDestination.append("package-info.java"))
          .batch(MAIN_SOURCE.append(DOMAIN), mainDestination.append(DOMAIN))
          .addTemplate("Role.java")
          .addTemplate("Roles.java")
          .addTemplate("Username.java")
          .and()
        .batch(MAIN_SOURCE.append(APPLICATION), mainDestination.append(APPLICATION))
          .addTemplate("AuthenticationException.java")
          .addTemplate("NotAuthenticatedUserException.java")
          .addTemplate("UnknownAuthenticationException.java")
          .and()
        .add(MAIN_SOURCE.append(PRIMARY).template("AuthenticationExceptionAdvice.java"), mainDestination.append(PRIMARY).append("AuthenticationExceptionAdvice.java"))
        .batch(TEST_SOURCE.append(DOMAIN), testDestination.append(DOMAIN))
          .addTemplate("RolesTest.java")
          .addTemplate("RoleTest.java")
          .addTemplate("UsernameTest.java")
          .and()
        .batch(TEST_SOURCE.append(PRIMARY), testDestination.append(PRIMARY))
          .addTemplate("AccountExceptionResource.java")
          .addTemplate("AuthenticationExceptionAdviceIT.java")
          .and()
        .and()
      .mandatoryReplacements()
        .in(path("src/test/java").append(properties.packagePath()).append("IntegrationTest.java"))
          .add(IMPORT_NEEDLE, "import org.springframework.security.test.context.support.WithMockUser;")
          .add(ANNOTATION_NEEDLE, "@WithMockUser")
          .and()
      .and();
    // @formatter:on
  }

  private static JavaDependency springSecurityTest() {
    return JavaDependency.builder()
      .groupId("org.springframework.security")
      .artifactId("spring-security-test")
      .scope(JavaDependencyScope.TEST)
      .build();
  }
}
