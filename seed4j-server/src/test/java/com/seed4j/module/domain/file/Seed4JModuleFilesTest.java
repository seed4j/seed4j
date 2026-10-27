package com.seed4j.module.domain.file;

import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModulesFixture.testModuleProperties;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModule;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JModuleFilesTest {

  @Test
  void shouldAddNullMarkedPackageInfo() {
    Seed4JModule module = moduleBuilder(testModuleProperties())
      .files()
      .nullMarkedJavaPackage(toSrcMainJava().append("com/test/myapp/shared/error/domain"))
      .and()
      .build();

    assertThatModule(module)
      .hasFile("src/main/java/com/test/myapp/shared/error/domain/package-info.java")
      .containing(
        """
        @org.jspecify.annotations.NullMarked
        package com.test.myapp.shared.error.domain;
        """
      );
  }
}
