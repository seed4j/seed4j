package com.seed4j.generator.server.springboot.dbmigration.neo4j.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class Neo4jMigrationsModuleFactoryTest {

  private static final Neo4jMigrationModuleFactory factory = new Neo4jMigrationModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    Seed4JModule module = factory.buildModule(properties);

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
