package com.seed4j.generator.server.micronaut.database.jpa;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.fileFromClasspath;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.ModuleFile;
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

    assertThatModuleWithFiles(
      module,
      pomFile(),
      logbackFile("src/main/resources/logback.xml"),
      logbackFile("src/test/resources/logback-test.xml")
    )
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
                    <path>
                      <groupId>io.micronaut.data</groupId>
                      <artifactId>micronaut-data-processor</artifactId>
                    </path>
        """
      )
      .containing(
        """
              <groupId>org.postgresql</groupId>
              <artifactId>postgresql</artifactId>\
        """
      )
      .and()
      .hasFile("src/main/resources/application.yml")
      .containing("datasources:")
      .containing("db-type: postgres")
      .containing("physical_naming_strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategySnakeCaseImpl")
      .notContaining("implicit_naming_strategy")
      .notContaining("org.springframework")
      .containing("jpa:")
      .and()
      .hasFile("src/main/resources/logback.xml")
      .containing("<logger name=\"org.hibernate.orm\" level=\"WARN\" />")
      .and()
      .hasFile("src/test/resources/logback-test.xml")
      .containing("<logger name=\"org.hibernate.validator\" level=\"WARN\" />");
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
      .hasFile("src/main/resources/application.yml")
      .containing("db-type: mysql")
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
      )
      .and()
      .hasFile("src/main/resources/application.yml")
      .containing("db-type: mariadb");
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
      .hasFile("src/main/resources/application.yml")
      .containing("db-type: mssql")
      .containing("dialect: SQL_SERVER");
  }

  private static ModuleFile logbackFile(String destination) {
    return fileFromClasspath("generator/server/springboot/core/test/logback.xml.mustache", destination);
  }
}
