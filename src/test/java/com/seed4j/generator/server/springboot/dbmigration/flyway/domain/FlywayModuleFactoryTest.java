package com.seed4j.generator.server.springboot.dbmigration.flyway.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class FlywayModuleFactoryTest {

  private static final String INVOCATION_DATE = "2007-12-03T10:15:30.00Z";

  private static final FlywayModuleFactory factory = new FlywayModuleFactory();

  @Test
  void shouldBuildModuleInitializationModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .put("date", INVOCATION_DATE)
      .build();

    JHipsterModule module = factory.buildInitializationModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.flywaydb</groupId>
              <artifactId>flyway-core</artifactId>
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
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildMysqlDependencyModule(properties);

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
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildPostgreSQLDependencyModule(properties);

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
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildMsSqlServerDependencyModule(properties);

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
