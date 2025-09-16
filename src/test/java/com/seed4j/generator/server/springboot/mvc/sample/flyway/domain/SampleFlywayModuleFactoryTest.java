package com.seed4j.generator.server.springboot.mvc.sample.flyway.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModule;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SampleFlywayModuleFactoryTest {

  private static final SampleFlywayModuleFactory factory = new SampleFlywayModuleFactory();

  @Test
  void shouldBuildModuleForPostGreSQL() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .put("date", "2021-12-03T10:15:30.00Z")
      .build();

    Seed4JModule module = factory.buildPostgreSQLModule(properties);

    assertThatModule(module)
      .hasFile("src/main/resources/db/migration/V20211203101531__sample_feature_schema.sql")
      .containing("  id            UUID NOT NULL PRIMARY KEY,");
  }

  @Test
  void shouldBuildModuleForNotPostGreSQL() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .put("date", "2021-12-03T10:15:30.00Z")
      .build();

    Seed4JModule module = factory.buildNotPostgreSQLModule(properties);

    assertThatModule(module)
      .hasFile("src/main/resources/db/migration/V20211203101531__sample_feature_schema.sql")
      .containing("  id            BINARY(16) NOT NULL PRIMARY KEY,");
  }
}
