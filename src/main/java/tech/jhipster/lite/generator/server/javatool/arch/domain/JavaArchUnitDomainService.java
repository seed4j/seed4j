package tech.jhipster.lite.generator.server.javatool.arch.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_RESOURCES;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_PATH;

import java.util.Arrays;
import java.util.stream.Collectors;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

public record JavaArchUnitDomainService(
  ProjectRepository projectRepository,
  BuildToolService buildToolService,
  SpringBootCommonService springBootCommonService
)
  implements JavaArchUnitService {
  public static final String SOURCE = "server/javatool/arch";

  @Override
  public void init(Project project) {
    addHexagobalArchJavaFiles(project);
    addArchUnitMavenPlugin(project);
    addLoggerInConfiguration(project);
  }

  private void addHexagobalArchJavaFiles(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);

    String packageNamePath = project.getPackageNamePath().orElse(PACKAGE_PATH);
    String packageWalkPath = Arrays.stream(packageNamePath.split("/")).map(s -> "\"" + s + "\"").collect(Collectors.joining(", "));
    project.addConfig("packageWalkPath", packageWalkPath);

    projectRepository.template(project, SOURCE, "BusinessContext.java", getPath(MAIN_JAVA, packageNamePath));
    projectRepository.template(project, SOURCE, "SharedKernel.java", getPath(MAIN_JAVA, packageNamePath));

    projectRepository.template(project, SOURCE, "archunit.properties", getPath(TEST_RESOURCES));
    projectRepository.template(project, SOURCE, "HexagonalArchTest.java", getPath(TEST_JAVA, packageNamePath));
  }

  private void addArchUnitMavenPlugin(Project project) {
    buildToolService
      .getVersion(project, "archunit-junit5")
      .ifPresentOrElse(
        version -> {
          buildToolService.addProperty(project, ArchUnit.ARCHUNIT_JUNIT5_VERSION, version);
          buildToolService.addDependency(project, ArchUnit.archUnitDependency());
        },
        () -> {
          throw new GeneratorException("Version not found: archunit-junit5");
        }
      );
  }

  private void addLoggerInConfiguration(Project project) {
    addLogger(project, "com.tngtech.archunit", Level.WARN);
  }

  private void addLogger(Project project, String packageName, Level level) {
    springBootCommonService.addLoggerTest(project, packageName, level);
  }
}
