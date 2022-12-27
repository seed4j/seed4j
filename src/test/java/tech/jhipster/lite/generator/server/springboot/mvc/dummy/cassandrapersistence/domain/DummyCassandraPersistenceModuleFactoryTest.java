package tech.jhipster.lite.generator.server.springboot.mvc.dummy.cassandrapersistence.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.file;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions;

@UnitTest
public class DummyCassandraPersistenceModuleFactoryTest {

  private static final String BASE_NAME = "jhipster";
  private static final DummyCassandraPersistenceModuleFactory factory = new DummyCassandraPersistenceModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName(BASE_NAME)
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, dummyInMemoryRepository(), inMemoryBeersReseter())
      .hasPrefixedFiles(
        "src/main/java/com/jhipster/test/dummy/infrastructure/secondary",
        "BeerCatalogTable.java",
        "BeerTable.java",
        "CassandraBeerCatalogRepository.java",
        "CassandraBeerRepository.java",
        "SpringDataBeersRepository.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/jhipster/test/dummy/infrastructure/secondary",
        "BeerCatalogTableTest.java",
        "BeerTableTest.java",
        "CassandraBeerCatalogRepositoryIntTest.java",
        "CassandraBeerRepositoryIntTest.java",
        "CassandraBeersResetter.java",
        "SpringDataRepositoryIntTest.java"
      )
      .hasPrefixedFiles("src/main/resources/config/cql/changelog", "00000000000000_create-keyspace.cql", "00000000000001_create-tables.cql")
      .hasFile("src/main/resources/config/application.properties")
      .containing("spring.cassandra.keyspace-name=" + BASE_NAME)
      .and()
      .doNotHaveFiles(
        "src/main/java/com/jhipster/test/dummy/infrastructure/secondary/InMemoryBeersRepository.java",
        "src/test/java/com/jhipster/test/dummy/infrastructure/secondary/InMemoryBeersReseter.java"
      );
  }

  private JHipsterModulesAssertions.ModuleFile dummyInMemoryRepository() {
    return file(
      "src/test/resources/projects/dummy-feature/InMemoryBeersRepository.java",
      "src/main/java/com/jhipster/test/dummy/infrastructure/secondary/InMemoryBeersRepository.java"
    );
  }

  private JHipsterModulesAssertions.ModuleFile inMemoryBeersReseter() {
    return file(
      "src/test/resources/projects/dummy-feature/InMemoryBeersReseter.java",
      "src/test/java/com/jhipster/test/dummy/infrastructure/secondary/InMemoryBeersReseter.java"
    );
  }
}
