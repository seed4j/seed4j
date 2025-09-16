package com.seed4j.generator.server.springboot.mvc.internationalizederrors.domain;

import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.javaDependency;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class InternationalizedErrorsModuleFactory {

  private static final String ERROR = "shared/error";
  private static final String DOMAIN = "domain";
  private static final String INFRASTRUCTURE_PRIMARY = "infrastructure/primary";

  private static final Seed4JSource SOURCE = from("server/springboot/mvc/internationalized-errors");
  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final Seed4JSource RESOURCES_SOURCE = SOURCE.append("resources");
  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");

  private static final Seed4JDestination MESSAGES_DESTINATION = to("src/main/resources/messages");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String baseName = properties.projectBaseName().capitalized();
    String baseFileName = properties.projectBaseName().kebabCase();
    String packagePath = properties.packagePath();

    Seed4JDestination mainErrorDestination = toSrcMainJava().append(packagePath).append(ERROR);
    Seed4JDestination mainDomainDestination = mainErrorDestination.append(DOMAIN);
    Seed4JDestination mainPrimaryDestination = mainErrorDestination.append(INFRASTRUCTURE_PRIMARY);

    Seed4JDestination testErrorDestination = toSrcTestJava().append(packagePath).append(ERROR);
    Seed4JDestination testPrimaryDestination = testErrorDestination.append(INFRASTRUCTURE_PRIMARY);
    Seed4JDestination testDomainDestination = testErrorDestination.append(DOMAIN);

    Seed4JDestination errorGeneratorDestination = toSrcTestJava().append(packagePath).append("shared/error_generator");
    Seed4JDestination errorGeneratorPrimaryDestination = errorGeneratorDestination.append(INFRASTRUCTURE_PRIMARY);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("baseName", baseName)
        .put("baseFileName", baseFileName)
        .and()
      .javaDependencies()
        .addDependency(reflectionsDependency())
        .and()
      .documentation(documentationTitle("Application errors"), SOURCE.template("documentation/application-errors.md.mustache"))
      .files()
        .add(MAIN_SOURCE.template("ApplicationException.java"), mainDomainDestination.append(baseName + "Exception.java"))
        .batch(MAIN_SOURCE, mainDomainDestination)
          .addTemplate("ErrorKey.java")
          .addTemplate("ErrorStatus.java")
          .addTemplate("StandardErrorKey.java")
          .and()
        .batch(MAIN_SOURCE, mainPrimaryDestination)
          .addTemplate("ArgumentsReplacer.java")
          .addTemplate("AssertionErrorsConfiguration.java")
          .addTemplate("AssertionErrorsHandler.java")
          .and()
        .add(
          MAIN_SOURCE.template("ApplicationErrorsConfiguration.java"),
          mainPrimaryDestination.append(baseName + "ErrorsConfiguration.java")
        )
        .add(MAIN_SOURCE.template("ApplicationErrorsHandler.java"), mainPrimaryDestination.append(baseName + "ErrorsHandler.java"))
        .batch(RESOURCES_SOURCE.append("assertions-errors"), MESSAGES_DESTINATION.append("assertions-errors"))
          .addFile("assertion-errors-messages.properties")
          .addFile("assertion-errors-messages_fr.properties")
          .and()
        .add(
          RESOURCES_SOURCE.append("errors/application-errors-messages.properties"),
          MESSAGES_DESTINATION.append("errors").append(baseFileName + "-errors-messages.properties")
        )
        .add(
          RESOURCES_SOURCE.append("errors/application-errors-messages_fr.properties"),
          MESSAGES_DESTINATION.append("errors").append(baseFileName + "-errors-messages_fr.properties")
        )
        .add(
          TEST_SOURCE.template("ApplicationErrorsHandlerIT.java"),
          testPrimaryDestination.append(baseName + "ErrorsHandlerIT.java")
        )
        .add(TEST_SOURCE.template("ApplicationErrorsHandlerTest.java"), testPrimaryDestination.append(baseName + "ErrorsHandlerTest.java"))
        .add(TEST_SOURCE.template("ApplicationErrorsMessagesTest.java"), testPrimaryDestination.append(baseName + "ErrorsMessagesTest.java"))
        .add(TEST_SOURCE.template("ApplicationExceptionFactory.java"), testPrimaryDestination.append(baseName + "ExceptionFactory.java"))
        .batch(TEST_SOURCE, testPrimaryDestination)
          .addTemplate("ArgumentsReplacerTest.java")
          .addTemplate("AssertionErrorMessagesTest.java")
          .addTemplate("AssertionErrorsHandlerIT.java")
          .addTemplate("AssertionErrorsHandlerTest.java")
          .and()
        .add(TEST_SOURCE.template("ApplicationExceptionTest.java"), testDomainDestination.append(baseName + "ExceptionTest.java"))
        .add(TEST_SOURCE.template("ErrorKeyTest.java"), testDomainDestination.append("ErrorKeyTest.java"))
        .add(
          TEST_SOURCE.template("NullElementInCollectionExceptionFactory.java"),
          errorGeneratorDestination.append(DOMAIN).append("NullElementInCollectionExceptionFactory.java")
        )
        .add(TEST_SOURCE.template("AssertionsErrorsResource.java"), errorGeneratorPrimaryDestination.append("AssertionsErrorsResource.java"))
        .add(
          TEST_SOURCE.template("ApplicationErrorsResource.java"),
          errorGeneratorPrimaryDestination.append(baseName + "ErrorsResource.java")
        )
        .and()
      .build();
    // @formatter:on
  }

  private JavaDependency reflectionsDependency() {
    return javaDependency()
      .groupId("org.reflections")
      .artifactId("reflections")
      .scope(JavaDependencyScope.TEST)
      .versionSlug("reflections")
      .build();
  }
}
