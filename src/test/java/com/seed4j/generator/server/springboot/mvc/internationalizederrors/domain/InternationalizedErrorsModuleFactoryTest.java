package com.seed4j.generator.server.springboot.mvc.internationalizederrors.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class InternationalizedErrorsModuleFactoryTest {

  private static final InternationalizedErrorsModuleFactory factory = new InternationalizedErrorsModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("jhipster")
      .build();

    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.reflections</groupId>
              <artifactId>reflections</artifactId>
              <version>${reflections.version}</version>
              <scope>test</scope>
            </dependency>
        """
      )
      .and()
      .hasFiles("documentation/application-errors.md")
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/shared/error/domain",
        "ErrorStatus.java",
        "ErrorKey.java",
        "StandardErrorKey.java",
        "JhipsterException.java"
      )
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/shared/error/infrastructure/primary",
        "ArgumentsReplacer.java",
        "AssertionErrorsConfiguration.java",
        "AssertionErrorsHandler.java",
        "JhipsterErrorsConfiguration.java",
        "JhipsterErrorsHandler.java"
      )
      .hasPrefixedFiles(
        "src/main/resources/messages/assertions-errors",
        "assertion-errors-messages.properties",
        "assertion-errors-messages_fr.properties"
      )
      .hasPrefixedFiles(
        "src/main/resources/messages/errors",
        "jhipster-errors-messages.properties",
        "jhipster-errors-messages_fr.properties"
      )
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/shared/error/infrastructure/primary",
        "JhipsterErrorsHandlerIT.java",
        "JhipsterErrorsMessagesTest.java",
        "JhipsterExceptionFactory.java",
        "ArgumentsReplacerTest.java",
        "AssertionErrorMessagesTest.java",
        "AssertionErrorsHandlerIT.java",
        "AssertionErrorsHandlerTest.java"
      )
      .hasFile("src/test/java/com/seed4j/growth/shared/error/infrastructure/primary/JhipsterErrorsHandlerTest.java")
      .containing("handler.handleJhipsterException(JhipsterException.internalServerError")
      .and()
      .hasPrefixedFiles("src/test/java/com/seed4j/growth/shared/error/domain", "JhipsterExceptionTest.java", "ErrorKeyTest.java")
      .hasFiles("src/test/java/com/seed4j/growth/shared/error_generator/domain/NullElementInCollectionExceptionFactory.java")
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/shared/error_generator/infrastructure/primary",
        "AssertionsErrorsResource.java",
        "JhipsterErrorsResource.java"
      );
  }
}
