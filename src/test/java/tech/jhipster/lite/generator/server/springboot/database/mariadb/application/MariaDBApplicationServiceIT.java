package tech.jhipster.lite.generator.server.springboot.database.mariadb.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.APPLICATION_PROPERTIES;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.LOGGING_CONFIGURATION;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.LOGGING_TEST_CONFIGURATION;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.Constants.POM_XML;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_RESOURCES;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_NAME;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.application.ProjectApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.server.springboot.database.mariadb.domain.MariaDB;
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class MariaDBApplicationServiceIT {

  @Autowired
  MariaDBApplicationService mariaDBApplicationService;

  @Autowired
  ProjectApplicationService projectApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    mariaDBApplicationService.init(project);

    assertFileContent(project, POM_XML, springBootStarterDataJpa());
    assertFileContent(project, POM_XML, mariaDBDriver());
    assertFileContent(project, POM_XML, hikari());
    assertFileContent(project, POM_XML, hibernateCore());

    assertFileExist(project, "src/main/docker/mariadb.yml");
    assertFileContent(project, "src/main/docker/mariadb.yml", "MYSQL_DATABASE=jhipster");

    String mariadbPath = getPath("com/mycompany/myapp/technical/infrastructure/secondary/mariadb");
    assertFileExist(project, getPath(MAIN_JAVA, mariadbPath, "DatabaseConfiguration.java"));

    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES),
      "spring.datasource.url=jdbc:mariadb://localhost:3306/jhipster"
    );
    assertTestContainersWasAdded(project);
    assertLoggerInConfig(project);
  }

  private void assertTestContainersWasAdded(Project project) {
    assertFileContent(project, POM_XML, "<testcontainers.version>");
    assertFileContent(project, POM_XML, "</testcontainers.version>");
    assertFileContent(project, POM_XML, testcontainers());
    assertFileContent(
      project,
      getPath(TEST_RESOURCES, "config/application.properties"),
      List.of("spring.datasource.url=jdbc:tc:" + MariaDB.getDockerImageName() + ":///jhipster", "spring.datasource.username=jhipster")
    );
  }

  @Test
  void shouldAddSpringDataJpa() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    mariaDBApplicationService.addSpringDataJpa(project);

    assertFileContent(project, POM_XML, springBootStarterDataJpa());
  }

  @Test
  @DisplayName("should add mariadb driver")
  void shouldAddMariaDBDriver() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    mariaDBApplicationService.addMariaDBDriver(project);

    assertFileContent(project, POM_XML, mariaDBDriver());
  }

  @Test
  void shouldAddHikari() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    mariaDBApplicationService.addHikari(project);

    assertFileContent(project, POM_XML, hikari());
  }

  @Test
  void shouldAddHibernateCore() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    mariaDBApplicationService.addHibernateCore(project);

    assertFileContent(project, POM_XML, hibernateCore());
  }

  @Test
  void shouldAddDockerCompose() {
    Project project = tmpProject();

    mariaDBApplicationService.addDockerCompose(project);

    assertFileExist(project, "src/main/docker/mariadb.yml");
    assertFileContent(project, "src/main/docker/mariadb.yml", "mariadb:10.8.2");
    assertFileContent(project, "src/main/docker/mariadb.yml", "MYSQL_DATABASE=jhipster");
  }

  @Test
  void shouldAddDockerComposeWithBaseName() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "chips");

    mariaDBApplicationService.addDockerCompose(project);

    assertFileExist(project, "src/main/docker/mariadb.yml");
    assertFileContent(project, "src/main/docker/mariadb.yml", "MYSQL_DATABASE=chips");
  }

  @Test
  void shouldAddJavaFiles() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "chips");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.chips");

    mariaDBApplicationService.addJavaFiles(project);

    assertFileExist(
      project,
      getPath(MAIN_JAVA, "tech/jhipster/chips", "/technical/infrastructure/secondary/mariadb/DatabaseConfiguration.java")
    );
  }

  @Test
  void shouldAddProperties() {
    Project project = tmpProject();
    project.addConfig(PACKAGE_NAME, "tech.jhipster.chips");
    project.addConfig(BASE_NAME, "chips");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    mariaDBApplicationService.addProperties(project);

    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES),
      List.of(
        "spring.datasource.hikari.auto-commit=false",
        "spring.datasource.hikari.poolName=Hikari",
        "spring.datasource.password=",
        "spring.datasource.type=com.zaxxer.hikari.HikariDataSource",
        "spring.datasource.url=jdbc:mariadb://localhost:3306/chips",
        "spring.datasource.username=root"
      )
    );
  }

  @Test
  void shouldAddLoggingConfiguration() {
    Project project = tmpProject();
    project.addConfig(PACKAGE_NAME, "tech.jhipster.chips");
    project.addConfig(BASE_NAME, "chips");
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    mariaDBApplicationService.addLogger(project);

    assertLoggerInConfig(project);
  }

  private void assertLoggerInConfig(Project project) {
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, LOGGING_CONFIGURATION),
      List.of(
        "<logger name=\"org.hibernate\" level=\"WARN\" />",
        "<logger name=\"org.hibernate.ejb.HibernatePersistence\" level=\"OFF\" />",
        "<logger name=\"org.hibernate.validator\" level=\"WARN\" />"
      )
    );

    assertFileContent(
      project,
      getPath(TEST_RESOURCES, LOGGING_TEST_CONFIGURATION),
      List.of(
        "<logger name=\"org.hibernate\" level=\"WARN\" />",
        "<logger name=\"org.hibernate.ejb.HibernatePersistence\" level=\"OFF\" />",
        "<logger name=\"org.hibernate.validator\" level=\"WARN\" />",
        "<logger name=\"com.github.dockerjava\" level=\"WARN\" />",
        "<logger name=\"org.testcontainers\" level=\"WARN\" />"
      )
    );
  }

  private List<String> springBootStarterDataJpa() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.boot</groupId>",
      "<artifactId>spring-boot-starter-data-jpa</artifactId>",
      "</dependency>"
    );
  }

  private List<String> mariaDBDriver() {
    return List.of("<dependency>", "<groupId>org.mariadb.jdbc</groupId>", "<artifactId>mariadb-java-client</artifactId>", "</dependency>");
  }

  private List<String> hikari() {
    return List.of("<dependency>", "<groupId>com.zaxxer</groupId>", "<artifactId>HikariCP</artifactId>", "</dependency>");
  }

  private List<String> hibernateCore() {
    return List.of("<dependency>", "<groupId>org.hibernate</groupId>", "<artifactId>hibernate-core</artifactId>", "</dependency>");
  }

  private List<String> testcontainers() {
    return List.of(
      "<dependency>",
      "<groupId>org.testcontainers</groupId>",
      "<artifactId>mariadb</artifactId>",
      "<version>${testcontainers.version}</version>",
      "<scope>test</scope>",
      "</dependency>"
    );
  }
}
