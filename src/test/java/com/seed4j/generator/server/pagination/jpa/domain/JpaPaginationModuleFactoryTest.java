package com.seed4j.generator.server.pagination.jpa.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class JpaPaginationModuleFactoryTest {

  private static final JpaPaginationModuleFactory factory = new JpaPaginationModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myApp")
      .build();

    SeedModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFiles("documentation/jpa-pages.md")
      .hasFiles("src/main/java/com/seed4j/growth/shared/pagination/infrastructure/secondary/MyAppPages.java")
      .hasFiles("src/test/java/com/seed4j/growth/shared/pagination/infrastructure/secondary/MyAppPagesTest.java");
  }
}
