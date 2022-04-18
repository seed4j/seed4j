package tech.jhipster.lite.generator.server.springboot.logging.logstash.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;

import java.util.Map;
import java.util.TreeMap;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.DefaultConfig;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

public record LogstashDomainService(
  BuildToolService buildToolService,
  ProjectRepository projectRepository,
  SpringBootCommonService springBootCommonService
)
  implements LogstashService {
  public static final String SOURCE = "server/springboot/logging/logstash";
  public static final String DESTINATION = "technical/infrastructure/secondary/logstash";

  @Override
  public void init(Project project) {
    addDependencies(project);
    addJavaFiles(project);
    addProperties(project);
    addLoggerInConfiguration(project);
  }

  @Override
  public void addDependencies(Project project) {
    this.buildToolService.getVersion(project, "logstash-logback-encoder")
      .ifPresentOrElse(
        version -> {
          buildToolService.addProperty(project, "logstash-logback-encoder.version", version);
          buildToolService.addDependency(project, Logstash.logstashLogbackEncoderDependency());
        },
        () -> {
          throw new GeneratorException("Logstash Logback Encoder version not found");
        }
      );
  }

  @Override
  public void addJavaFiles(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(BASE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));

    templateToLogstash(project, packageNamePath, "src", "LogstashTcpConfiguration.java", MAIN_JAVA);
    templateToLogstash(project, packageNamePath, "src", "LogstashTcpLifeCycle.java", MAIN_JAVA);
    templateToLogstash(project, packageNamePath, "src", "LogstashTcpProperties.java", MAIN_JAVA);

    templateToLogstash(project, packageNamePath, "test", "LogstashTcpConfigurationIT.java", TEST_JAVA);
    templateToLogstash(project, packageNamePath, "test", "LogstashTcpConfigurationTest.java", TEST_JAVA);
    templateToLogstash(project, packageNamePath, "test", "LogstashTcpLifeCycleTest.java", TEST_JAVA);
    templateToLogstash(project, packageNamePath, "test", "LogstashTcpPropertiesTest.java", TEST_JAVA);
  }

  @Override
  public void addProperties(Project project) {
    springBootCommonService.addPropertiesComment(project, "Logstash appender");
    properties().forEach((k, v) -> springBootCommonService.addProperties(project, k, v));
    springBootCommonService.addPropertiesNewLine(project);
  }

  private Map<String, Object> properties() {
    TreeMap<String, Object> result = new TreeMap<>();

    result.put("application.logging.logstash.tcp.enabled", false);
    result.put("application.logging.logstash.tcp.host", "localhost");
    result.put("application.logging.logstash.tcp.port", 5000);
    result.put("application.logging.logstash.tcp.ring-buffer-size", 8192);
    result.put("application.logging.logstash.tcp.shutdown_grace_period", "PT1M");

    return result;
  }

  private void templateToLogstash(Project project, String source, String type, String sourceFilename, String destination) {
    projectRepository.template(project, getPath(SOURCE, type), sourceFilename, getPath(destination, source, DESTINATION));
  }

  @Override
  public void addLoggerInConfiguration(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageName = project.getPackageName().orElse(DefaultConfig.DEFAULT_PACKAGE_NAME);
    String destinationPackage = DESTINATION.replace("/", ".");
    addLogger(project, packageName + "." + destinationPackage, Level.WARN);
    addLogger(project, "org.jboss.logging", Level.WARN);
  }

  private void addLogger(Project project, String packageName, Level level) {
    springBootCommonService.addLoggerTest(project, packageName, level);
  }
}
