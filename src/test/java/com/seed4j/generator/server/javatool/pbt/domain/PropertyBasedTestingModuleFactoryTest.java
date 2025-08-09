package com.seed4j.generator.server.javatool.pbt.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class PropertyBasedTestingModuleFactoryTest {

  private final PropertyBasedTestingModuleFactory factory = new PropertyBasedTestingModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    SeedModule module = factory.buildModule(properties);

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
