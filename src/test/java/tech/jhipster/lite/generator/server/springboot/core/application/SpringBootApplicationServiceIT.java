package tech.jhipster.lite.generator.server.springboot.core.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.Constants.POM_XML;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_RESOURCES;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class SpringBootApplicationServiceIT {

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    project.addConfig("springBootVersion", "0.0.0");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    mavenApplicationService.addMavenWrapper(project);

    springBootApplicationService.init(project);

    assertFileContent(project, POM_XML, "<artifactId>spring-boot-dependencies</artifactId>");
    assertFileContent(project, POM_XML, "<version>${spring-boot.version}</version>");

    assertFileContent(project, POM_XML, "<groupId>org.springframework.boot</groupId>");
    assertFileContent(project, POM_XML, "<artifactId>spring-boot-starter</artifactId>");

    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-configuration-processor</artifactId>",
        "<optional>true</optional>",
        "</dependency>"
      )
    );

    assertFileContent(project, POM_XML, "<groupId>org.apache.commons</groupId>");
    assertFileContent(project, POM_XML, "<artifactId>commons-lang3</artifactId>");
    assertFileContent(project, POM_XML, "<artifactId>spring-boot-starter-test</artifactId>");

    assertFileContent(project, POM_XML, "<groupId>org.springframework.boot</groupId>");
    assertFileContent(project, POM_XML, "<artifactId>spring-boot-maven-plugin</artifactId>");

    assertFileExist(project, getPath(MAIN_JAVA, "com/mycompany/myapp/JhipsterApp.java"));
    assertFileExist(project, getPath(TEST_JAVA, "com/mycompany/myapp/JhipsterAppTest.java"));
    assertFileExist(project, getPath(TEST_JAVA, "com/mycompany/myapp", "IntegrationTest.java"));

    assertFileExist(project, getPath(MAIN_RESOURCES, "config/application.properties"));
    assertFileExist(project, getPath(MAIN_RESOURCES, "config/application-local.properties"));
    assertFileExist(project, getPath(TEST_RESOURCES, "config/application.properties"));
    assertFileExist(project, getPath(MAIN_RESOURCES, "logback-spring.xml"));
    assertFileExist(project, getPath(TEST_RESOURCES, "logback.xml"));
  }

  @Test
  void shouldAddSpringBootDependenciesBOM() {
    Project project = tmpProject();
    project.addConfig("springBootVersion", "0.0.0");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    springBootApplicationService.addSpringBootDependenciesBOM(project);
    assertFileContent(project, POM_XML, "<artifactId>spring-boot-dependencies</artifactId>");
    assertFileContent(project, POM_XML, "<version>${spring-boot.version}</version>");

    // add again the parent, with wrong version
    project.addConfig("springBootVersion", "X.X.X");
    springBootApplicationService.addSpringBootDependenciesBOM(project);
    assertFileContent(project, POM_XML, "<artifactId>spring-boot-dependencies</artifactId>");
    assertFileContent(project, POM_XML, "<version>${spring-boot.version}</version>");
  }

  @Test
  void shouldNotAddSpringBootDependenciesBOMWhenNoPomXml() {
    Project project = tmpProject();

    assertThatThrownBy(() -> springBootApplicationService.addSpringBootDependenciesBOM(project))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddSpringBootDependencies() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    springBootApplicationService.addSpringBootDependencies(project);

    assertFileContent(project, POM_XML, "<groupId>org.springframework.boot</groupId>");
    assertFileContent(project, POM_XML, "<artifactId>spring-boot-starter</artifactId>");

    assertFileContent(project, POM_XML, "<groupId>org.apache.commons</groupId>");
    assertFileContent(project, POM_XML, "<artifactId>commons-lang3</artifactId>");

    assertFileContent(project, POM_XML, "<artifactId>spring-boot-starter-test</artifactId>");
  }

  @Test
  void shouldNotAddSpringBootDependenciesWhenNoPomXml() {
    Project project = tmpProject();

    assertThatThrownBy(() -> springBootApplicationService.addSpringBootDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddSpringBootPluginManagement() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    springBootApplicationService.addSpringBootMavenPluginManagement(project);

    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<plugin>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-maven-plugin</artifactId>",
        "<version>${spring-boot.version}</version>",
        "<executions>",
        "<execution>",
        "<goals>",
        "<goal>repackage</goal>",
        "</goals>",
        "</execution>",
        "</executions>",
        "<configuration>",
        "<mainClass>${start-class}</mainClass>",
        "</configuration>",
        "</plugin>"
      )
    );
    assertFileContent(project, POM_XML, "<spring-boot.version>");
    assertFileContent(project, POM_XML, "</spring-boot.version>");
  }

  @Test
  void shouldNotAddSpringBootPluginManagementWhenNoPomXml() {
    Project project = tmpProject();

    assertThatThrownBy(() -> springBootApplicationService.addSpringBootMavenPluginManagement(project))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddSpringBootPlugin() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    springBootApplicationService.addSpringBootMavenPlugin(project);

    assertFileContent(
      project,
      POM_XML,
      List.of("<plugin>", "<groupId>org.springframework.boot</groupId>", "<artifactId>spring-boot-maven-plugin</artifactId>", "</plugin>")
    );
  }

  @Test
  void shouldAddMainApp() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    springBootApplicationService.addMainApp(project);

    assertFileExist(project, "src/main/java/com/mycompany/myapp/JhipsterApp.java");
    assertFileExist(project, "src/test/java/com/mycompany/myapp/JhipsterAppTest.java");
  }

  @Test
  void shouldAddApplicationProperties() {
    Project project = tmpProject();
    projectApplicationService.init(project);

    springBootApplicationService.addApplicationProperties(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "config/application.properties"));
  }

  @Test
  void shouldAddApplicationLocalProperties() {
    Project project = tmpProject();
    projectApplicationService.init(project);

    springBootApplicationService.addApplicationLocalProperties(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "config/application-local.properties"));
  }

  @Test
  void shouldAddApplicationTestProperties() {
    Project project = tmpProject();
    projectApplicationService.init(project);

    springBootApplicationService.addApplicationTestProperties(project);

    assertFileExist(project, getPath(TEST_RESOURCES, "config/application.properties"));
  }

  @Test
  void shouldAddLoggingConfiguration() {
    Project project = tmpProject();
    projectApplicationService.init(project);

    springBootApplicationService.addLoggingConfiguration(project);

    assertFileExist(project, getPath(MAIN_RESOURCES, "logback-spring.xml"));
  }

  @Test
  void shouldAddLoggingTestConfiguration() {
    Project project = tmpProject();
    projectApplicationService.init(project);

    springBootApplicationService.addLoggingTestConfiguration(project);

    assertFileExist(project, getPath(TEST_RESOURCES, "logback.xml"));
  }
}
