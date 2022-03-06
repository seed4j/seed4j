package tech.jhipster.lite.generator.server.springboot.database.mysql.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.Constants.POM_XML;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.server.springboot.database.mysql.domain.MySQL;

@IntegrationTest
class MySQLApplicationServiceIT {

  @Autowired
  MySQLApplicationService mysqlApplicationService;

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    mysqlApplicationService.init(project);

    assertFileContent(project, POM_XML, springBootStarterDataJpa());
    assertFileContent(project, POM_XML, mySQLDriver());
    assertFileContent(project, POM_XML, hikari());
    assertFileContent(project, POM_XML, hibernateCore());

    assertFileExist(project, "src/main/docker/mysql.yml");
    assertFileContent(project, "src/main/docker/mysql.yml", "MYSQL_DATABASE=jhipster");

    String mysqlPath = getPath("com/mycompany/myapp/technical/infrastructure/secondary/mysql");
    assertFileExist(project, getPath(MAIN_JAVA, mysqlPath, "DatabaseConfiguration.java"));

    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES),
      "spring.datasource.url=jdbc:mysql://localhost:3306/jhipster"
    );
    assertTestContainersWereAdded(project);
    assertLoggerInConfig(project);
  }

  private void assertTestContainersWereAdded(Project project) {
    assertFileContent(project, POM_XML, "<testcontainers.version>");
    assertFileContent(project, POM_XML, "</testcontainers.version>");
    assertFileContent(project, POM_XML, testcontainers());
    assertFileContent(
      project,
      getPath(TEST_RESOURCES, "config/application.properties"),
      List.of("spring.datasource.url=jdbc:tc:" + MySQL.getDockerImageName() + ":///jhipster", "spring.datasource.username=jhipster")
    );
  }

  @Test
  void shouldAddSpringDataJpa() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    mysqlApplicationService.addSpringDataJpa(project);

    assertFileContent(project, POM_XML, springBootStarterDataJpa());
  }

  @Test
  @DisplayName("should add mysql driver")
  void shouldAddMySQLDriver() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    mysqlApplicationService.addMySQLDriver(project);

    assertFileContent(project, POM_XML, mySQLDriver());
  }

  @Test
  void shouldAddHikari() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    mysqlApplicationService.addHikari(project);

    assertFileContent(project, POM_XML, hikari());
  }

  @Test
  void shouldAddHibernateCore() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    mysqlApplicationService.addHibernateCore(project);

    assertFileContent(project, POM_XML, hibernateCore());
  }

  @Test
  void shouldAddDockerCompose() {
    Project project = tmpProject();

    mysqlApplicationService.addDockerCompose(project);

    assertFileExist(project, "src/main/docker/mysql.yml");
    assertFileContent(project, "src/main/docker/mysql.yml", "MYSQL_DATABASE=jhipster");
  }

  @Test
  void shouldAddDockerComposeWithBaseName() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "chips");

    mysqlApplicationService.addDockerCompose(project);

    assertFileExist(project, "src/main/docker/mysql.yml");
    assertFileContent(project, "src/main/docker/mysql.yml", "MYSQL_DATABASE=chips");
  }

  @Test
  void shouldAddJavaFiles() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "chips");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.chips");

    mysqlApplicationService.addJavaFiles(project);

    assertFileExist(
      project,
      getPath(MAIN_JAVA, "tech/jhipster/chips", "/technical/infrastructure/secondary/mysql/DatabaseConfiguration.java")
    );
  }

  @Test
  void shouldAddProperties() {
    Project project = tmpProject();
    project.addConfig(PACKAGE_NAME, "tech.jhipster.chips");
    project.addConfig(BASE_NAME, "chips");
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    mysqlApplicationService.addProperties(project);

    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES),
      List.of(
        "spring.datasource.hikari.auto-commit=false",
        "spring.datasource.hikari.poolName=Hikari",
        "spring.datasource.password=",
        "spring.datasource.type=com.zaxxer.hikari.HikariDataSource",
        "spring.datasource.url=jdbc:mysql://localhost:3306/chips",
        "spring.datasource.username=root"
      )
    );
  }

  @Test
  void shouldAddLoggingConfiguration() {
    Project project = tmpProject();
    project.addConfig(PACKAGE_NAME, "tech.jhipster.chips");
    project.addConfig(BASE_NAME, "chips");
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    mysqlApplicationService.addLogger(project);

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
    assertFileContent(
      project,
      getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES),
      List.of(
        "logging.level.org.hibernate=WARN",
        "logging.level.org.hibernate.ejb.HibernatePersistence=OFF",
        "logging.level.org.hibernate.validator=WARN",
        "logging.level.com.github.dockerjava=WARN",
        "logging.level.org.testcontainers=WARN"
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

  private List<String> mySQLDriver() {
    return List.of("<dependency>", "<groupId>mysql</groupId>", "<artifactId>mysql-connector-java</artifactId>", "</dependency>");
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
      "<artifactId>mysql</artifactId>",
      "<version>${testcontainers.version}</version>",
      "<scope>test</scope>",
      "</dependency>"
    );
  }
}
