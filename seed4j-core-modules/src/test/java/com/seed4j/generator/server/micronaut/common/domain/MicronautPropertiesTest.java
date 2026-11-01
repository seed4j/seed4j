package com.seed4j.generator.server.micronaut.common.domain;

import static com.seed4j.generator.server.micronaut.common.domain.MicronautProperties.MICRONAUT_MAIN;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautProperties.MICRONAUT_TEST;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModule;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import org.junit.jupiter.api.Test;

@UnitTest
class MicronautPropertiesTest {

  @Test
  void shouldWriteMainPropertiesAtRootOfMainResources() {
    Seed4JModule module = moduleBuilder(Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build())
      .applicationProperties(MICRONAUT_MAIN)
      .set(propertyKey("micronaut.server.port"), propertyValue(8080))
      .and()
      .applicationProperties(MICRONAUT_TEST)
      .set(propertyKey("micronaut.server.port"), propertyValue(-1))
      .and()
      .build();

    assertThatModule(module)
      .hasFile("src/main/resources/application.yml")
      .containing(
        """
        micronaut:
          server:
            port: 8080
        """
      )
      .and()
      .hasFile("src/test/resources/application-test.yml")
      .containing(
        """
        micronaut:
          server:
            port: -1
        """
      );
  }
}
