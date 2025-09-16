package com.seed4j.generator.server.javatool.memoizer.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class JavaMemoizersModuleFactoryTest {

  private static final JavaMemoizersModuleFactory factory = new JavaMemoizersModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFiles("src/main/java/com/seed4j/growth/shared/memoizer/package-info.java")
      .hasFiles("src/main/java/com/seed4j/growth/shared/memoizer/domain/Memoizers.java")
      .hasFiles("src/test/java/com/seed4j/growth/shared/memoizer/domain/MemoizersTest.java");
  }
}
