package com.seed4j.generator.server.springboot.cache.caffeine.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class CaffeineCacheModuleFactoryTest {

  private static final CaffeineCacheModuleFactory factory = new CaffeineCacheModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>com.github.ben-manes.caffeine</groupId>
              <artifactId>caffeine</artifactId>
            </dependency>
        """
      )
      .and()
      .hasFiles("documentation/caffeine.md");
  }
}
