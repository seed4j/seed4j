package tech.jhipster.lite.generator.server.javatool.base.domain;

import static tech.jhipster.lite.generator.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.JHipsterDestination;
import tech.jhipster.lite.generator.module.domain.JHipsterModule;
import tech.jhipster.lite.generator.module.domain.JHipsterSource;

public class JavaBaseModuleFactory {

  private static final String SOURCE_FOLDER = "server/javatool/base";
  private static final String ERROR_DOMAIN = "error/domain";

  public JHipsterModule buildModule(JavaBaseModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.basePackage().path();
    String baseClassName = properties.projectBaseName().capitalized();

    JHipsterDestination testDestination = toSrcTestJava().append(packagePath);
    JHipsterDestination mainDestination = toSrcMainJava().append(packagePath);

    //@formatter:off
    return moduleForProject(properties.project())
      .context()
        .packageName(properties.basePackage())
        .put("collectionClass", baseClassName)
        .and()
      .files()
        .batch(source(), mainDestination.append(ERROR_DOMAIN))
          .add("Assert.java")
          .add("MissingMandatoryValueException.java")
          .add("AssertionException.java")
          .add("NotAfterTimeException.java")
          .add("NotBeforeTimeException.java")
          .add("NullElementInCollectionException.java")
          .add("NumberValueTooHighException.java")
          .add("NumberValueTooLowException.java")
          .add("StringTooLongException.java")
          .add("StringTooShortException.java")
          .add("TooManyElementsException.java")
          .and()
        .batch(source(), testDestination.append(ERROR_DOMAIN))
          .add("AssertTest.java")
          .add("MissingMandatoryValueExceptionTest.java")
          .and()
        .batch(source(), testDestination)
          .add("UnitTest.java")
          .add("ReplaceCamelCase.java")
          .and()
        .add(source().template("ProjectCollections.java"), collectionsDestination(baseClassName, mainDestination))
        .add(source().template("ProjectCollectionsTest.java"), collectionsTestDestination(baseClassName, testDestination))
        .and()
      .build();
    //@formatter:on
  }

  private JHipsterDestination collectionsDestination(String className, JHipsterDestination mainDestination) {
    return mainDestination.append("common/domain").append(className + "Collections.java");
  }

  private JHipsterDestination collectionsTestDestination(String className, JHipsterDestination testDestination) {
    return testDestination.append("common/domain").append(className + "CollectionsTest.java");
  }

  private JHipsterSource source() {
    return from(SOURCE_FOLDER);
  }
}
