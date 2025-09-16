package com.seed4j.generator.server.springboot.mvc.sample.jpapersistence.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SampleJpaPersistenceModuleFactoryTest {

  private static final SampleJpaPersistenceModuleFactory factory = new SampleJpaPersistenceModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, beersApplicationService(), sampleInMemoryRepository(), inMemoryBeersResetter())
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/sample/infrastructure/secondary",
        "BeerEntity.java",
        "JpaBeersRepository.java",
        "SpringDataBeersRepository.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/sample/infrastructure/secondary",
        "BeerEntityTest.java",
        "JpaBeersRepositoryIT.java"
      )
      .hasFile("src/main/java/com/seed4j/growth/sample/application/BeersApplicationService.java")
      .containing("import org.springframework.transaction.annotation.Transactional;")
      .containing(
        """
          @Transactional(readOnly = true)
          public Beers catalog() {
        """
      )
      .containing(
        """
          @Transactional
          @PreAuthorize("can('create', #beerToCreate)")
          public Beer create(BeerToCreate beerToCreate) {
        """
      )
      .containing(
        """
          @Transactional
          @PreAuthorize("can('remove', #beer)")
          public void remove(BeerId beer) {
        """
      )
      .and()
      .doNotHaveFiles(
        "src/main/java/com/seed4j/growth/sample/infrastructure/secondary/InMemoryBeersRepository.java",
        "src/test/java/com/seed4j/growth/sample/infrastructure/secondary/InMemoryBeersResetter.java"
      );
  }

  private ModuleFile beersApplicationService() {
    return file(
      "src/test/resources/projects/sample-feature/BeersApplicationService.java",
      "src/main/java/com/seed4j/growth/sample/application/BeersApplicationService.java"
    );
  }

  private ModuleFile sampleInMemoryRepository() {
    return file(
      "src/test/resources/projects/sample-feature/InMemoryBeersRepository.java",
      "src/main/java/com/seed4j/growth/sample/infrastructure/secondary/InMemoryBeersRepository.java"
    );
  }

  private ModuleFile inMemoryBeersResetter() {
    return file(
      "src/test/resources/projects/sample-feature/InMemoryBeersResetter.java",
      "src/test/java/com/seed4j/growth/sample/infrastructure/secondary/InMemoryBeersResetter.java"
    );
  }
}
