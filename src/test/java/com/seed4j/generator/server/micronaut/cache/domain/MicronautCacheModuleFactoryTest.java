package com.seed4j.generator.server.micronaut.cache.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class MicronautCacheModuleFactoryTest {

  private final MicronautCacheModuleFactory factory = new MicronautCacheModuleFactory();

  @Test
  void shouldBuildCaffeineModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildCaffeineModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
              <groupId>io.micronaut.cache</groupId>
              <artifactId>micronaut-cache-caffeine</artifactId>\
        """
      )
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing("micronaut:")
      .containing("caches:")
      .containing("maximum-size: 100");
  }

  @Test
  void shouldBuildRedisModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildRedisModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
              <groupId>io.micronaut.cache</groupId>
              <artifactId>micronaut-cache-redis</artifactId>\
        """
      )
      .containing(
        """
              <groupId>io.micronaut.redis</groupId>
              <artifactId>micronaut-redis-lettuce</artifactId>\
        """
      )
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing("redis:")
      .containing("uri: redis://localhost:6379");
  }
}
