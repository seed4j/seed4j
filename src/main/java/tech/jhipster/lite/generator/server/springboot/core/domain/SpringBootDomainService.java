package tech.jhipster.lite.generator.server.springboot.core.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.CONFIG;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_RESOURCES;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.LOGGING_CONFIGURATION;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.LOGGING_TEST_CONFIGURATION;

import tech.jhipster.lite.common.domain.WordUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public record SpringBootDomainService(ProjectRepository projectRepository, BuildToolService buildToolService) implements SpringBootService {
  public static final String SOURCE = "server/springboot/core";
  public static final String SPRINGBOOT_PACKAGE = "org.springframework.boot";

  @Override
  public void init(Project project) {
    addSpringBootDependenciesBOM(project);
    addSpringBootDependencies(project);
    addSpringBootMavenPluginManagement(project);
    addSpringBootMavenPlugin(project);
    addMainApp(project);
    addApplicationProperties(project);
    addApplicationLocalProperties(project);
    addApplicationTestProperties(project);
    addLoggingConfiguration(project);
    addLoggingTestConfiguration(project);
  }

  @Override
  public void addSpringBootDependenciesBOM(Project project) {
    this.buildToolService.getVersion(project, "spring-boot")
      .ifPresentOrElse(
        version -> {
          project.addConfig("springBootVersion", version);

          Dependency springBootDependenciesPom = Dependency
            .builder()
            .groupId(SPRINGBOOT_PACKAGE)
            .artifactId("spring-boot-dependencies")
            .version("\\${spring-boot.version}")
            .type("pom")
            .scope("import")
            .build();

          buildToolService.addDependencyManagement(project, springBootDependenciesPom);
        },
        () -> {
          throw new GeneratorException("Spring Boot version not found");
        }
      );
  }

  @Override
  public void addSpringBootDependencies(Project project) {
    Dependency springBootStarterDependency = Dependency.builder().groupId(SPRINGBOOT_PACKAGE).artifactId("spring-boot-starter").build();
    buildToolService.addDependency(project, springBootStarterDependency);

    Dependency springBootConfigurationProcessor = Dependency
      .builder()
      .groupId(SPRINGBOOT_PACKAGE)
      .artifactId("spring-boot-configuration-processor")
      .optional()
      .build();
    buildToolService.addDependency(project, springBootConfigurationProcessor);

    Dependency commonLangDependency = Dependency.builder().groupId("org.apache.commons").artifactId("commons-lang3").build();
    buildToolService.addDependency(project, commonLangDependency);

    Dependency springBootStarterTestDependency = Dependency
      .builder()
      .groupId(SPRINGBOOT_PACKAGE)
      .artifactId("spring-boot-starter-test")
      .scope("test")
      .build();
    buildToolService.addDependency(project, springBootStarterTestDependency);
  }

  @Override
  public void addSpringBootMavenPluginManagement(Project project) {
    this.buildToolService.getVersion(project, "spring-boot")
      .ifPresentOrElse(
        version -> {
          Plugin plugin = Plugin
            .builder()
            .groupId(SPRINGBOOT_PACKAGE)
            .artifactId("spring-boot-maven-plugin")
            .version("\\${spring-boot.version}")
            .additionalElements(springBootAdditionalElements())
            .build();
          buildToolService.addProperty(project, "spring-boot.version", version);
          buildToolService.addPluginManagement(project, plugin);
        },
        () -> {
          throw new GeneratorException("Spring Boot version not found");
        }
      );
  }

  private String springBootAdditionalElements() {
    return """
      <executions>
        <execution>
          <goals>
            <goal>repackage</goal>
          </goals>
        </execution>
      </executions>
      <configuration>
        <mainClass>\\${start-class}</mainClass>
      </configuration>
      """;
  }

  @Override
  public void addSpringBootMavenPlugin(Project project) {
    Plugin plugin = Plugin.builder().groupId(SPRINGBOOT_PACKAGE).artifactId("spring-boot-maven-plugin").build();
    buildToolService.addPlugin(project, plugin);
  }

  @Override
  public void addMainApp(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(BASE_NAME);

    String baseName = project.getBaseName().orElse("jhipster");
    String className = WordUtils.upperFirst(baseName);
    project.addConfig("mainClass", className);

    String packageNamePath = project.getPackageNamePath().orElse("com/mycompany/myapp");

    projectRepository.template(project, SOURCE, "MainApp.java", getPath(MAIN_JAVA, packageNamePath), className + "App.java");
    projectRepository.template(project, SOURCE, "MainAppTest.java", getPath(TEST_JAVA, packageNamePath), className + "AppTest.java");
    projectRepository.template(project, SOURCE, "IntegrationTest.java", getPath(TEST_JAVA, packageNamePath));
  }

  @Override
  public void addApplicationProperties(Project project) {
    project.addDefaultConfig(BASE_NAME);

    projectRepository.template(project, SOURCE, "application.properties", getPath(MAIN_RESOURCES, CONFIG));
  }

  @Override
  public void addApplicationLocalProperties(Project project) {
    project.addDefaultConfig(BASE_NAME);

    projectRepository.template(project, SOURCE, "application-local.properties", getPath(MAIN_RESOURCES, CONFIG));
  }

  @Override
  public void addApplicationTestProperties(Project project) {
    project.addDefaultConfig(BASE_NAME);

    projectRepository.template(project, SOURCE, "application-test.properties", getPath(TEST_RESOURCES, CONFIG), "application.properties");
  }

  @Override
  public void addLoggingConfiguration(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);

    projectRepository.template(project, SOURCE, LOGGING_CONFIGURATION, getPath(MAIN_RESOURCES));
  }

  @Override
  public void addLoggingTestConfiguration(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);

    projectRepository.template(project, SOURCE, LOGGING_TEST_CONFIGURATION, getPath(TEST_RESOURCES));
  }
}
