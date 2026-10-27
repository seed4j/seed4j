package com.seed4j.generator.server.sample.common.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;
import java.util.function.Consumer;

/**
 * Framework agnostic part of the Beers sample: its business context and its domain, with their tests.
 */
public final class BeersDomainShortcuts {

  private static final String SAMPLE = "sample";
  private static final String DOMAIN = "domain";
  private static final String BEER = "beer";
  private static final String ORDER = "order";

  private static final Seed4JSource SOURCE = from("server/sample/feature");
  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final Seed4JSource MAIN_DOMAIN_SOURCE = MAIN_SOURCE.append(DOMAIN);
  private static final Seed4JSource TEST_DOMAIN_SOURCE = SOURCE.append("test").append(DOMAIN);

  private BeersDomainShortcuts() {}

  public static Consumer<Seed4JModuleBuilder> beersDomain(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.basePackage().path();
    Seed4JDestination mainDestination = toSrcMainJava().append(packagePath).append(SAMPLE);
    Seed4JDestination mainDomainDestination = mainDestination.append(DOMAIN);
    Seed4JDestination testDomainDestination = toSrcTestJava().append(packagePath).append(SAMPLE).append(DOMAIN);

    // @formatter:off
    return builder -> builder
      .files()
        .batch(MAIN_SOURCE, mainDestination)
          .addTemplate("package-info.java")
          .and()
        .batch(MAIN_DOMAIN_SOURCE.append(BEER), mainDomainDestination.append(BEER))
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
        .batch(MAIN_DOMAIN_SOURCE.append(ORDER), mainDomainDestination.append(ORDER))
          .addTemplate("BeerOrder.java")
          .addTemplate("BeerOrderLine.java")
          .addTemplate("OrderedBeer.java")
          .and()
        .batch(MAIN_DOMAIN_SOURCE, mainDomainDestination)
          .addTemplate("Amount.java")
          .addTemplate("BeerId.java")
          .and()
          .nullMarkedJavaPackage(mainDomainDestination)
          .nullMarkedJavaPackage(mainDomainDestination.append(BEER))
          .nullMarkedJavaPackage(mainDomainDestination.append(ORDER))
        .batch(TEST_DOMAIN_SOURCE, testDomainDestination)
          .addTemplate("AmountTest.java")
          .addTemplate("BeersIdentityFixture.java")
          .and()
        .batch(TEST_DOMAIN_SOURCE.append(BEER), testDomainDestination.append(BEER))
          .addTemplate("BeersFixture.java")
          .addTemplate("BeersRemoverTest.java")
          .addTemplate("BeersTest.java")
          .and()
        .batch(TEST_DOMAIN_SOURCE.append(ORDER), testDomainDestination.append(ORDER))
          .addTemplate("BeerOrderFixture.java")
          .addTemplate("BeerOrderTest.java")
          .and()
        .and();
    // @formatter:on
  }
}
