package tech.jhipster.lite.generator.server.springboot.common.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileContentRegexp;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.TestUtils.tmpProjectWithSpringBootLoggingConfiguration;
import static tech.jhipster.lite.TestUtils.tmpProjectWithSpringBootProperties;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.common.domain.WordUtils.LF;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_NAME;

import java.io.IOException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.tools.domain.DefaultConfig;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class SpringBootCommonApplicationServiceIT {

  @Autowired
  SpringBootCommonApplicationService springBootCommonApplicationService;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldAddTestLogbackRecorder() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "foo");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.bar");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootCommonApplicationService.addTestLogbackRecorder(project);

    String packageName = project.getPackageName().orElse(DefaultConfig.DEFAULT_PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(DefaultConfig.PACKAGE_PATH);

    assertFileExist(project, getPath(TEST_JAVA, packageNamePath, "LogbackRecorder.java"));

    assertFileContent(project, getPath(TEST_JAVA, packageNamePath, "LogbackRecorder.java"), "package " + packageName);
  }

  @Nested
  class PropertiesIT {

    @Test
    void shouldNotAddProperties() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootCommonApplicationService.addProperties(project, "server.port", 8080))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddPropertiesWithInteger() {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addProperties(project, "server.port", 8080);

      String applicationProperties = getPath(MAIN_RESOURCES, "config/application.properties");
      assertFileContent(project, applicationProperties, "server.port=8080");
      assertFileContent(project, applicationProperties, "# jhipster-needle-application-properties");
    }

    @Test
    void shouldAddPropertiesWithBoolean() {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addProperties(project, "spring.jmx.enabled", false);

      String applicationProperties = getPath(MAIN_RESOURCES, "config/application.properties");
      assertFileContent(project, applicationProperties, "spring.jmx.enabled=false");
      assertFileContent(project, applicationProperties, "# jhipster-needle-application-properties");
    }

    @Test
    void shouldAddPropertiesWithString() {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addProperties(project, "jhipster.application", "jhlite");

      String applicationProperties = getPath(MAIN_RESOURCES, "config/application.properties");
      assertFileContent(project, applicationProperties, "jhipster.application=jhlite");
      assertFileContent(project, applicationProperties, "# jhipster-needle-application-properties");
    }

    @Test
    void shouldNotAddPropertiesWhenNoApplicationProperties() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootCommonApplicationService.addProperties(project, "jhipster.application", "jhlite"))
        .isExactlyInstanceOf(GeneratorException.class);
    }
  }

  @Nested
  class LocalPropertiesIT {

    @Test
    void shouldNotAddPropertiesLocal() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootCommonApplicationService.addPropertiesLocal(project, "specific.config.local", "chips"))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddPropertiesLocalWithInteger() {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addPropertiesLocal(project, "server.port", 8080);

      String applicationProperties = getPath(MAIN_RESOURCES, "config/application-local.properties");
      assertFileContent(project, applicationProperties, "server.port=8080");
      assertFileContent(project, applicationProperties, "# jhipster-needle-application-local-properties");
    }

    @Test
    void shouldAddPropertiesLocalWithBoolean() {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addPropertiesLocal(project, "spring.jmx.enabled", false);

      String applicationProperties = getPath(MAIN_RESOURCES, "config/application-local.properties");
      assertFileContent(project, applicationProperties, "spring.jmx.enabled=false");
      assertFileContent(project, applicationProperties, "# jhipster-needle-application-local-properties");
    }

    @Test
    void shouldAddPropertiesLocalWithString() {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addPropertiesLocal(project, "jhipster.application", "jhlite");

      String applicationProperties = getPath(MAIN_RESOURCES, "config/application-local.properties");
      assertFileContent(project, applicationProperties, "jhipster.application=jhlite");
      assertFileContent(project, applicationProperties, "# jhipster-needle-application-local-properties");
    }

    @Test
    void shouldNotAddPropertiesLocalWhenNoApplicationProperties() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootCommonApplicationService.addPropertiesLocal(project, "jhipster.application", "jhlite"))
        .isExactlyInstanceOf(GeneratorException.class);
    }
  }

  @Nested
  class TestPropertiesIT {

    @Test
    void shouldNotAddPropertiesTest() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootCommonApplicationService.addPropertiesTest(project, "server.port", 8080))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddPropertiesTestWithInteger() {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addPropertiesTest(project, "server.port", 8080);

      String applicationProperties = getPath(TEST_RESOURCES, "config/application.properties");
      assertFileContent(project, applicationProperties, "server.port=8080");
      assertFileContent(project, applicationProperties, "# jhipster-needle-application-test-properties");
    }

    @Test
    void shouldAddPropertiesTestWithBoolean() {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addPropertiesTest(project, "spring.jmx.enabled", false);

      String applicationProperties = getPath(TEST_RESOURCES, "config/application.properties");
      assertFileContent(project, applicationProperties, "spring.jmx.enabled=false");
      assertFileContent(project, applicationProperties, "# jhipster-needle-application-test-properties");
    }

    @Test
    void shouldAddPropertiesTestWithString() {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addPropertiesTest(project, "jhipster.application", "jhlite");

      String applicationProperties = getPath(TEST_RESOURCES, "config/application.properties");
      assertFileContent(project, applicationProperties, "jhipster.application=jhlite");
      assertFileContent(project, applicationProperties, "# jhipster-needle-application-test-properties");
    }

    @Test
    void shouldNotAddPropertiesTestWhenNoApplicationProperties() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootCommonApplicationService.addPropertiesTest(project, "jhipster.application", "jhlite"))
        .isExactlyInstanceOf(GeneratorException.class);
    }
  }

  @Nested
  class TestLoggingPropertiesIT {

    @Test
    void shouldNotAddPropertiesLoggingTest() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootCommonApplicationService.addPropertiesTestLogging(project, "tech.jhipster.beer", Level.OFF))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddPropertiesLoggingTest() {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addPropertiesTestLogging(project, "tech.jhipster.beer", Level.OFF);

      String applicationProperties = getPath(TEST_RESOURCES, "config/application.properties");
      assertFileContent(project, applicationProperties, "logging.level.tech.jhipster.beer=OFF");
      assertFileContent(project, applicationProperties, "# jhipster-needle-application-test-logging-properties");
    }
  }

  @Nested
  class PropertiesNewLineIT {

    @Test
    void shouldNotAddPropertiesNewLine() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootCommonApplicationService.addPropertiesNewLine(project))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddPropertiesNewLine() throws IOException {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addProperties(project, "new.property", "value");
      springBootCommonApplicationService.addPropertiesNewLine(project);

      String applicationProperties = getPath(MAIN_RESOURCES, "config/application.properties");
      assertFileContentRegexp(project, applicationProperties, "new.property=value" + LF + LF + "# jhipster-needle-application-properties");
    }
  }

  @Nested
  class PropertiesLocalNewLineIT {

    @Test
    void shouldNotAddPropertiesLocalNewLine() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootCommonApplicationService.addPropertiesLocalNewLine(project))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddPropertiesLocalNewLine() throws IOException {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addPropertiesLocal(project, "new.property", "value");
      springBootCommonApplicationService.addPropertiesLocalNewLine(project);

      String applicationProperties = getPath(MAIN_RESOURCES, "config/application-local.properties");
      assertFileContentRegexp(
        project,
        applicationProperties,
        "new.property=value" + LF + LF + "# jhipster-needle-application-local-properties"
      );
    }
  }

  @Nested
  class PropertiesTestNewLineIT {

    @Test
    void shouldNotAddPropertiesTestNewLine() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootCommonApplicationService.addPropertiesTestNewLine(project))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddPropertiesTestNewLine() throws IOException {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addPropertiesTest(project, "new.property", "value");
      springBootCommonApplicationService.addPropertiesTestNewLine(project);

      String applicationProperties = getPath(TEST_RESOURCES, "config/application.properties");
      assertFileContentRegexp(
        project,
        applicationProperties,
        "new.property=value" + LF + LF + "# jhipster-needle-application-test-properties"
      );
    }
  }

  @Nested
  class PropertiesTestLoggingNewLineIT {

    @Test
    void shouldNotAddPropertiesTestLoggingNewLine() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootCommonApplicationService.addPropertiesTestLoggingNewLine(project))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddPropertieTestsNewLine() throws IOException {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addPropertiesTestLogging(project, "tech.jhipster.beer", Level.WARN);
      springBootCommonApplicationService.addPropertiesTestLoggingNewLine(project);

      String applicationProperties = getPath(TEST_RESOURCES, "config/application.properties");
      assertFileContentRegexp(
        project,
        applicationProperties,
        "logging.level.tech.jhipster.beer=WARN" + LF + LF + "# jhipster-needle-application-test-logging-properties"
      );
    }
  }

  @Nested
  class PropertiesCommentIT {

    @Test
    void shouldNotAddPropertiesComment() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootCommonApplicationService.addPropertiesComment(project, "new comment"))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddPropertiesComment() {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addPropertiesComment(project, "new comment");

      String applicationProperties = getPath(MAIN_RESOURCES, "config/application.properties");
      assertFileContent(project, applicationProperties, "# new comment");
      assertFileContent(project, applicationProperties, "# jhipster-needle-application-properties");
    }
  }

  @Nested
  class PropertiesLocalCommentIT {

    @Test
    void shouldNotAddPropertiesLocalComment() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootCommonApplicationService.addPropertiesLocalComment(project, "new comment"))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddPropertiesLocalComment() {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addPropertiesLocalComment(project, "new comment");

      String applicationProperties = getPath(MAIN_RESOURCES, "config/application-local.properties");
      assertFileContent(project, applicationProperties, "# new comment");
      assertFileContent(project, applicationProperties, "# jhipster-needle-application-local-properties");
    }
  }

  @Nested
  class PropertiesTestCommentIT {

    @Test
    void shouldNotAddPropertiesTestComment() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootCommonApplicationService.addPropertiesTestComment(project, "new comment"))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddPropertiesTestComment() {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addPropertiesTestComment(project, "new comment");

      String applicationProperties = getPath(TEST_RESOURCES, "config/application.properties");
      assertFileContent(project, applicationProperties, "# new comment");
      assertFileContent(project, applicationProperties, "# jhipster-needle-application-test-properties");
    }
  }

  @Nested
  class PropertiesTestLoggingCommentIT {

    @Test
    void shouldNotAddPropertiesTestLoggingComment() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootCommonApplicationService.addPropertiesTestLoggingComment(project, "new comment"))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddPropertiesTestComment() {
      Project project = tmpProjectWithSpringBootProperties();

      springBootCommonApplicationService.addPropertiesTestLoggingComment(project, "new comment");

      String applicationProperties = getPath(TEST_RESOURCES, "config/application.properties");
      assertFileContent(project, applicationProperties, "# new comment");
      assertFileContent(project, applicationProperties, "# jhipster-needle-application-test-logging-properties");
    }
  }

  @Nested
  class LoggingConfigurationIT {

    @Test
    void shouldNotAddLogger() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootCommonApplicationService.addLogger(project, "tech.jhipster.web", Level.INFO))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddLoggerWithLevel() {
      Project project = tmpProjectWithSpringBootLoggingConfiguration();

      springBootCommonApplicationService.addLogger(project, "tech.jhipster.web", Level.DEBUG);

      String applicationProperties = getPath(MAIN_RESOURCES, "logback-spring.xml");
      assertFileContent(project, applicationProperties, "<logger name=\"tech.jhipster.web\" level=\"DEBUG\" />");
      assertFileContent(project, applicationProperties, "<!-- jhipster-needle-logback-add-log -->");
    }
  }

  @Nested
  class TestLoggingConfigurationIT {

    @Test
    void shouldNotAddTestLogger() {
      Project project = tmpProject();

      assertThatThrownBy(() -> springBootCommonApplicationService.addLoggerTest(project, "tech.jhipster.web", Level.INFO))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldAddLoggerWithLevel() {
      Project project = tmpProjectWithSpringBootLoggingConfiguration();

      springBootCommonApplicationService.addLoggerTest(project, "tech.jhipster.web", Level.DEBUG);

      String applicationProperties = getPath(TEST_RESOURCES, "logback.xml");
      assertFileContent(project, applicationProperties, "<logger name=\"tech.jhipster.web\" level=\"DEBUG\" />");
      assertFileContent(project, applicationProperties, "<!-- jhipster-needle-logback-add-log -->");
    }
  }

  @Test
  void shouldUpdateIntegrationTestAnnotation() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootCommonApplicationService.updateIntegrationTestAnnotation(project, "BeerContainer");

    String integrationTestFile = getPath(TEST_JAVA, DefaultConfig.PACKAGE_PATH, "IntegrationTest.java");
    assertFileContent(project, integrationTestFile, "public @interface");
    assertFileContent(project, integrationTestFile, "@ExtendWith(BeerContainer.class)");

    springBootCommonApplicationService.updateIntegrationTestAnnotation(project, "ChipsContainer");
    assertFileContent(project, integrationTestFile, "@ExtendWith(ChipsContainer.class)");
  }
}
