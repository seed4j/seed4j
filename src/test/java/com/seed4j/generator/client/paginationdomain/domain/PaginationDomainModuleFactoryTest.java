package com.seed4j.generator.client.paginationdomain.domain;

import static com.seed4j.module.domain.Seed4JModulesFixture.*;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class PaginationDomainModuleFactoryTest {

  private static final TSPaginationDomainModuleFactory factory = new TSPaginationDomainModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile()).hasPrefixedFiles(
      "src/main/webapp/app/shared/pagination/domain",
      "Page.ts",
      "DisplayedOnPage.ts"
    );
  }
}
