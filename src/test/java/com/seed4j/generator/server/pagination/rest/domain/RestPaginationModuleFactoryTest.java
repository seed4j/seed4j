package com.seed4j.generator.server.pagination.rest.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class RestPaginationModuleFactoryTest {

  private static final RestPaginationModuleFactory factory = new RestPaginationModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myApp")
      .build();

    Seed4JModule module = factory.buildModule(properties);

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
