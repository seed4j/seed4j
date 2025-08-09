package com.seed4j.generator.server.javatool.base.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.readmeFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class JavaBaseModuleFactoryTest {

  private static final JavaBaseModuleFactory factory = new JavaBaseModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

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
