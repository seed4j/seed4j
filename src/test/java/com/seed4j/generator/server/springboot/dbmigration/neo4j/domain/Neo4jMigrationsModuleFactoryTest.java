package com.seed4j.generator.server.springboot.dbmigration.neo4j.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class Neo4jMigrationsModuleFactoryTest {

  private static final Neo4jMigrationModuleFactory factory = new Neo4jMigrationModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFiles("documentation/neo4j-migrations.md")
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>eu.michael-simons.neo4j</groupId>
              <artifactId>neo4j-migrations-spring-boot-starter</artifactId>
              <version>${neo4j-migrations.version}</version>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        org:
          neo4j:
            migrations:
              check-location: false
        """
      );
  }
}
