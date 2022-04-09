package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security.oauth2AccountContextFiles;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security.oauth2AccountContextTestFiles;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security.properties;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security.propertiesForTests;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.Constants.POM_XML;
import static tech.jhipster.lite.generator.tools.domain.Constants.TECHNICAL_INFRASTRUCTURE_PRIMARY;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.DEFAULT_PACKAGE_NAME;

import java.util.List;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security;
import tech.jhipster.lite.generator.tools.domain.DefaultConfig;
import tech.jhipster.lite.generator.tools.domain.Project;

public class OAuth2SecurityAssert {

  private OAuth2SecurityAssert() {}

  public static void assertSecurityDependencies(Project project) {
    assertFileContent(project, POM_XML, securityDependency());
    assertFileContent(project, POM_XML, oauth2ClientDependency());
    assertFileContent(project, POM_XML, oauth2ResourceServerDependency());

    assertFileContent(project, POM_XML, securityTestDependency());
  }

  public static void assertDockerKeycloak(Project project) {
    assertFileExist(project, "src/main/docker/keycloak.yml");
    assertFileExist(project, "src/main/docker/keycloak-realm-config/jhipster-realm.json");
    assertFileExist(project, "src/main/docker/keycloak-realm-config/jhipster-users-0.json");

    assertFileContent(project, "src/main/docker/keycloak.yml", "jboss/keycloak:16.1.1");
  }

  public static void assertJavaFiles(Project project) {
    String oauth2Path = getPath(project.getPackageNamePath().orElse(DefaultConfig.PACKAGE_PATH));

    // main java files
    OAuth2Security
      .oauth2SecurityFiles()
      .forEach((javaFile, destination) -> assertFileExist(project, getPath(MAIN_JAVA, oauth2Path, destination, javaFile)));
    OAuth2Security
      .oauth2TestSecurityFiles()
      .forEach((javaFile, destination) -> assertFileExist(project, getPath(TEST_JAVA, oauth2Path, destination, javaFile)));
  }

  public static void assertProperties(Project project) {
    properties().forEach((k, v) -> assertFileContent(project, getPath(MAIN_RESOURCES, "config/application.properties"), k + "=" + v));
    propertiesForTests()
      .forEach((k, v) -> assertFileContent(project, getPath(TEST_RESOURCES, "config/application.properties"), k + "=" + v));
  }

  public static void assertExceptionTranslatorWithSecurity(Project project) {
    String path = getPath(project.getPackageNamePath().orElse("com/mycompany/myapp"), TECHNICAL_INFRASTRUCTURE_PRIMARY, "exception");
    String packageName = project.getPackageName().orElse(DEFAULT_PACKAGE_NAME);

    assertFileContent(
      project,
      getPath(MAIN_JAVA, path, "ExceptionTranslator.java"),
      "import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;"
    );
    assertFileContent(
      project,
      getPath(MAIN_JAVA, path, "ExceptionTranslator.java"),
      String.format("import %s.error.domain.AccountException;", packageName)
    );
    assertFileContent(
      project,
      getPath(MAIN_JAVA, path, "ExceptionTranslator.java"),
      "public class ExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {"
    );
    assertFileContent(
      project,
      getPath(MAIN_JAVA, path, "ExceptionTranslator.java"),
      "public ResponseEntity<Problem> handleAccountException"
    );

    assertFileContent(project, getPath(TEST_JAVA, path, "ExceptionTranslatorTestController.java"), "@GetMapping(\"/account-exception\")");

    assertFileContent(project, getPath(TEST_JAVA, path, "ExceptionTranslatorTestController.java"), "public void accountException()");
    assertFileContent(
      project,
      getPath(TEST_JAVA, path, "ExceptionTranslatorIT.java"),
      "void shouldHandleAccountException() throws Exception"
    );
  }

  public static void assertIntegrationTestWithSecurity(Project project) {
    String path = getPath(project.getPackageNamePath().orElse(DefaultConfig.PACKAGE_PATH));
    String integrationTestFile = getPath(TEST_JAVA, path, "IntegrationTest.java");

    assertFileContent(project, integrationTestFile, "import org.springframework.boot.test.context.SpringBootTest;");

    assertFileContent(project, integrationTestFile, "import org.springframework.security.test.context.support.WithMockUser;");
    assertFileContent(project, integrationTestFile, "@WithMockUser");

    String packageName = project.getPackageName().orElse(DefaultConfig.DEFAULT_PACKAGE_NAME);
    assertFileContent(
      project,
      integrationTestFile,
      "import " + packageName + ".security.oauth2.infrastructure.config.TestSecurityConfiguration;"
    );
    assertFileContent(project, integrationTestFile, "TestSecurityConfiguration.class");
  }

  public static List<String> securityDependency() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.boot</groupId>",
      "<artifactId>spring-boot-starter-security</artifactId>",
      "</dependency>"
    );
  }

  public static List<String> securityTestDependency() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.security</groupId>",
      "<artifactId>spring-security-test</artifactId>",
      "<scope>test</scope>",
      "</dependency>"
    );
  }

  public static List<String> oauth2ClientDependency() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.boot</groupId>",
      "<artifactId>spring-boot-starter-oauth2-client</artifactId>",
      "</dependency>"
    );
  }

  public static List<String> oauth2ResourceServerDependency() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.boot</groupId>",
      "<artifactId>spring-boot-starter-oauth2-resource-server</artifactId>",
      "</dependency>"
    );
  }

  public static void assertAccountFiles(Project project) {
    String path = getPath(project.getPackageNamePath().orElse(DefaultConfig.PACKAGE_PATH));

    // main java files
    oauth2AccountContextFiles()
      .forEach((javaFile, destination) -> assertFileExist(project, getPath(MAIN_JAVA, path, destination, javaFile)));

    // test java files
    oauth2AccountContextTestFiles()
      .forEach((javaFile, destination) -> assertFileExist(project, getPath(TEST_JAVA, path, destination, javaFile)));
  }
}
