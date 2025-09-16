package com.seed4j.generator.server.javatool.pbt.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class PropertyBasedTestingModuleFactoryTest {

  private final PropertyBasedTestingModuleFactory factory = new PropertyBasedTestingModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFiles("documentation/property-based-testing.md")
      .hasFile(".gitignore")
      .containing(
        """
        # JQwik
        .jqwik-database\
        """
      )
      .and()
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>net.jqwik</groupId>
              <artifactId>jqwik</artifactId>
              <version>${jqwik.version}</version>
              <scope>test</scope>
            </dependency>
        """
      );
  }
}
