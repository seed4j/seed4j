package com.seed4j.generator.server.javatool.base.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.readmeFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class JavaBaseModuleFactoryTest {

  private static final JavaBaseModuleFactory factory = new JavaBaseModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, readmeFile())
      .hasPrefixedFiles("src/main/java/com/seed4j/growth", "BusinessContext.java", "SharedKernel.java")
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/shared/error/domain",
        "Assert.java",
        "AssertionErrorType.java",
        "MissingMandatoryValueException.java",
        "AssertionException.java",
        "NotAfterTimeException.java",
        "NotBeforeTimeException.java",
        "NullElementInCollectionException.java",
        "NumberValueTooHighException.java",
        "NumberValueTooLowException.java",
        "StringTooLongException.java",
        "StringTooShortException.java",
        "TooManyElementsException.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/shared/error/domain/",
        "AssertTest.java",
        "MissingMandatoryValueExceptionTest.java",
        "NotAfterTimeExceptionTest.java",
        "NotBeforeTimeExceptionTest.java",
        "NullElementInCollectionExceptionTest.java",
        "NumberValueTooHighExceptionTest.java",
        "NumberValueTooLowExceptionTest.java",
        "StringTooLongExceptionTest.java",
        "StringTooShortExceptionTest.java",
        "TooManyElementsExceptionTest.java"
      )
      .hasJavaTests(
        "com/seed4j/growth/shared/collection/domain/MyappCollectionsTest.java",
        "com/seed4j/growth/UnitTest.java",
        "com/seed4j/growth/ComponentTest.java",
        "com/seed4j/growth/ReplaceCamelCase.java"
      )
      .hasFiles(
        "src/main/java/com/seed4j/growth/shared/error/package-info.java",
        "src/main/java/com/seed4j/growth/shared/collection/package-info.java",
        "src/main/java/com/seed4j/growth/shared/generation/package-info.java",
        "src/main/java/com/seed4j/growth/shared/generation/domain/ExcludeFromGeneratedCodeCoverage.java"
      )
      .hasFile("src/main/java/com/seed4j/growth/shared/collection/domain/MyappCollections.java")
      .containing("class MyappCollections")
      .and()
      .hasPrefixedFiles("documentation", "package-types.md")
      .hasPrefixedFiles("documentation", "assertions.md")
      .hasFile("README.md")
      .containing("[Package types](documentation/package-types.md)")
      .containing("[Assertions](documentation/assertions.md)");
  }
}
