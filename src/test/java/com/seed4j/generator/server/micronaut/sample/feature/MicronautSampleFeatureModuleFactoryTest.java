package com.seed4j.generator.server.micronaut.sample.feature;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class MicronautSampleFeatureModuleFactoryTest {

  private static final MicronautSampleFeatureModuleFactory factory = new MicronautSampleFeatureModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing("<artifactId>micronaut-validation</artifactId>")
      .containing("<artifactId>micronaut-validation-processor</artifactId>")
      .and()
      .hasFile("src/main/java/com/seed4j/growth/sample/package-info.java")
      .and()
      .hasPrefixedFiles("src/main/java/com/seed4j/growth/sample/domain", "Amount.java", "BeerId.java")
      .hasPrefixedFiles("src/main/java/com/seed4j/growth/sample/domain/beer", "Beer.java", "Beers.java", "BeersRepository.java")
      .hasFile("src/main/java/com/seed4j/growth/sample/application/BeersApplicationService.java")
      .containing("@Singleton")
      .notContaining("PreAuthorize")
      .and()
      .hasFile("src/main/java/com/seed4j/growth/sample/infrastructure/primary/beer/BeersResource.java")
      .containing("@Controller(\"/api/beers\")")
      .and()
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/sample/infrastructure/primary/beer",
        "RestBeer.java",
        "RestBeers.java",
        "RestBeerToCreate.java"
      )
      .hasFile("src/main/java/com/seed4j/growth/sample/infrastructure/secondary/InMemoryBeersRepository.java")
      .and()
      .hasPrefixedFiles("src/test/java/com/seed4j/growth/sample/domain/beer", "BeersTest.java", "BeersRemoverTest.java")
      .hasFile("src/test/java/com/seed4j/growth/sample/infrastructure/primary/beer/BeersResourceTest.java")
      .containing(".bearerAuth(")
      .and()
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/sample/infrastructure/primary/beer",
        "BeersResourceTest.java",
        "RestBeerTest.java",
        "RestBeersTest.java",
        "RestBeerToCreateTest.java"
      );
  }
}
