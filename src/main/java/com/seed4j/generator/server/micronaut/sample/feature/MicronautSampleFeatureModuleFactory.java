package com.seed4j.generator.server.micronaut.sample.feature;

import static com.seed4j.generator.server.sample.common.domain.BeersDomainShortcuts.beersDomain;
import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.javaAnnotationProcessor;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

class MicronautSampleFeatureModuleFactory {

  private static final Seed4JSource SOURCE = from("server/micronaut/sample/feature");
  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");

  private static final GroupId MICRONAUT_VALIDATION_GROUP = groupId("io.micronaut.validation");

  private static final String SAMPLE = "sample";
  private static final String APPLICATION = "application";
  private static final String PRIMARY = "infrastructure/primary";
  private static final String SECONDARY = "infrastructure/secondary";
  private static final String BEER = "beer";

  Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.basePackage().path();
    Seed4JDestination mainDestination = toSrcMainJava().append(packagePath).append(SAMPLE);
    Seed4JDestination testDestination = toSrcTestJava().append(packagePath).append(SAMPLE);

    // @formatter:off
    return moduleBuilder(properties)
      .with(beersDomain(properties))
      .javaDependencies()
        .addDependency(MICRONAUT_VALIDATION_GROUP, artifactId("micronaut-validation"))
        .and()
      .javaCompiler()
        .addAnnotationProcessor(javaAnnotationProcessor().groupId(MICRONAUT_VALIDATION_GROUP).artifactId("micronaut-validation-processor").build())
        .and()
      .files()
        .nullMarkedJavaPackage(mainDestination.append(APPLICATION))
        .nullMarkedJavaPackage(mainDestination.append(PRIMARY).append(BEER))
        .nullMarkedJavaPackage(mainDestination.append(SECONDARY))
        .add(
          MAIN_SOURCE.append(APPLICATION).template("BeersApplicationService.java"),
          mainDestination.append(APPLICATION).append("BeersApplicationService.java")
        )
        .batch(MAIN_SOURCE.append(PRIMARY).append(BEER), mainDestination.append(PRIMARY).append(BEER))
          .addTemplate("BeersResource.java")
          .addTemplate("RestBeer.java")
          .addTemplate("RestBeers.java")
          .addTemplate("RestBeerToCreate.java")
          .and()
        .add(
          MAIN_SOURCE.append(SECONDARY).template("InMemoryBeersRepository.java"),
          mainDestination.append(SECONDARY).append("InMemoryBeersRepository.java")
        )
        .batch(TEST_SOURCE.append(PRIMARY).append(BEER), testDestination.append(PRIMARY).append(BEER))
          .addTemplate("BeersResourceTest.java")
          .addTemplate("RestBeerTest.java")
          .addTemplate("RestBeersTest.java")
          .addTemplate("RestBeerToCreateTest.java")
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
