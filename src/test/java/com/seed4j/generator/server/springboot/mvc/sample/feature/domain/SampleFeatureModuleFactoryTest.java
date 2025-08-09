package com.seed4j.generator.server.springboot.mvc.sample.feature.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SampleFeatureModuleFactoryTest {

  private static final SampleFeatureModuleFactory factory = new SampleFeatureModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .and()
      .hasFiles("documentation/sample.md")
      .hasPrefixedFiles("src/main/java/com/seed4j/growth/sample", "package-info.java")
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/sample/application",
        "BeersApplicationService.java",
        "BeerIdAccessChecker.java",
        "BeerResource.java",
        "BeersAccessesConfiguration.java",
        "BeerToCreateAccessChecker.java"
      )
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/sample/domain/beer",
        "Beer.java",
        "BeerName.java",
        "Beers.java",
        "BeersCreator.java",
        "BeerSellingState.java",
        "BeersRemover.java",
        "BeersRepository.java",
        "BeerToCreate.java",
        "UnknownBeerException.java"
      )
      .hasPrefixedFiles("src/main/java/com/seed4j/growth/sample/domain/order", "BeerOrder.java", "BeerOrderLine.java", "OrderedBeer.java")
      .hasPrefixedFiles("src/main/java/com/seed4j/growth/sample/domain", "Amount.java", "BeerId.java")
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/sample/infrastructure/primary/beer",
        "BeersResource.java",
        "RestBeer.java",
        "RestBeers.java",
        "RestBeerToCreate.java"
      )
      .hasFiles("src/main/java/com/seed4j/growth/sample/infrastructure/secondary/InMemoryBeersRepository.java")
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/sample/application",
        "BeerIdAccessCheckerTest.java",
        "BeerToCreateAccessCheckerTest.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/sample/domain/beer",
        "BeersFixture.java",
        "BeersRemoverTest.java",
        "BeersTest.java"
      )
      .hasPrefixedFiles("src/test/java/com/seed4j/growth/sample/domain/order", "BeerOrderFixture.java", "BeerOrderTest.java")
      .hasPrefixedFiles("src/test/java/com/seed4j/growth/sample/domain", "AmountTest.java", "BeersIdentityFixture.java")
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/sample/infrastructure/primary/beer",
        "BeersSteps.java",
        "RestBeersTest.java",
        "RestBeerTest.java",
        "RestBeerToCreateTest.java"
      )
      .hasFiles("src/test/java/com/seed4j/growth/sample/infrastructure/secondary/InMemoryBeersResetter.java")
      .hasFiles("src/test/java/com/seed4j/growth/HttpSteps.java")
      .hasFiles("src/test/features/beers-catalog.feature");
  }
}
