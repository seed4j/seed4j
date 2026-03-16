package com.seed4j.generator.server.micronaut.database.jpa.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class MicronautDataJpaModuleFactoryTest {

  private final MicronautDataJpaModuleFactory factory = new MicronautDataJpaModuleFactory();

  @Test
  void shouldBuildPostgreSQLModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildPostgreSQL(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("src/main/java/com/seed4j/growth/wire/database/infrastructure/secondary/DatabaseConfiguration.java")
      .containing("package com.seed4j.growth.wire.database.infrastructure.secondary;")
      .and()
      .hasFile("pom.xml")
      .containing(
        """
              <groupId>io.micronaut.data</groupId>
              <artifactId>micronaut-data-hibernate-jpa</artifactId>\
        """
      )
      .containing(
        """
              <groupId>io.micronaut.sql</groupId>
              <artifactId>micronaut-jdbc-hikari</artifactId>\
        """
      )
      .containing(
        """
              <groupId>org.postgresql</groupId>
              <artifactId>postgresql</artifactId>\
        """
      )
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing("datasources:")
      .containing("db-type: postgresql")
      .containing("jpa:");
  }

  @Test
  void shouldBuildMySQLModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildMySQL(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
              <groupId>com.mysql</groupId>
              <artifactId>mysql-connector-j</artifactId>\
        """
      )
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing("dialect: MYSQL");
  }

  @Test
  void shouldBuildMariaDBModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildMariaDB(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
              <groupId>org.mariadb.jdbc</groupId>
              <artifactId>mariadb-java-client</artifactId>\
        """
      );
  }

  @Test
  void shouldBuildMsSQLModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildMsSQL(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
              <groupId>com.microsoft.sqlserver</groupId>
              <artifactId>mssql-jdbc</artifactId>\
        """
      )
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing("dialect: SQL_SERVER");
  }
}
