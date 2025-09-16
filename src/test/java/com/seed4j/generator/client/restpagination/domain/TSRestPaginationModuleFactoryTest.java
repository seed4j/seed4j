package com.seed4j.generator.client.restpagination.domain;

import static com.seed4j.module.domain.Seed4JModulesFixture.*;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class TSRestPaginationModuleFactoryTest {

  private static final TSRestPaginationModuleFactory factory = new TSRestPaginationModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile())
      .hasFiles("documentation/rest-page.md")
      .hasFiles("src/main/webapp/app/shared/pagination/infrastructure/secondary/RestPage.ts")
      .hasFiles("src/test/webapp/unit/shared/pagination/infrastructure/secondary/RestPage.spec.ts");
  }
}
