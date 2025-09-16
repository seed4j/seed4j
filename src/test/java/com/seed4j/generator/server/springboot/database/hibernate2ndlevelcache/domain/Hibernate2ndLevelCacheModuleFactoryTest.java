package com.seed4j.generator.server.springboot.database.hibernate2ndlevelcache.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class Hibernate2ndLevelCacheModuleFactoryTest {

  private static final Hibernate2ndLevelCacheModuleFactory factory = new Hibernate2ndLevelCacheModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModule module = factory.buildModule(properties());

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.hibernate.orm</groupId>
              <artifactId>hibernate-jcache</artifactId>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          jpa:
            properties:
              hibernate:
                cache:
                  use_second_level_cache: true
        """
      )
      .and()
      .hasFile("src/test/java/com/seed4j/growth/wire/cache/infrastructure/secondary/Hibernate2ndLevelCacheConfigurationIT.java");
  }

  private Seed4JModuleProperties properties() {
    return Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).basePackage("com.seed4j.growth").build();
  }
}
