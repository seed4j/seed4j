package com.seed4j.generator.server.springboot.mvc.sample.feature.domain;

import static com.seed4j.generator.server.sample.common.domain.BeersDomainShortcuts.beersDomain;
import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SampleFeatureModuleFactory {

  private static final String SAMPLE = "sample";

  private static final Seed4JSource SOURCE = from("server/springboot/mvc/sample/feature");

  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");
  private static final Seed4JSource SAMPLE_TEST_SOURCE = TEST_SOURCE.append(SAMPLE);

  private static final String APPLICATION = "application";
  private static final String PRIMARY = "infrastructure/primary";
  private static final String SECONDARY = "infrastructure/secondary";

  private static final String BEER = "beer";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.basePackage().path();
    Seed4JDestination mainDestination = toSrcMainJava().append(packagePath).append(SAMPLE);
    Seed4JDestination testDestination = toSrcTestJava().append(packagePath).append(SAMPLE);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("baseName", properties.projectBaseName().capitalized())
        .and()
      .documentation(documentationTitle("Sample"), SOURCE.file("sample.md"))
      .with(beersDomain(properties))
      .files()
      .nullMarkedJavaPackage(mainDestination.append(APPLICATION))
      .nullMarkedJavaPackage(mainDestination.append(PRIMARY).append(BEER))
      .nullMarkedJavaPackage(mainDestination.append(SECONDARY))
        .batch(MAIN_SOURCE.append(APPLICATION), mainDestination.append(APPLICATION))
          .addTemplate("BeersApplicationService.java")
          .addTemplate("BeerIdAccessChecker.java")
          .addTemplate("BeerResource.java")
          .addTemplate("BeersAccessesConfiguration.java")
          .addTemplate("BeerToCreateAccessChecker.java")
          .and()
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
        .batch(SAMPLE_TEST_SOURCE.append(APPLICATION), testDestination.append(APPLICATION))
          .addTemplate("BeerIdAccessCheckerTest.java")
          .addTemplate("BeerToCreateAccessCheckerTest.java")
          .and()
        .batch(SAMPLE_TEST_SOURCE.append(PRIMARY).append(BEER), testDestination.append(PRIMARY).append(BEER))
          .addTemplate("BeersSteps.java")
          .addTemplate("RestBeersTest.java")
          .addTemplate("RestBeerTest.java")
          .addTemplate("RestBeerToCreateTest.java")
          .and()
        .add(SAMPLE_TEST_SOURCE.append(SECONDARY).template("InMemoryBeersResetter.java"), testDestination.append(SECONDARY).append("InMemoryBeersResetter.java"))
        .add(TEST_SOURCE.file("beers-catalog.feature"), to("src/test/features/beers-catalog.feature"))
        .add(TEST_SOURCE.template("HttpSteps.java"), toSrcTestJava().append(packagePath).append("HttpSteps.java"))
        .and()
      .build();
    // @formatter:on
  }
}
