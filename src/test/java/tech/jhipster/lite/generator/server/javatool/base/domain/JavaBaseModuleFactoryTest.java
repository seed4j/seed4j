package tech.jhipster.lite.generator.server.javatool.base.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.readmeFile;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class JavaBaseModuleFactoryTest {

  private static final JavaBaseModuleFactory factory = new JavaBaseModuleFactory();

  @Test
  void shouldCreateModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, readmeFile())
      .hasPrefixedFiles("src/main/java/com/jhipster/test", "BusinessContext.java", "SharedKernel.java")
      .hasPrefixedFiles(
        "src/main/java/com/jhipster/test/shared/error/domain",
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
        "src/test/java/com/jhipster/test/shared/error/domain/",
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
        "com/jhipster/test/shared/collection/domain/MyappCollectionsTest.java",
        "com/jhipster/test/UnitTest.java",
        "com/jhipster/test/ComponentTest.java",
        "com/jhipster/test/ReplaceCamelCase.java"
      )
      .hasFiles(
        "src/main/java/com/jhipster/test/shared/error/package-info.java",
        "src/main/java/com/jhipster/test/shared/collection/package-info.java",
        "src/main/java/com/jhipster/test/shared/generation/package-info.java",
        "src/main/java/com/jhipster/test/shared/generation/domain/ExcludeFromGeneratedCodeCoverage.java"
      )
      .hasFile("src/main/java/com/jhipster/test/shared/collection/domain/MyappCollections.java")
      .containing("class MyappCollections")
      .and()
      .hasPrefixedFiles("documentation", "package-types.md")
      .hasPrefixedFiles("documentation", "assertions.md")
      .hasFile("README.md")
      .containing("[Package types](documentation/package-types.md)")
      .containing("[Assertions](documentation/assertions.md)");
  }
}
