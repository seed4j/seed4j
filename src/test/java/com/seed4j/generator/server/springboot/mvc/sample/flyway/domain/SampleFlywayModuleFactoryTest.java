package com.seed4j.generator.server.springboot.mvc.sample.flyway.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.assertThatModule;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SampleFlywayModuleFactoryTest {

  private static final SampleFlywayModuleFactory factory = new SampleFlywayModuleFactory();

  @Test
  void shouldBuildModuleForPostGreSQL() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .put("date", "2021-12-03T10:15:30.00Z")
      .build();

    SeedModule module = factory.buildPostgreSQLModule(properties);

    assertThatModule(module)
      .hasFile("src/main/resources/db/migration/V20211203101531__sample_feature_schema.sql")
      .containing("  id            UUID NOT NULL PRIMARY KEY,");
  }

  @Test
  void shouldBuildModuleForNotPostGreSQL() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .put("date", "2021-12-03T10:15:30.00Z")
      .build();

    SeedModule module = factory.buildNotPostgreSQLModule(properties);

    assertThatModule(module)
      .hasFile("src/main/resources/db/migration/V20211203101531__sample_feature_schema.sql")
      .containing("  id            BINARY(16) NOT NULL PRIMARY KEY,");
  }
}
