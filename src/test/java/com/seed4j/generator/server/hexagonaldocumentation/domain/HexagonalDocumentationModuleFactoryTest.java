package com.seed4j.generator.server.hexagonaldocumentation.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class HexagonalDocumentationModuleFactoryTest {

  private static final HexagonalDocumentationModuleFactory factory = new HexagonalDocumentationModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, readmeFile())
      .hasPrefixedFiles("documentation", "hexagonal-architecture.md", "hexagonal-flow.png", "hexagonal-global-schema.png")
      .hasFile("README.md")
      .containing("[Hexagonal architecture](documentation/hexagonal-architecture.md)");
  }
}
