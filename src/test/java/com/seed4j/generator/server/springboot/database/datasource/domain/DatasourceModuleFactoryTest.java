package com.seed4j.generator.server.springboot.database.datasource.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.file;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;
import static org.mockito.Mockito.when;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.docker.DockerImageName;
import com.seed4j.module.domain.docker.DockerImageVersion;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class DatasourceModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private DatasourceModuleFactory factory;

  @Test
  void shouldBuildPostgreSQLModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    when(dockerImages.get(new DockerImageName("postgres"))).thenReturn(new DockerImageVersion("postgres", "0.0.0"));

    JHipsterModule module = factory.buildPostgreSQL(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("documentation/postgresql.md")
      .containing("docker compose -f src/main/docker/postgresql.yml up -d")
      .and()
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.postgresql</groupId>
              <artifactId>postgresql</artifactId>
              <scope>runtime</scope>
            </dependency>
        """
      )
      .containing("<groupId>com.zaxxer</groupId>")
      .containing("<artifactId>HikariCP</artifactId>")
      .containing("<groupId>org.testcontainers</groupId>")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
          datasource:
            driver-class-name: org.postgresql.Driver
            hikari:
              auto-commit: false
              poolName: Hikari
            password: ''
            type: com.zaxxer.hikari.HikariDataSource
            url: jdbc:postgresql://localhost:5432/myapp
            username: myapp
        """
      )
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        """
        spring:
          datasource:
            driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
            hikari:
              maximum-pool-size: 2
            password: ''
            url: jdbc:tc:postgresql:0.0.0:///myapp?TC_TMPFS=/testtmpfs:rw
            username: myapp
        """
      );
  }

  @Test
  void shouldBuildMariadbModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    when(dockerImages.get(new DockerImageName("mariadb"))).thenReturn(new DockerImageVersion("mariadb", "0.0.0"));

    JHipsterModule module = factory.buildMariaDB(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("documentation/mariadb.md")
      .containing("docker compose -f src/main/docker/mariadb.yml up -d")
      .and()
      .hasPrefixedFiles("src/main/docker", "mariadb.yml")
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.mariadb.jdbc</groupId>
              <artifactId>mariadb-java-client</artifactId>
              <scope>runtime</scope>
            </dependency>
        """
      )
      .containing("<groupId>com.zaxxer</groupId>")
      .containing("<artifactId>HikariCP</artifactId>")
      .containing("<groupId>org.testcontainers</groupId>")
      .containing("<artifactId>mariadb</artifactId>")
      .and()
      .hasFile("docker-compose.yml")
      .containing("src/main/docker/mariadb.yml")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          datasource:
            driver-class-name: org.mariadb.jdbc.Driver
            hikari:
              auto-commit: false
              poolName: Hikari
            password: ''
            type: com.zaxxer.hikari.HikariDataSource
            url: jdbc:mariadb://localhost:3306/myapp
            username: root
        """
      )
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        """
        spring:
          datasource:
            driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
            hikari:
              maximum-pool-size: 2
            password: ''
            url: jdbc:tc:mariadb:0.0.0:///myapp
            username: myapp
        """
      );
  }

  @Test
  void shouldBuildMysqlModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    when(dockerImages.get(new DockerImageName("mysql"))).thenReturn(new DockerImageVersion("mysql", "0.0.0"));

    JHipsterModule module = factory.buildMySQL(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("documentation/mysql.md")
      .containing("docker compose -f src/main/docker/mysql.yml up -d")
      .and()
      .hasPrefixedFiles("src/main/docker", "mysql.yml")
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>com.mysql</groupId>
              <artifactId>mysql-connector-j</artifactId>
              <scope>runtime</scope>
            </dependency>
        """
      )
      .containing("<groupId>com.zaxxer</groupId>")
      .containing("<artifactId>HikariCP</artifactId>")
      .containing("<groupId>org.testcontainers</groupId>")
      .containing("<artifactId>mysql</artifactId>")
      .and()
      .hasFile("docker-compose.yml")
      .containing("src/main/docker/mysql.yml")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        // language=yaml
        """
        spring:
          datasource:
            driver-class-name: com.mysql.cj.jdbc.Driver
            hikari:
              auto-commit: false
              poolName: Hikari
            password: ''
            type: com.zaxxer.hikari.HikariDataSource
            url: jdbc:mysql://localhost:3306/myapp
            username: root
        """
      )
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        // language=yaml
        """
        spring:
          datasource:
            driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
            hikari:
              maximum-pool-size: 2
            password: ''
            url: jdbc:tc:mysql:0.0.0:///myapp
            username: myapp
        """
      );
  }

  @Test
  void shouldBuildMssqlModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    when(dockerImages.get(new DockerImageName("mcr.microsoft.com/mssql/server"))).thenReturn(
      new DockerImageVersion("mcr.microsoft.com/mssql/server", "0.0.0")
    );

    JHipsterModule module = factory.buildMsSQL(properties);

    assertThatModuleWithFiles(module, pomFile(), integrationTestAnnotation())
      .hasFile("documentation/mssql.md")
      .containing("docker compose -f src/main/docker/mssql.yml up -d")
      .and()
      .hasFile("src/test/java/com/seed4j/growth/MsSQLTestContainerExtension.java")
      .and()
      .hasFile("src/test/resources/container-license-acceptance.txt")
      .and()
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>com.microsoft.sqlserver</groupId>
              <artifactId>mssql-jdbc</artifactId>
              <scope>runtime</scope>
            </dependency>
        """
      )
      .containing("<groupId>com.zaxxer</groupId>")
      .containing("<artifactId>HikariCP</artifactId>")
      .containing("<groupId>org.testcontainers</groupId>")
      .containing("<artifactId>mssqlserver</artifactId>")
      .and()
      .hasFile("docker-compose.yml")
      .containing("src/main/docker/mssql.yml")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        // language=yaml
        """
        spring:
          datasource:
            driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver
            hikari:
              auto-commit: false
              poolName: Hikari
            password: yourStrong(!)Password
            type: com.zaxxer.hikari.HikariDataSource
            url: jdbc:sqlserver://localhost:1433;database=myapp;trustServerCertificate=true
            username: SA
        """
      )
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        // language=yaml
        """
        spring:
          datasource:
            driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
            hikari:
              maximum-pool-size: 2
            password: yourStrong(!)Password
            url: jdbc:tc:sqlserver:///;database=myapp;trustServerCertificate=true?TC_TMPFS=/testtmpfs:rw
            username: SA
        """
      );
  }

  private JHipsterModulesAssertions.ModuleFile integrationTestAnnotation() {
    return file("src/test/resources/projects/files/IntegrationTest.java", "src/test/java/com/seed4j/growth/IntegrationTest.java");
  }
}
