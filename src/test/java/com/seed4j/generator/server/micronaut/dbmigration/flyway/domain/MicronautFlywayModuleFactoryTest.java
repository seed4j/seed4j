package com.seed4j.generator.server.micronaut.dbmigration.flyway.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class MicronautFlywayModuleFactoryTest {

  private final MicronautFlywayModuleFactory factory = new MicronautFlywayModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("src/main/resources/db/migration/.gitkeep")
      .and()
      .hasFile("pom.xml")
      .containing(
        """
              <groupId>io.micronaut.flyway</groupId>
              <artifactId>micronaut-flyway</artifactId>\
        """
      )
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing("flyway:")
      .containing("datasources:")
      .containing("enabled: true");
  }
}
