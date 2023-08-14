package tech.jhipster.lite.generator.server.pagination.jpa.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class JpaPaginationModuleFactoryTest {

  private static final JpaPaginationModuleFactory factory = new JpaPaginationModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myApp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFiles("documentation/jpa-pages.md")
      .hasFiles("src/main/java/com/jhipster/test/shared/pagination/infrastructure/secondary/MyAppPages.java")
      .hasFiles("src/test/java/com/jhipster/test/shared/pagination/infrastructure/secondary/MyAppPagesTest.java");
  }
}
