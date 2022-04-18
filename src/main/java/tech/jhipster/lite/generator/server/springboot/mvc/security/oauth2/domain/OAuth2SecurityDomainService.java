package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_DOCKER;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.DEFAULT_BASE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.DEFAULT_PACKAGE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_PATH;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security.TECHNICAL_INFRASTRUCTURE_PRIMARY_EXCEPTION;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security.getDockerKeycloakImageName;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security.oauth2AccountContextFiles;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security.oauth2AccountContextTestFiles;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security.oauth2SecurityFiles;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security.oauth2TestSecurityFiles;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security.properties;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security.propertiesForTests;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security.springBootStarterOAuth2ClientDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security.springBootStarterOAuth2ResourceServerDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security.springBootStarterSecurityDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2Security.springSecurityTestDependency;

import tech.jhipster.lite.common.domain.WordUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.mvc.security.common.domain.CommonSecurityService;

public record OAuth2SecurityDomainService(
  ProjectRepository projectRepository,
  BuildToolService buildToolService,
  SpringBootCommonService springBootCommonService,
  CommonSecurityService commonSecurityService,
  DockerService dockerService
)
  implements OAuth2SecurityService {
  public static final String SOURCE = "server/springboot/mvc/security/oauth2";
  public static final String SECURITY_OAUTH2_PATH = "security/oauth2";

  @Override
  public void addOAuth2(Project project) {
    addDependencies(project);
    addKeycloakDocker(project);
    addJavaFiles(project);
    addSpringBootProperties(project);

    updateExceptionTranslator(project);

    updateExceptionTranslatorWithAccountExceptionHandler(project);
    updateExceptionTranslatorTestController(project);
    updateExceptionTranslatorIT(project);

    updateIntegrationTestWithMockUser(project);
    updateIntegrationTestWithTestSecurityConfiguration(project);
  }

  private void addDependencies(Project project) {
    buildToolService.addDependency(project, springBootStarterSecurityDependency());
    buildToolService.addDependency(project, springBootStarterOAuth2ClientDependency());
    buildToolService.addDependency(project, springSecurityTestDependency());
    buildToolService.addDependency(project, springBootStarterOAuth2ResourceServerDependency());
  }

  private void addKeycloakDocker(Project project) {
    String keycloakVersion = dockerService
      .getImageVersion(getDockerKeycloakImageName())
      .orElseThrow(() -> {
        throw new GeneratorException("Version not found for docker image: " + getDockerKeycloakImageName());
      });
    String imageName = dockerService.getImageNameWithVersion(getDockerKeycloakImageName()).orElseThrow();

    project.addConfig("dockerKeycloakImage", imageName);
    project.addConfig("dockerKeycloakVersion", keycloakVersion);

    String dockerSourcePath = getPath(SOURCE, "docker");
    String dockerPathRealm = getPath(MAIN_DOCKER, "keycloak-realm-config");
    projectRepository.template(project, dockerSourcePath, "keycloak.yml", MAIN_DOCKER, "keycloak.yml");
    projectRepository.template(project, dockerSourcePath, "jhipster-realm.json", dockerPathRealm, "jhipster-realm.json");
    projectRepository.template(project, dockerSourcePath, "jhipster-users-0.json", dockerPathRealm, "jhipster-users-0.json");
  }

  private void addJavaFiles(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));

    String sourceSrc = getPath(SOURCE, "src");
    String destinationSrc = getPath(MAIN_JAVA, packageNamePath);
    oauth2SecurityFiles()
      .forEach((javaFile, folder) ->
        projectRepository.template(project, getPath(sourceSrc, folder), javaFile, getPath(destinationSrc, folder))
      );

    String sourceTest = getPath(SOURCE, "test");
    String destinationTest = getPath(TEST_JAVA, packageNamePath);
    oauth2TestSecurityFiles()
      .forEach((javaFile, folder) ->
        projectRepository.template(project, getPath(sourceTest, folder), javaFile, getPath(destinationTest, folder))
      );
  }

  @Override
  public void addAccountContext(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));

    String sourceSrc = getPath(SOURCE, "src");
    String destinationSrc = getPath(MAIN_JAVA, packageNamePath);
    oauth2AccountContextFiles()
      .forEach((file, folder) -> projectRepository.template(project, getPath(sourceSrc, folder), file, getPath(destinationSrc, folder)));

    String sourceTest = getPath(SOURCE, "test");
    String destinationTest = getPath(TEST_JAVA, packageNamePath);
    oauth2AccountContextTestFiles()
      .forEach((file, folder) -> projectRepository.template(project, getPath(sourceTest, folder), file, getPath(destinationTest, folder)));
  }

  private void addSpringBootProperties(Project project) {
    springBootCommonService.addPropertiesComment(project, "Spring Security OAuth2");
    properties().forEach((k, v) -> springBootCommonService.addProperties(project, k, v));
    springBootCommonService.addPropertiesNewLine(project);

    springBootCommonService.addPropertiesTestComment(project, "Spring Security OAuth2");
    propertiesForTests().forEach((k, v) -> springBootCommonService.addPropertiesTest(project, k, v));
    springBootCommonService.addPropertiesTestNewLine(project);
  }

  private void updateExceptionTranslator(Project project) {
    commonSecurityService.updateExceptionTranslator(project);
  }

  private void updateIntegrationTestWithMockUser(Project project) {
    commonSecurityService.updateIntegrationTestWithMockUser(project);
  }

  private void updateExceptionTranslatorWithAccountExceptionHandler(Project project) {
    String packageName = project.getPackageName().orElse(DEFAULT_PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    String exceptionTranslatorPath = getPath(MAIN_JAVA, packageNamePath, TECHNICAL_INFRASTRUCTURE_PRIMARY_EXCEPTION);
    String exceptionTranslatorFile = "ExceptionTranslator.java";

    String oldImport = "import org.zalando.problem.violations.ConstraintViolationProblem;";
    String newImport = String.format(
      """
        import org.zalando.problem.violations.ConstraintViolationProblem;
        import %s.error.domain.AccountException;""",
      packageName
    );
    projectRepository.replaceText(project, exceptionTranslatorPath, exceptionTranslatorFile, oldImport, newImport);

    String oldNeedle = "// jhipster-needle-exception-translator";
    String newNeedle =
      """
        @ExceptionHandler
          public ResponseEntity<Problem> handleAccountException(AccountException ex, NativeWebRequest request) {
            Problem problem = Problem.builder().withStatus(Status.UNAUTHORIZED).withTitle(ex.getMessage()).build();
            return create(ex, problem, request);
          }

          // jhipster-needle-exception-translator""";
    projectRepository.replaceText(project, exceptionTranslatorPath, exceptionTranslatorFile, oldNeedle, newNeedle);
  }

  private void updateExceptionTranslatorTestController(Project project) {
    String packageName = project.getPackageName().orElse(DEFAULT_PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    String exceptionTranslatorPath = getPath(TEST_JAVA, packageNamePath, TECHNICAL_INFRASTRUCTURE_PRIMARY_EXCEPTION);
    String fileToReplace = "ExceptionTranslatorTestController.java";

    String oldImport = "import org.springframework.http.converter.HttpMessageConversionException;";
    String newImport = String.format(
      """
        import org.springframework.http.converter.HttpMessageConversionException;
        import %s.error.domain.AccountException;""",
      packageName
    );
    projectRepository.replaceText(project, exceptionTranslatorPath, fileToReplace, oldImport, newImport);

    String oldNeedle = "// jhipster-needle-exception-translator-test-controller";
    String newNeedle =
      """
        @GetMapping("/account-exception")
          public void accountException() {
            throw new AccountException("beer");
          }

          // jhipster-needle-exception-translator-test-controller""";
    projectRepository.replaceText(project, exceptionTranslatorPath, fileToReplace, oldNeedle, newNeedle);
  }

  private void updateExceptionTranslatorIT(Project project) {
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    String exceptionTranslatorPath = getPath(TEST_JAVA, packageNamePath, TECHNICAL_INFRASTRUCTURE_PRIMARY_EXCEPTION);
    String fileToReplace = "ExceptionTranslatorIT.java";

    String oldNeedle = "// jhipster-needle-exception-translator-it";
    String newNeedle =
      """
        @Test
          void shouldHandleAccountException() throws Exception {
            mockMvc
              .perform(get("/api/exception-translator-test/account-exception"))
              .andExpect(status().isUnauthorized())
              .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
              .andExpect(jsonPath("\\$.message").value("error.http.401"))
              .andExpect(jsonPath("\\$.title").value("beer"));
          }

          // jhipster-needle-exception-translator-it""";
    projectRepository.replaceText(project, exceptionTranslatorPath, fileToReplace, oldNeedle, newNeedle);
  }

  private void updateIntegrationTestWithTestSecurityConfiguration(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageName = project.getPackageName().orElse(DEFAULT_PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    String integrationTestPath = getPath(TEST_JAVA, packageNamePath);

    project.addDefaultConfig(BASE_NAME);
    String baseName = project.getBaseName().orElse(DEFAULT_BASE_NAME);
    String className = WordUtils.upperFirst(baseName);

    String oldImport = "import org.springframework.boot.test.context.SpringBootTest;";
    String newImport = String.format(
      """
        import org.springframework.boot.test.context.SpringBootTest;
        import %s.security.oauth2.infrastructure.config.TestSecurityConfiguration;""",
      packageName
    );
    projectRepository.replaceText(project, integrationTestPath, "IntegrationTest.java", oldImport, newImport);

    String oldClass = className + "App.class";
    String newClass = className + "App.class, TestSecurityConfiguration.class";
    projectRepository.replaceText(project, integrationTestPath, "IntegrationTest.java", oldClass, newClass);
  }
}
