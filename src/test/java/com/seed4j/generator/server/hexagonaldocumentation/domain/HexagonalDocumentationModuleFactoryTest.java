package com.seed4j.generator.server.hexagonaldocumentation.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class HexagonalDocumentationModuleFactoryTest {

  private static final HexagonalDocumentationModuleFactory factory = new HexagonalDocumentationModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, readmeFile())
      .hasPrefixedFiles("documentation", "hexagonal-architecture.md", "hexagonal-flow.png", "hexagonal-global-schema.png")
      .hasFile("README.md")
      .containing("[Hexagonal architecture](documentation/hexagonal-architecture.md)");
  }
}
