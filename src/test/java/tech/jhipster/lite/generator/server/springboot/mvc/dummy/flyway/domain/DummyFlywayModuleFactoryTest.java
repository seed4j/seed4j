package tech.jhipster.lite.generator.server.springboot.mvc.dummy.flyway.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class DummyFlywayModuleFactoryTest {

  private static final DummyFlywayModuleFactory factory = new DummyFlywayModuleFactory();

  @Test
  void shouldBuildModuleForPostGreSQL() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .put("date", "2021-12-03T10:15:30.00Z")
      .build();

    JHipsterModule module = factory.buildPostgresqlModule(properties);

    assertThatModule(module)
      .hasFile("src/main/resources/db/migration/V20211203101531__dummy_feature_schema.sql")
      .containing("  id            UUID NOT NULL PRIMARY KEY,");
  }

  @Test
  void shouldBuildModuleForNotPostGreSQL() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .put("date", "2021-12-03T10:15:30.00Z")
      .build();

    JHipsterModule module = factory.buildNotPostgresqlModule(properties);

    assertThatModule(module)
      .hasFile("src/main/resources/db/migration/V20211203101531__dummy_feature_schema.sql")
      .containing("  id            BINARY(16) NOT NULL PRIMARY KEY,");
  }
}
