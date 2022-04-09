package tech.jhipster.lite.generator.server.springboot.logging.aop.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_NAME;

import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

public class AopLoggingDomainService implements AopLoggingService {

  public static final String SOURCE = "server/springboot/logging/aop";
  private static final String LOGGING_PROPERTY_FIELD = "application.aop.logging";
  private static final String COMMENT_AOP_LOGGING = "AOP Logging Configuration";

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;

  public AopLoggingDomainService(
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
    addMavenDependencies(project);
    addJavaFiles(project);
    addProperties(project);
  }

  @Override
  public void addMavenDependencies(Project project) {
    Dependency aopDependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-aop").build();
    buildToolService.addDependency(project, aopDependency);
  }

  @Override
  public void addProperties(Project project) {
    springBootCommonService.addPropertiesComment(project, COMMENT_AOP_LOGGING);
    springBootCommonService.addProperties(project, LOGGING_PROPERTY_FIELD, "false");
    springBootCommonService.addPropertiesNewLine(project);

    springBootCommonService.addPropertiesLocalComment(project, COMMENT_AOP_LOGGING);
    springBootCommonService.addPropertiesLocal(project, LOGGING_PROPERTY_FIELD, "true");
    springBootCommonService.addPropertiesLocalNewLine(project);

    springBootCommonService.addPropertiesTestComment(project, COMMENT_AOP_LOGGING);
    springBootCommonService.addPropertiesTest(project, LOGGING_PROPERTY_FIELD, "true");
    springBootCommonService.addPropertiesTestNewLine(project);
  }

  @Override
  public void addJavaFiles(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(BASE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));
    String aopLoggingPath = "technical/infrastructure/secondary/aop/logging";

    projectRepository.template(project, SOURCE, "LoggingAspectConfiguration.java", getPath(MAIN_JAVA, packageNamePath, aopLoggingPath));
    projectRepository.template(project, SOURCE, "LoggingAspect.java", getPath(MAIN_JAVA, packageNamePath, aopLoggingPath));
    projectRepository.template(project, SOURCE, "LoggingAspectTest.java", getPath(TEST_JAVA, packageNamePath, aopLoggingPath));
  }
}
