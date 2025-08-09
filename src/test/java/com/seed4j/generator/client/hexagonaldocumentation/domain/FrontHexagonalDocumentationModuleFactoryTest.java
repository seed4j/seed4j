package com.seed4j.generator.client.hexagonaldocumentation.domain;

import static com.seed4j.module.domain.SeedModulesFixture.*;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class FrontHexagonalDocumentationModuleFactoryTest {

  private static final FrontHexagonalDocumentationModuleFactory factory = new FrontHexagonalDocumentationModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    SeedModule module = factory.buildModule(properties);

    assertThatModule(module).hasFile("documentation/front-hexagonal-architecture.md");
  }
}
