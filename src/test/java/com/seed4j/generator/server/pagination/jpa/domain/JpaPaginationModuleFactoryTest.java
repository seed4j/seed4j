package com.seed4j.generator.server.pagination.jpa.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class JpaPaginationModuleFactoryTest {

  private static final JpaPaginationModuleFactory factory = new JpaPaginationModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myApp")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFiles("documentation/jpa-pages.md")
      .hasFiles("src/main/java/com/seed4j/growth/shared/pagination/infrastructure/secondary/MyAppPages.java")
      .hasFiles("src/test/java/com/seed4j/growth/shared/pagination/infrastructure/secondary/MyAppPagesTest.java");
  }
}
