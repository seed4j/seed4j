package com.seed4j.generator.server.pagination.jpa.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class JpaPaginationModuleFactoryTest {

  private static final JpaPaginationModuleFactory factory = new JpaPaginationModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myApp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFiles("documentation/jpa-pages.md")
      .hasFiles("src/main/java/tech/jhipster/jhlitest/shared/pagination/infrastructure/secondary/MyAppPages.java")
      .hasFiles("src/test/java/tech/jhipster/jhlitest/shared/pagination/infrastructure/secondary/MyAppPagesTest.java");
  }
}
