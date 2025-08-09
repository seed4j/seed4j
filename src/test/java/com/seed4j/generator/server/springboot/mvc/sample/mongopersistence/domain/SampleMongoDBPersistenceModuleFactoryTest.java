package com.seed4j.generator.server.springboot.mvc.sample.mongopersistence.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SampleMongoDBPersistenceModuleFactoryTest {

  private static final SampleMongoDBPersistenceModuleFactory factory = new SampleMongoDBPersistenceModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, sampleInMemoryRepository(), inMemoryBeersResetter())
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/sample/infrastructure/secondary",
        "BeerDocument.java",
        "MongoDBBeersRepository.java",
        "SpringDataBeersRepository.java",
        "BeersCollectionChangeUnit.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/sample/infrastructure/secondary",
        "BeerDocumentTest.java",
        "MongoDBBeersRepositoryIT.java",
        "MongoDBBeersResetter.java"
      )
      .doNotHaveFiles(
        "src/main/java/com/seed4j/growth/sample/infrastructure/secondary/InMemoryBeersRepository.java",
        "src/test/java/com/seed4j/growth/sample/infrastructure/secondary/InMemoryBeersResetter.java"
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
