package com.seed4j.generator.server.pagination.rest.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class RestPaginationModuleFactoryTest {

  private static final RestPaginationModuleFactory factory = new RestPaginationModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myApp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFile("documentation/rest-pagination.md")
      .containing("MyAppPage<")
      .and()
      .hasPrefixedFiles(
        "src/main/java/tech/jhipster/jhlitest/shared/pagination/infrastructure/primary",
        "RestMyAppPage.java",
        "RestMyAppPageable.java"
      )
      .hasPrefixedFiles(
        "src/test/java/tech/jhipster/jhlitest/shared/pagination/infrastructure/primary",
        "RestMyAppPageTest.java",
        "RestMyAppPageableTest.java"
      );
  }
}
