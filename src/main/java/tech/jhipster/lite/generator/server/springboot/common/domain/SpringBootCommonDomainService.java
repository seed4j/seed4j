package tech.jhipster.lite.generator.server.springboot.common.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.common.domain.WordUtils.LF;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.APPLICATION_LOCAL_PROPERTIES;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.APPLICATION_PROPERTIES;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.LOGGING_CONFIGURATION;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.LOGGING_TEST_CONFIGURATION;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.NEEDLE_APPLICATION_LOCAL_PROPERTIES;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.NEEDLE_APPLICATION_PROPERTIES;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.NEEDLE_APPLICATION_TEST_LOGGING_PROPERTIES;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.NEEDLE_APPLICATION_TEST_PROPERTIES;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.NEEDLE_LOGBACK_LOGGER;
import static tech.jhipster.lite.generator.tools.domain.Constants.CONFIG;
import static tech.jhipster.lite.generator.tools.domain.Constants.INTEGRATION_TEST;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_PATH;

import java.util.Optional;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.tools.domain.DatabaseType;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

public class SpringBootCommonDomainService implements SpringBootCommonService {

  public static final String SOURCE = "server/springboot/common";
  private static final String PROJECT_FIELD_ASSERT_NAME = "project";

  private final ProjectRepository projectRepository;

