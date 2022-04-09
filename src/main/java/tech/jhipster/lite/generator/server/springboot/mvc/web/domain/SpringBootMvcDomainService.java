package tech.jhipster.lite.generator.server.springboot.mvc.web.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommon.TECHNICAL_INFRASTRUCTURE_PRIMARY_EXCEPTION;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvc.CORS;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvc.TECHNICAL_INFRASTRUCTURE_PRIMARY_CORS;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvc.corsFiles;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvc.corsProperties;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvc.problemSpringDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvc.springBootActuatorDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvc.springBootStarterValidation;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvc.springBootStarterWebDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvc.tomcatDependency;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvc.undertowDependency;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_PATH;

import java.util.List;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.tools.domain.DefaultConfig;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

public class SpringBootMvcDomainService implements SpringBootMvcService {

  public static final String SOURCE = "server/springboot/mvc/web";

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;

  public SpringBootMvcDomainService(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
  }

  @Override
  public void init(Project project) {
    addSpringBootMvc(project);
  }

  @Override
  public void addSpringBootMvc(Project project) {
    buildToolService.addDependency(project, springBootStarterWebDependency());

    addServerPortInProperties(project);
    addExceptionHandler(project);
    addLoggerInConfiguration(project, "org.springframework.web", Level.WARN);

    addCorsFiles(project);
    addCorsProperties(project);
  }

  @Override
  public void addSpringBootUndertow(Project project) {
    buildToolService.addDependency(project, springBootStarterWebDependency(), List.of(tomcatDependency()));
    buildToolService.addDependency(project, undertowDependency());

    addServerPortInProperties(project);
    addExceptionHandler(project);
    addLoggerInConfiguration(project, "io.undertow", Level.WARN);

    addCorsFiles(project);
    addCorsProperties(project);
  }

  @Override
  public void addSpringBootActuator(Project project) {
    buildToolService.addDependency(project, springBootActuatorDependency());

    springBootCommonService.addPropertiesComment(project, "Spring Boot Actuator");
    springBootCommonService.addProperties(project, "management.endpoints.web.base-path", "/management");
    springBootCommonService.addProperties(
      project,
      "management.endpoints.web.exposure.include",
      "configprops, env, health, info, logfile, loggers, threaddump"
    );
    springBootCommonService.addProperties(project, "management.endpoint.health.probes.enabled", "true");
    springBootCommonService.addProperties(project, "management.endpoint.health.group.liveness.include", "livenessState");
    springBootCommonService.addProperties(project, "management.endpoint.health.group.readiness.include", "readinessState");
    springBootCommonService.addPropertiesNewLine(project);
  }

  @Override
  public void addExceptionHandler(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);

    this.buildToolService.getVersion(project, "problem-spring")
      .ifPresentOrElse(
        version -> {
          buildToolService.addProperty(project, "problem-spring.version", version);
          buildToolService.addProperty(project, "problem-spring-web.version", "\\${problem-spring.version}");
        },
        () -> {
          throw new GeneratorException("Problem Spring version not found");
        }
      );

    buildToolService.addDependency(project, problemSpringDependency());
    buildToolService.addDependency(project, springBootStarterValidation());

    String packageName = project.getPackageName().orElse(DefaultConfig.DEFAULT_PACKAGE_NAME);
    springBootCommonService.addProperties(project, "application.exception.details", "false");
    springBootCommonService.addProperties(project, "application.exception.package", "org.,java.,net.,javax.,com.,io.,de.," + packageName);
    springBootCommonService.addPropertiesTest(project, "application.exception.package", "org.,java.");

    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));
    templateToExceptionHandler(project, packageNamePath, "src", "BadRequestAlertException.java", MAIN_JAVA);
    templateToExceptionHandler(project, packageNamePath, "src", "ErrorConstants.java", MAIN_JAVA);
    templateToExceptionHandler(project, packageNamePath, "src", "ExceptionTranslator.java", MAIN_JAVA);
    templateToExceptionHandler(project, packageNamePath, "src", "FieldErrorDTO.java", MAIN_JAVA);
    templateToExceptionHandler(project, packageNamePath, "src", "HeaderUtil.java", MAIN_JAVA);
    templateToExceptionHandler(project, packageNamePath, "src", "ProblemConfiguration.java", MAIN_JAVA);

    templateToExceptionHandler(project, packageNamePath, "test", "BadRequestAlertExceptionTest.java", TEST_JAVA);
    templateToExceptionHandler(project, packageNamePath, "test", "ExceptionTranslatorIT.java", TEST_JAVA);
    templateToExceptionHandler(project, packageNamePath, "test", "ExceptionTranslatorTestController.java", TEST_JAVA);
    templateToExceptionHandler(project, packageNamePath, "test", "FieldErrorDTOTest.java", TEST_JAVA);
    templateToExceptionHandler(project, packageNamePath, "test", "HeaderUtilTest.java", TEST_JAVA);

    projectRepository.template(project, getPath(SOURCE, "test"), "TestUtil.java", getPath(TEST_JAVA, packageNamePath));
  }

  private void templateToExceptionHandler(Project project, String source, String type, String sourceFilename, String destination) {
    projectRepository.template(
      project,
      getPath(SOURCE, type),
      sourceFilename,
      getPath(destination, source, TECHNICAL_INFRASTRUCTURE_PRIMARY_EXCEPTION)
    );
  }

  private void addServerPortInProperties(Project project) {
    springBootCommonService.addPropertiesComment(project, "Spring Boot MVC");
    springBootCommonService.addProperties(project, "server.port", project.getServerPort());
    springBootCommonService.addPropertiesTest(project, "server.port", 0);
    springBootCommonService.addPropertiesNewLine(project);
  }

  private void addLoggerInConfiguration(Project project, String packageName, Level level) {
    springBootCommonService.addLogger(project, packageName, level);
    springBootCommonService.addLoggerTest(project, packageName, level);
    springBootCommonService.addPropertiesNewLine(project);
    springBootCommonService.addPropertiesTestNewLine(project);
  }

  private void addCorsFiles(Project project) {
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    corsFiles()
      .forEach((javaFile, destination) ->
        projectRepository.template(
          project,
          getPath(SOURCE, "src", CORS),
          javaFile,
          getPath(MAIN_JAVA, packageNamePath, TECHNICAL_INFRASTRUCTURE_PRIMARY_CORS)
        )
      );

    projectRepository.template(
      project,
      getPath(SOURCE, "test", CORS),
      "CorsFilterConfigurationIT.java",
      getPath(TEST_JAVA, packageNamePath, TECHNICAL_INFRASTRUCTURE_PRIMARY_CORS)
    );
  }

  private void addCorsProperties(Project project) {
    String baseName = project.getBaseName().orElse("jhipster");

    String commentCorsConfiguration = "CORS configuration";
    springBootCommonService.addPropertiesComment(project, commentCorsConfiguration);
    springBootCommonService.addPropertiesLocalComment(project, commentCorsConfiguration);
    springBootCommonService.addPropertiesTestComment(project, commentCorsConfiguration);

    corsProperties(baseName)
      .forEach((k, v) -> {
        springBootCommonService.addPropertiesComment(project, k + "=" + v);
        springBootCommonService.addPropertiesLocal(project, k, v);
        springBootCommonService.addPropertiesTest(project, k, v);
      });

    springBootCommonService.addPropertiesNewLine(project);
    springBootCommonService.addPropertiesLocalNewLine(project);
    springBootCommonService.addPropertiesTestNewLine(project);
  }
}
