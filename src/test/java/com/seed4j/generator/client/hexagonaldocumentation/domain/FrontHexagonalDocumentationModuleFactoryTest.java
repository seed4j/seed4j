package com.seed4j.generator.client.hexagonaldocumentation.domain;

import static com.seed4j.module.domain.JHipsterModulesFixture.*;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class FrontHexagonalDocumentationModuleFactoryTest {

  private static final FrontHexagonalDocumentationModuleFactory factory = new FrontHexagonalDocumentationModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module).hasFile("documentation/front-hexagonal-architecture.md");
  }
}
