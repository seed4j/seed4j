package com.seed4j.generator.client.restpagination.domain;

import static com.seed4j.module.domain.SeedModulesFixture.*;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class TSRestPaginationModuleFactoryTest {

  private static final TSRestPaginationModuleFactory factory = new TSRestPaginationModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile())
      .hasFiles("documentation/rest-page.md")
      .hasFiles("src/main/webapp/app/shared/pagination/infrastructure/secondary/RestPage.ts")
      .hasFiles("src/test/webapp/unit/shared/pagination/infrastructure/secondary/RestPage.spec.ts");
  }
}
