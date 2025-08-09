package com.seed4j.generator.server.pagination.rest.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class RestPaginationModuleFactoryTest {

  private static final RestPaginationModuleFactory factory = new RestPaginationModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myApp")
      .build();

    SeedModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFile("documentation/rest-pagination.md")
      .containing("MyAppPage<")
      .and()
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/shared/pagination/infrastructure/primary",
        "RestMyAppPage.java",
        "RestMyAppPageable.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/shared/pagination/infrastructure/primary",
        "RestMyAppPageTest.java",
        "RestMyAppPageableTest.java"
      );
  }
}
