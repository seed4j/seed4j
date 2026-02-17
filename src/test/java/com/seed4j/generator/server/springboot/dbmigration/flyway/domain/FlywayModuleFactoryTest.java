package com.seed4j.generator.server.springboot.dbmigration.flyway.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class FlywayModuleFactoryTest {

  private static final String INVOCATION_DATE = "2007-12-03T10:15:30.00Z";

  private static final FlywayModuleFactory factory = new FlywayModuleFactory();

  @Test
  void shouldBuildModuleInitializationModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .put("date", INVOCATION_DATE)
      .build();

    Seed4JModule module = factory.buildInitializationModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        // language=xml
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-flyway</artifactId>
            </dependency>
        """
      )
      .and()
      .hasFiles("src/main/resources/db/migration/V20071203101530__init.sql")
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          flyway:
            enabled: true
            locations: classpath:db/migration
        """
      );
  }

  @Test
  void shouldBuildMysqlDependencyModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildMysqlDependencyModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.flywaydb</groupId>
              <artifactId>flyway-mysql</artifactId>
            </dependency>
        """
      );
  }

  @Test
  void shouldBuildPostgreSQLDependencyModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildPostgreSQLDependencyModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.flywaydb</groupId>
              <artifactId>flyway-database-postgresql</artifactId>
            </dependency>
        """
      );
  }

  @Test
  void shouldBuildMsSqlServerDependencyModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildMsSqlServerDependencyModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.flywaydb</groupId>
              <artifactId>flyway-sqlserver</artifactId>
            </dependency>
        """
      );
  }
}
