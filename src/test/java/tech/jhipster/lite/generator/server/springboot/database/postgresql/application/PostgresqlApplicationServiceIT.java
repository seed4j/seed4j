package tech.jhipster.lite.generator.server.springboot.database.postgresql.application;

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
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;
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
import tech.jhipster.lite.generator.tools.domain.Project;

@IntegrationTest
class PostgresqlApplicationServiceIT {

  @Autowired
  PostgresqlApplicationService postgresqlApplicationService;

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

    postgresqlApplicationService.init(project);

    assertFileContent(project, POM_XML, springBootStarterDataJpa());
    assertFileContent(project, POM_XML, postgreSQLDriver());
    assertFileContent(project, POM_XML, hikari());
    assertFileContent(project, POM_XML, hibernateCore());

    assertFileExist(project, "src/main/docker/postgresql.yml");
    assertFileContent(project, "src/main/docker/postgresql.yml", "POSTGRES_USER=jhipster");

    String postgresqlPath = getPath("com/mycompany/myapp/technical/infrastructure/secondary/postgresql");
    assertFileExist(project, getPath(MAIN_JAVA, postgresqlPath, "DatabaseConfiguration.java"));
    assertFileExist(project, getPath(MAIN_JAVA, postgresqlPath, "FixedPostgreSQL10Dialect.java"));
    assertFileExist(project, getPath(TEST_JAVA, postgresqlPath, "FixedPostgreSQL10DialectTest.java"));

    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES),
      "spring.datasource.url=jdbc:postgresql://localhost:5432/jhipster"
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
      List.of("spring.datasource.url=jdbc:tc:postgresql:14.2:///jhipster?TC_TMPFS=/testtmpfs:rw", "spring.datasource.username=jhipster")
    );
  }

  @Test
  void shouldAddSpringDataJpa() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    postgresqlApplicationService.addSpringDataJpa(project);

    assertFileContent(project, POM_XML, springBootStarterDataJpa());
  }

  @Test
  @DisplayName("should add postgresql driver")
  void shouldAddPostgreSQLDriver() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    postgresqlApplicationService.addPostgreSQLDriver(project);

    assertFileContent(project, POM_XML, postgreSQLDriver());
  }

  @Test
  void shouldAddHikari() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    postgresqlApplicationService.addHikari(project);

    assertFileContent(project, POM_XML, hikari());
  }

  @Test
  void shouldAddHibernateCore() {
    Project project = tmpProject();
    projectApplicationService.init(project);
    mavenApplicationService.addPomXml(project);

    postgresqlApplicationService.addHibernateCore(project);

    assertFileContent(project, POM_XML, hibernateCore());
  }

  @Test
  void shouldAddDockerCompose() {
    Project project = tmpProject();

    postgresqlApplicationService.addDockerCompose(project);

    assertFileExist(project, "src/main/docker/postgresql.yml");
    assertFileContent(project, "src/main/docker/postgresql.yml", "postgres:14.2");
    assertFileContent(project, "src/main/docker/postgresql.yml", "POSTGRES_USER=jhipster");
  }

  @Test
  void shouldAddDockerComposeWithBaseName() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "chips");

    postgresqlApplicationService.addDockerCompose(project);

    assertFileExist(project, "src/main/docker/postgresql.yml");
    assertFileContent(project, "src/main/docker/postgresql.yml", "postgres:14.2");
    assertFileContent(project, "src/main/docker/postgresql.yml", "POSTGRES_USER=chips");
  }

  @Test
  void shouldAddDialectJava() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "chips");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.chips");

    postgresqlApplicationService.addDialectJava(project);

    assertFileExist(
      project,
      getPath(MAIN_JAVA, "tech/jhipster/chips", "/technical/infrastructure/secondary/postgresql/FixedPostgreSQL10Dialect.java")
    );
    assertFileExist(
      project,
      getPath(TEST_JAVA, "tech/jhipster/chips", "/technical/infrastructure/secondary/postgresql/FixedPostgreSQL10DialectTest.java")
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

    postgresqlApplicationService.addProperties(project);

    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES),
      List.of(
        "spring.datasource.driver-class-name=org.postgresql.Driver",
        "spring.datasource.hikari.auto-commit=false",
        "spring.datasource.hikari.poolName=Hikari",
        "spring.datasource.password=",
        "spring.datasource.type=com.zaxxer.hikari.HikariDataSource",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/chips",
        "spring.datasource.username=chips"
      )
    );

    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES),
      List.of("spring.jpa.database-platform=tech.jhipster.chips.technical.infrastructure.secondary.postgresql.FixedPostgreSQL10Dialect")
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

    postgresqlApplicationService.addLogger(project);

    assertLoggerInConfig(project);
  }

  private void assertLoggerInConfig(Project project) {
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, LOGGING_CONFIGURATION),
      List.of(
        "<logger name=\"org.hibernate\" level=\"WARN\" />",
        "<logger name=\"org.hibernate.ejb.HibernatePersistence\" level=\"OFF\" />",
        "<logger name=\"org.hibernate.validator\" level=\"WARN\" />",
        "<logger name=\"org.postgresql\" level=\"WARN\" />"
      )
    );

    assertFileContent(
      project,
      getPath(TEST_RESOURCES, LOGGING_TEST_CONFIGURATION),
      List.of(
        "<logger name=\"org.hibernate\" level=\"WARN\" />",
        "<logger name=\"org.hibernate.ejb.HibernatePersistence\" level=\"OFF\" />",
        "<logger name=\"org.hibernate.validator\" level=\"WARN\" />",
        "<logger name=\"org.postgresql\" level=\"WARN\" />",
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

  private List<String> postgreSQLDriver() {
    return List.of("<dependency>", "<groupId>org.postgresql</groupId>", "<artifactId>postgresql</artifactId>", "</dependency>");
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
      "<artifactId>postgresql</artifactId>",
      "<version>${testcontainers.version}</version>",
      "<scope>test</scope>",
      "</dependency>"
    );
  }
}
