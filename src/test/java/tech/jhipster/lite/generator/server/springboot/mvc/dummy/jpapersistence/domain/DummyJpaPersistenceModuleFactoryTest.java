package tech.jhipster.lite.generator.server.springboot.mvc.dummy.jpapersistence.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class DummyJpaPersistenceModuleFactoryTest {

  private static final DummyJpaPersistenceModuleFactory factory = new DummyJpaPersistenceModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, beersApplicationService(), dummyInMemoryRepository(), inMemoryBeersReseter())
      .hasPrefixedFiles(
        "src/main/java/com/jhipster/test/dummy/infrastructure/secondary",
        "BeerEntity.java",
        "JpaBeersRepository.java",
        "SpringDataBeersRepository.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/jhipster/test/dummy/infrastructure/secondary",
        "BeerEntityTest.java",
        "JpaBeersRepositoryIntTest.java"
      )
      .hasFile("src/main/java/com/jhipster/test/dummy/application/BeersApplicationService.java")
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
        "src/main/java/com/jhipster/test/dummy/infrastructure/secondary/InMemoryBeersRepository.java",
        "src/test/java/com/jhipster/test/dummy/infrastructure/secondary/InMemoryBeersReseter.java"
      );
  }

  private ModuleFile beersApplicationService() {
    return file(
      "src/test/resources/projects/dummy-feature/BeersApplicationService.java",
      "src/main/java/com/jhipster/test/dummy/application/BeersApplicationService.java"
    );
  }

  private ModuleFile dummyInMemoryRepository() {
    return file(
      "src/test/resources/projects/dummy-feature/InMemoryBeersRepository.java",
      "src/main/java/com/jhipster/test/dummy/infrastructure/secondary/InMemoryBeersRepository.java"
    );
  }

  private ModuleFile inMemoryBeersReseter() {
    return file(
      "src/test/resources/projects/dummy-feature/InMemoryBeersReseter.java",
      "src/test/java/com/jhipster/test/dummy/infrastructure/secondary/InMemoryBeersReseter.java"
    );
  }
}
