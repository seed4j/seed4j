package com.seed4j.generator.server.springboot.mvc.sample.feature.domain;

import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.to;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SampleFeatureModuleFactory {

  private static final String SAMPLE = "sample";

  private static final SeedSource SOURCE = from("server/springboot/mvc/sample/feature");

  private static final SeedSource MAIN_SOURCE = SOURCE.append("main");
  private static final SeedSource TEST_SOURCE = SOURCE.append("test");
  private static final SeedSource SAMPLE_TEST_SOURCE = TEST_SOURCE.append(SAMPLE);

  private static final String APPLICATION = "application";
  private static final String DOMAIN = "domain";
  private static final String PRIMARY = "infrastructure/primary";
  private static final String SECONDARY = "infrastructure/secondary";

  private static final String BEER = "beer";
  private static final String ORDER = "order";

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.basePackage().path();
    SeedDestination mainDestination = toSrcMainJava().append(packagePath).append(SAMPLE);
    SeedDestination testDestination = toSrcTestJava().append(packagePath).append(SAMPLE);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("baseName", properties.projectBaseName().capitalized())
        .and()
      .documentation(documentationTitle("Sample"), SOURCE.file("sample.md"))
      .files()
        .batch(MAIN_SOURCE, mainDestination)
          .addTemplate("package-info.java")
          .and()
        .batch(MAIN_SOURCE.append(APPLICATION), mainDestination.append(APPLICATION))
          .addTemplate("BeersApplicationService.java")
          .addTemplate("BeerIdAccessChecker.java")
          .addTemplate("BeerResource.java")
          .addTemplate("BeersAccessesConfiguration.java")
          .addTemplate("BeerToCreateAccessChecker.java")
          .and()
        .batch(MAIN_SOURCE.append(DOMAIN).append(BEER), mainDestination.append(DOMAIN).append(BEER))
          .addTemplate("Beer.java")
          .addTemplate("BeerName.java")
          .addTemplate("Beers.java")
          .addTemplate("BeersCreator.java")
          .addTemplate("BeerSellingState.java")
          .addTemplate("BeersRemover.java")
          .addTemplate("BeersRepository.java")
          .addTemplate("BeerToCreate.java")
          .addTemplate("UnknownBeerException.java")
          .and()
        .batch(MAIN_SOURCE.append(DOMAIN).append(ORDER), mainDestination.append(DOMAIN).append(ORDER))
          .addTemplate("BeerOrder.java")
          .addTemplate("BeerOrderLine.java")
          .addTemplate("OrderedBeer.java")
          .and()
        .batch(MAIN_SOURCE.append(DOMAIN), mainDestination.append(DOMAIN))
          .addTemplate("Amount.java")
          .addTemplate("BeerId.java")
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
        .batch(SAMPLE_TEST_SOURCE.append(DOMAIN), testDestination.append(DOMAIN))
          .addTemplate("AmountTest.java")
          .addTemplate("BeersIdentityFixture.java")
          .and()
        .batch(SAMPLE_TEST_SOURCE.append(DOMAIN).append(BEER), testDestination.append(DOMAIN).append(BEER))
          .addTemplate("BeersFixture.java")
          .addTemplate("BeersRemoverTest.java")
          .addTemplate("BeersTest.java")
          .and()
        .batch(SAMPLE_TEST_SOURCE.append(DOMAIN).append(ORDER), testDestination.append(DOMAIN).append(ORDER))
          .addTemplate("BeerOrderFixture.java")
          .addTemplate("BeerOrderTest.java")
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
