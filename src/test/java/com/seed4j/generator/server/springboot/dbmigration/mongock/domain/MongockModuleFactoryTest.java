package com.seed4j.generator.server.springboot.dbmigration.mongock.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class MongockModuleFactoryTest {

  private static final MongockModuleFactory factory = new MongockModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFiles("documentation/mongock.md")
      .hasFile("pom.xml")
      .containing(
        """
              <dependency>
                <groupId>io.mongock</groupId>
                <artifactId>mongock-bom</artifactId>
                <version>${mongock.version}</version>
                <type>pom</type>
                <scope>import</scope>
              </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>io.mongock</groupId>
              <artifactId>mongock-springboot</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>io.mongock</groupId>
              <artifactId>mongodb-springdata-v4-driver</artifactId>
            </dependency>
        """
      )
      .and()
      .hasFiles("src/main/java/com/seed4j/growth/wire/mongock/infrastructure/secondary/MongockDatabaseConfiguration.java")
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        mongock:
          migration-scan-package: com.seed4j.growth
        """
      );
  }
}
