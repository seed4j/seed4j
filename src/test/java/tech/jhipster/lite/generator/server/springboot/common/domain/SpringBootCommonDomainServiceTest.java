package tech.jhipster.lite.generator.server.springboot.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.TestUtils.tmpProjectWithSpringBootLoggingConfiguration;
import static tech.jhipster.lite.TestUtils.tmpProjectWithSpringBootProperties;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.common.domain.FileUtils.getPathOf;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.APPLICATION_LOCAL_PROPERTIES;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.APPLICATION_PROPERTIES;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.LOGGING_CONFIGURATION;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.LOGGING_TEST_CONFIGURATION;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.NEEDLE_LOGBACK_LOGGER;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_RESOURCES;

import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SpringBootCommonDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  SpringBootCommonDomainService springBootCommonDomainService;

  @Test
  void shouldAddProperties() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(getPath(project.getFolder(), MAIN_RESOURCES, "config"));
    Files.copy(
      getPathOf(TEST_RESOURCES, "generator/server/springboot/core/application.src.properties"),
      getPathOf(project.getFolder(), MAIN_RESOURCES, "config", APPLICATION_PROPERTIES)
    );

    springBootCommonDomainService.addProperties(project, "server.port", 8080);

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddPropertiesLocal() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(getPath(project.getFolder(), MAIN_RESOURCES, "config"));
    Files.copy(
      getPathOf(TEST_RESOURCES, "generator/server/springboot/core/application.src.local.properties"),
      getPathOf(project.getFolder(), MAIN_RESOURCES, "config", APPLICATION_LOCAL_PROPERTIES)
    );

    springBootCommonDomainService.addPropertiesLocal(project, "specific.config.local", "chips");

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddPropertiesTest() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(getPath(project.getFolder(), TEST_RESOURCES, "config"));
    Files.copy(
      getPathOf(TEST_RESOURCES, "generator/server/springboot/core/application.test.properties"),
      getPathOf(project.getFolder(), TEST_RESOURCES, "config", APPLICATION_PROPERTIES)
    );

    springBootCommonDomainService.addPropertiesTest(project, "server.port", 8080);

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddPropertiesNewLine() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(getPath(project.getFolder(), MAIN_RESOURCES, "config"));
    Files.copy(
      getPathOf(TEST_RESOURCES, "generator/server/springboot/core/application.src.properties"),
      getPathOf(project.getFolder(), MAIN_RESOURCES, "config", APPLICATION_PROPERTIES)
    );

    springBootCommonDomainService.addPropertiesNewLine(project);

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddPropertiesLocalNewLine() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(getPath(project.getFolder(), MAIN_RESOURCES, "config"));
    Files.copy(
      getPathOf(TEST_RESOURCES, "generator/server/springboot/core/application.src.local.properties"),
      getPathOf(project.getFolder(), MAIN_RESOURCES, "config", APPLICATION_LOCAL_PROPERTIES)
    );

    springBootCommonDomainService.addPropertiesLocalNewLine(project);

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddPropertiesTestNewLine() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(getPath(project.getFolder(), TEST_RESOURCES, "config"));
    Files.copy(
      getPathOf(TEST_RESOURCES, "generator/server/springboot/core/application.test.properties"),
      getPathOf(project.getFolder(), TEST_RESOURCES, "config", APPLICATION_PROPERTIES)
    );

    springBootCommonDomainService.addPropertiesTestNewLine(project);

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddPropertiesComment() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(getPath(project.getFolder(), MAIN_RESOURCES, "config"));
    Files.copy(
      getPathOf(TEST_RESOURCES, "generator/server/springboot/core/application.src.properties"),
      getPathOf(project.getFolder(), MAIN_RESOURCES, "config", APPLICATION_PROPERTIES)
    );

    springBootCommonDomainService.addPropertiesComment(project, "comment");

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddPropertiesLocalComment() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(getPath(project.getFolder(), MAIN_RESOURCES, "config"));
    Files.copy(
      getPathOf(TEST_RESOURCES, "generator/server/springboot/core/application.src.local.properties"),
      getPathOf(project.getFolder(), MAIN_RESOURCES, "config", APPLICATION_LOCAL_PROPERTIES)
    );

    springBootCommonDomainService.addPropertiesLocalComment(project, "comment");

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddPropertiesTestComment() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(getPath(project.getFolder(), TEST_RESOURCES, "config"));
    Files.copy(
      getPathOf(TEST_RESOURCES, "generator/server/springboot/core/application.test.properties"),
      getPathOf(project.getFolder(), TEST_RESOURCES, "config", APPLICATION_PROPERTIES)
    );

    springBootCommonDomainService.addPropertiesTestComment(project, "comment");

    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddLogger() {
    Project project = tmpProjectWithSpringBootLoggingConfiguration();

    springBootCommonDomainService.addLogger(project, "tech.jhipster.lite", Level.ERROR);

    verify(projectRepository)
      .replaceText(
        any(Project.class),
        contains("main"),
        eq(LOGGING_CONFIGURATION),
        eq(NEEDLE_LOGBACK_LOGGER),
        contains(NEEDLE_LOGBACK_LOGGER)
      );
  }

  @Test
  void shouldAddLoggerTest() {
    Project project = tmpProjectWithSpringBootLoggingConfiguration();

    springBootCommonDomainService.addLoggerTest(project, "tech.jhipster.lite", Level.ERROR);

    verify(projectRepository)
      .replaceText(
        any(Project.class),
        contains("test"),
        eq(LOGGING_TEST_CONFIGURATION),
        eq(NEEDLE_LOGBACK_LOGGER),
        contains(NEEDLE_LOGBACK_LOGGER)
      );
  }

  @Test
  void shouldGetProperty() {
    Project project = tmpProjectWithSpringBootProperties();

    assertThat(springBootCommonDomainService.getProperty(project, "spring.application.name")).contains("jhlite");
  }

  @Test
  void shouldNotGetProperty() {
    Project project = tmpProjectWithSpringBootProperties();

    assertThat(springBootCommonDomainService.getProperty(project, "bad.key")).isEmpty();
  }

  @Test
  void shouldNotGetPropertyWhenNoPropertiesFile() {
    Project project = tmpProject();

    assertThat(springBootCommonDomainService.getProperty(project, "spring.application.name")).isEmpty();
  }

  @Test
  void shouldNotGetPropertyForNullProject() {
    assertThatThrownBy(() -> springBootCommonDomainService.getProperty(null, "spring.application.name"))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldNotGetPropertyForNullKey() {
    Project project = tmpProject();
    assertThatThrownBy(() -> springBootCommonDomainService.getProperty(project, null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("key");
  }

  @Test
  void shouldNotGetPropertyForBlankKey() {
    Project project = tmpProject();
    assertThatThrownBy(() -> springBootCommonDomainService.getProperty(project, " "))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("key");
  }

  @Test
  void shouldNotCheckIfProjectIsSetWithMariaDbOrMySqlDatabaseWithoutProject() {
    assertThatThrownBy(() -> springBootCommonDomainService.isSetWithMySQLOrMariaDBDatabase(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldNotCheckIfProjectIsDatabaseUseSequencesWithoutProject() {
    assertThatThrownBy(() -> springBootCommonDomainService.isDatabaseUseSequences(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }
}
