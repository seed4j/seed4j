package tech.jhipster.lite.generator.server.pagination.rest.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class RestPaginationModuleFactoryTest {

  private static final RestPaginationModuleFactory factory = new RestPaginationModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myApp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFile("documentation/rest-pagination.md")
      .containing("MyAppPage<")
      .and()
      .hasPrefixedFiles(
        "src/main/java/com/jhipster/test/shared/pagination/infrastructure/primary",
        "RestMyAppPage.java",
        "RestMyAppPageable.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/jhipster/test/shared/pagination/infrastructure/primary",
        "RestMyAppPageTest.java",
        "RestMyAppPageableTest.java"
      );
  }
}
