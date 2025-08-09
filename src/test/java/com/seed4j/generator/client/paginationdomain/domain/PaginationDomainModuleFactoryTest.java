package com.seed4j.generator.client.paginationdomain.domain;

import static com.seed4j.module.domain.SeedModulesFixture.*;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class PaginationDomainModuleFactoryTest {

  private static final TSPaginationDomainModuleFactory factory = new TSPaginationDomainModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile()).hasPrefixedFiles(
      "src/main/webapp/app/shared/pagination/domain",
      "Page.ts",
      "DisplayedOnPage.ts"
    );
  }
}
