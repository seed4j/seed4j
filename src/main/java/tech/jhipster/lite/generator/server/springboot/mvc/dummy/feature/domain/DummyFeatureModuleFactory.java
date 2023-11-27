package tech.jhipster.lite.generator.server.springboot.mvc.dummy.feature.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class DummyFeatureModuleFactory {

  private static final String DUMMY = "dummy";

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/dummy/feature");

  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");
  private static final JHipsterSource DUMMY_TEST_SOURCE = TEST_SOURCE.append(DUMMY);

  private static final String APPLICATION = "application";
  private static final String DOMAIN = "domain";
  private static final String PRIMARY = "infrastructure/primary";
  private static final String SECONDARY = "infrastructure/secondary";

  private static final String BEER = "beer";
  private static final String ORDER = "order";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.basePackage().path();
    JHipsterDestination mainDestination = toSrcMainJava().append(packagePath).append(DUMMY);
    JHipsterDestination testDestination = toSrcTestJava().append(packagePath).append(DUMMY);

    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .put("baseName", properties.projectBaseName().capitalized())
        .and()
      .documentation(documentationTitle("Dummy"), SOURCE.file("dummy.md"))
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
        .batch(DUMMY_TEST_SOURCE.append(APPLICATION), testDestination.append(APPLICATION))
          .addTemplate("BeerIdAccessCheckerTest.java")
          .addTemplate("BeerToCreateAccessCheckerTest.java")
          .and()
        .batch(DUMMY_TEST_SOURCE.append(DOMAIN), testDestination.append(DOMAIN))
          .addTemplate("AmountTest.java")
          .addTemplate("BeersIdentityFixture.java")
          .and()
        .batch(DUMMY_TEST_SOURCE.append(DOMAIN).append(BEER), testDestination.append(DOMAIN).append(BEER))
          .addTemplate("BeersFixture.java")
          .addTemplate("BeersRemoverTest.java")
          .addTemplate("BeersTest.java")
          .and()
        .batch(DUMMY_TEST_SOURCE.append(DOMAIN).append(ORDER), testDestination.append(DOMAIN).append(ORDER))
          .addTemplate("BeerOrderFixture.java")
          .addTemplate("BeerOrderTest.java")
          .and()
        .batch(DUMMY_TEST_SOURCE.append(PRIMARY).append(BEER), testDestination.append(PRIMARY).append(BEER))
          .addTemplate("BeersSteps.java")
          .addTemplate("RestBeersTest.java")
          .addTemplate("RestBeerTest.java")
          .addTemplate("RestBeerToCreateTest.java")
          .and()
        .add(DUMMY_TEST_SOURCE.append(SECONDARY).template("InMemoryBeersResetter.java"), testDestination.append(SECONDARY).append("InMemoryBeersResetter.java"))
        .add(TEST_SOURCE.file("beers-catalog.feature"), to("src/test/features/beers-catalog.feature"))
        .add(TEST_SOURCE.template("HttpSteps.java"), toSrcTestJava().append(packagePath).append("HttpSteps.java"))
        .and()
      .build();
    //@formatter:on
  }
}
