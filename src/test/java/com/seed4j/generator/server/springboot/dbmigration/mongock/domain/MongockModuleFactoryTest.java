package com.seed4j.generator.server.springboot.dbmigration.mongock.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class MongockModuleFactoryTest {

  private static final MongockModuleFactory factory = new MongockModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    JHipsterModule module = factory.buildModule(properties);

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
