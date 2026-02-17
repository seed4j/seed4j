package com.seed4j.generator.server.springboot.mvc.security.common.domain;

import static com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.lineBeforeText;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.path;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.replacement.TextNeedleBeforeReplacer;
import com.seed4j.shared.error.domain.Assert;

public final class AuthenticationModuleFactory {

  private static final String AUTHENTICATION_DESTINATION = "shared/authentication";

  private static final Seed4JSource SOURCE = from("server/springboot/mvc/security/common");

  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");

  private static final GroupId SPRING_GROUP = groupId("org.springframework.boot");

  private static final String APPLICATION = "application";
  private static final String DOMAIN = "domain";
  private static final String PRIMARY = "infrastructure/primary";

  private static final TextNeedleBeforeReplacer IMPORT_NEEDLE = lineBeforeText(
    "import org.springframework.boot.test.context.SpringBootTest;"
  );
  private static final TextNeedleBeforeReplacer ANNOTATION_NEEDLE = lineBeforeText("@Target(ElementType.TYPE)");

  private AuthenticationModuleFactory() {}

  public static Seed4JModuleBuilder authenticationModuleBuilder(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.basePackage().path();
    Seed4JDestination mainDestination = toSrcMainJava().append(packagePath).append(AUTHENTICATION_DESTINATION);
    Seed4JDestination testDestination = toSrcTestJava().append(packagePath).append(AUTHENTICATION_DESTINATION);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("baseName", properties.projectBaseName().capitalized())
        .and()
      .javaDependencies()
        .addDependency(SPRING_GROUP, artifactId("spring-boot-starter-security"))
        .addTestDependency(SPRING_GROUP, artifactId("spring-boot-starter-security-test"))
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
}