  public SpringBootCommonDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addTestLogbackRecorder(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(BASE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));
    projectRepository.template(project, getPath(SOURCE, "test"), "LogbackRecorder.java", getPath(TEST_JAVA, packageNamePath));
  }

  @Override
  public void addProperties(Project project, String key, Object value) {
    addKeyValueToProperties(project, key, value, MAIN_RESOURCES, APPLICATION_PROPERTIES, NEEDLE_APPLICATION_PROPERTIES);
  }

  @Override
  public void addPropertiesLocal(Project project, String key, Object value) {
    addKeyValueToProperties(project, key, value, MAIN_RESOURCES, APPLICATION_LOCAL_PROPERTIES, NEEDLE_APPLICATION_LOCAL_PROPERTIES);
  }

  @Override
  public void addPropertiesTest(Project project, String key, Object value) {
    addKeyValueToProperties(project, key, value, TEST_RESOURCES, APPLICATION_PROPERTIES, NEEDLE_APPLICATION_TEST_PROPERTIES);
  }

  @Override
  public void addPropertiesTestLogging(Project project, String key, Level value) {
    addKeyValueToProperties(
      project,
      "logging.level." + key,
      value,
      TEST_RESOURCES,
      APPLICATION_PROPERTIES,
      NEEDLE_APPLICATION_TEST_LOGGING_PROPERTIES
    );
  }

  private void addKeyValueToProperties(
    Project project,
    String key,
    Object value,
    String folderProperties,
    String fileProperties,
    String needleProperties
  ) {
    String propertiesWithNeedle = key + "=" + value + LF + needleProperties;
    projectRepository.replaceText(project, getPath(folderProperties, CONFIG), fileProperties, needleProperties, propertiesWithNeedle);
  }

  @Override
  public void addPropertiesNewLine(Project project) {
    addNewLineToProperties(project, MAIN_RESOURCES, APPLICATION_PROPERTIES, NEEDLE_APPLICATION_PROPERTIES);
  }

  @Override
  public void addPropertiesLocalNewLine(Project project) {
    addNewLineToProperties(project, MAIN_RESOURCES, APPLICATION_LOCAL_PROPERTIES, NEEDLE_APPLICATION_LOCAL_PROPERTIES);
  }

  @Override
  public void addPropertiesTestNewLine(Project project) {
    addNewLineToProperties(project, TEST_RESOURCES, APPLICATION_PROPERTIES, NEEDLE_APPLICATION_TEST_PROPERTIES);
  }

  @Override
  public void addPropertiesTestLoggingNewLine(Project project) {
    addNewLineToProperties(project, TEST_RESOURCES, APPLICATION_PROPERTIES, NEEDLE_APPLICATION_TEST_LOGGING_PROPERTIES);
  }

  private void addNewLineToProperties(Project project, String folderProperties, String fileProperties, String needleProperties) {
    String propertiesWithNeedle = LF + needleProperties;
    projectRepository.replaceText(project, getPath(folderProperties, CONFIG), fileProperties, needleProperties, propertiesWithNeedle);
  }

  @Override
  public void addPropertiesComment(Project project, String text) {
    addCommentToProperties(project, text, MAIN_RESOURCES, APPLICATION_PROPERTIES, NEEDLE_APPLICATION_PROPERTIES);
  }

  @Override
  public void addPropertiesLocalComment(Project project, String text) {
    addCommentToProperties(project, text, MAIN_RESOURCES, APPLICATION_LOCAL_PROPERTIES, NEEDLE_APPLICATION_LOCAL_PROPERTIES);
  }

  @Override
  public void addPropertiesTestComment(Project project, String text) {
    addCommentToProperties(project, text, TEST_RESOURCES, APPLICATION_PROPERTIES, NEEDLE_APPLICATION_TEST_PROPERTIES);
  }

  @Override
  public void addPropertiesTestLoggingComment(Project project, String text) {
    addCommentToProperties(project, text, TEST_RESOURCES, APPLICATION_PROPERTIES, NEEDLE_APPLICATION_TEST_LOGGING_PROPERTIES);
  }

  private void addCommentToProperties(
    Project project,
    String text,
    String folderProperties,
    String fileProperties,
    String needleProperties
  ) {
    String propertiesWithNeedle = "# " + text + LF + needleProperties;
    projectRepository.replaceText(project, getPath(folderProperties, CONFIG), fileProperties, needleProperties, propertiesWithNeedle);
  }

  @Override
  public void addLogger(Project project, String packageName, Level level) {
    addLoggerToConfiguration(project, packageName, level, MAIN_RESOURCES, LOGGING_CONFIGURATION, NEEDLE_LOGBACK_LOGGER);
  }

  @Override
  public void addLoggerTest(Project project, String packageName, Level level) {
    addLoggerToConfiguration(project, packageName, level, TEST_RESOURCES, LOGGING_TEST_CONFIGURATION, NEEDLE_LOGBACK_LOGGER);
  }

  @Override
  public Optional<String> getProperty(Project project, String key) {
    Assert.notNull(PROJECT_FIELD_ASSERT_NAME, project);
    Assert.notBlank("key", key);

    return FileUtils
      .readLine(getPath(project.getFolder(), MAIN_RESOURCES, CONFIG, "application.properties"), key + "=")
      .map(readValue -> {
        String[] result = readValue.split("=");
        if (result.length == 2) {
          return result[1];
        }
        return null;
      });
  }

  @Override
  public boolean isSetWithMySQLOrMariaDBDatabase(Project project) {
    Assert.notNull(PROJECT_FIELD_ASSERT_NAME, project);
    return isMariaDBDatabase(project) || isMySQLDatabase(project);
  }

  @Override
  public boolean isDatabaseUseSequences(Project project) {
    Assert.notNull(PROJECT_FIELD_ASSERT_NAME, project);

    return hasSpecificDatabaseProperty(project, DatabaseType.POSTGRESQL);
  }

  private boolean isMySQLDatabase(Project project) {
    return hasSpecificDatabaseProperty(project, DatabaseType.MYSQL);
  }

  private boolean isMariaDBDatabase(Project project) {
    return hasSpecificDatabaseProperty(project, DatabaseType.MARIADB);
  }

  private boolean hasSpecificDatabaseProperty(Project project, DatabaseType databaseType) {
    return getProperty(project, "spring.datasource.url").filter(value -> value.contains(databaseType.id())).isPresent();
  }

  private void addLoggerToConfiguration(
    Project project,
    String packageName,
    Level level,
    String folderConfig,
    String fileLoggingConfig,
    String needleLogger
  ) {
    String loggerWithNeedle =
      String.format("<logger name=\"%s\" level=\"%s\" />", packageName, level.toString()) + LF + "  " + needleLogger;
    projectRepository.replaceText(project, getPath(folderConfig), fileLoggingConfig, needleLogger, loggerWithNeedle);
  }

  @Override
  public void updateIntegrationTestAnnotation(Project project, String className) {
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    String integrationTestPath = getPath(TEST_JAVA, packageNamePath);

    if (!containsExtendWith(project)) {
      String oldImport = "import org.springframework.boot.test.context.SpringBootTest;";
      String newImport =
        """
          import org.junit.jupiter.api.extension.ExtendWith;
          import org.springframework.boot.test.context.SpringBootTest;""";
      projectRepository.replaceText(project, integrationTestPath, INTEGRATION_TEST, oldImport, newImport);
    }

    String oldAnnotation = "public @interface";
    String newAnnotation = String.format("""
      @ExtendWith(%s.class)
      public @interface""", className);
    projectRepository.replaceText(project, integrationTestPath, INTEGRATION_TEST, oldAnnotation, newAnnotation);
  }

  private boolean containsExtendWith(Project project) {
    String packageNamePath = project.getPackageNamePath().orElse(PACKAGE_PATH);
    return FileUtils.containsInLine(
      getPath(project.getFolder(), TEST_JAVA, packageNamePath, INTEGRATION_TEST),
      "import org.junit.jupiter.api.extension.ExtendWith;"
    );
  }
}
