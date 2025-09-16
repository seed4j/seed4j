package com.seed4j.generator.server.springboot.mvc.internationalizederrors.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class InternationalizedErrorsModuleFactoryTest {

  private static final InternationalizedErrorsModuleFactory factory = new InternationalizedErrorsModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("seed4j")
      .build();

    Seed4JModule module = factory.buildModule(properties);

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
        "Seed4jException.java"
      )
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/shared/error/infrastructure/primary",
        "ArgumentsReplacer.java",
        "AssertionErrorsConfiguration.java",
        "AssertionErrorsHandler.java",
        "Seed4jErrorsConfiguration.java",
        "Seed4jErrorsHandler.java"
      )
      .hasPrefixedFiles(
        "src/main/resources/messages/assertions-errors",
        "assertion-errors-messages.properties",
        "assertion-errors-messages_fr.properties"
      )
      .hasPrefixedFiles("src/main/resources/messages/errors", "seed4j-errors-messages.properties", "seed4j-errors-messages_fr.properties")
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/shared/error/infrastructure/primary",
        "Seed4jErrorsHandlerIT.java",
        "Seed4jErrorsMessagesTest.java",
        "Seed4jExceptionFactory.java",
        "ArgumentsReplacerTest.java",
        "AssertionErrorMessagesTest.java",
        "AssertionErrorsHandlerIT.java",
        "AssertionErrorsHandlerTest.java"
      )
      .hasFile("src/test/java/com/seed4j/growth/shared/error/infrastructure/primary/Seed4jErrorsHandlerTest.java")
      .containing("handler.handleSeed4jException(Seed4jException.internalServerError")
      .and()
      .hasPrefixedFiles("src/test/java/com/seed4j/growth/shared/error/domain", "Seed4jExceptionTest.java", "ErrorKeyTest.java")
      .hasFiles("src/test/java/com/seed4j/growth/shared/error_generator/domain/NullElementInCollectionExceptionFactory.java")
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/shared/error_generator/infrastructure/primary",
        "AssertionsErrorsResource.java",
        "Seed4jErrorsResource.java"
      );
  }
}
