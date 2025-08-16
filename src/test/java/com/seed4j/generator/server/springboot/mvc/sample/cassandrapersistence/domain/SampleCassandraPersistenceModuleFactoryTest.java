package com.seed4j.generator.server.springboot.mvc.sample.cassandrapersistence.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SampleCassandraPersistenceModuleFactoryTest {

  private static final String BASE_NAME = "seed4j";
  private static final SampleCassandraPersistenceModuleFactory factory = new SampleCassandraPersistenceModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName(BASE_NAME)
      .build();

    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, sampleInMemoryRepository(), inMemoryBeersResetter())
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth/sample/infrastructure/secondary",
        "BeerCatalogTable.java",
        "BeerTable.java",
        "CassandraBeerCatalogRepository.java",
        "CassandraBeerRepository.java",
        "SpringDataBeersRepository.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/sample/infrastructure/secondary",
        "BeerCatalogTableTest.java",
        "BeerTableTest.java",
        "CassandraBeerCatalogRepositoryIT.java",
        "CassandraBeerRepositoryIT.java",
        "CassandraBeersResetter.java",
        "SpringDataRepositoryIT.java"
      )
      .hasPrefixedFiles("src/main/resources/config/cql/changelog", "00000000000000_create-keyspace.cql", "00000000000001_create-tables.cql")
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          cassandra:
            keyspace-name: seed4j
        """
      )
      .and()
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
