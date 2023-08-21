package tech.jhipster.lite.generator.server.springboot.mvc.dummy.liquibase.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class DummyLiquibaseModuleFactoryTest {

  private static final DummyLiquibaseModuleFactory factory = new DummyLiquibaseModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .put("date", "2021-12-03T10:15:30.00Z")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, masterChangelog())
      .hasFile("src/main/resources/config/liquibase/master.xml")
      .containing(
        "<include file=\"config/liquibase/changelog/20211203101530_dummy_feature_schema.xml\" relativeToChangelogFile=\"false\"/>"
      )
      .and()
      .hasFiles("src/main/resources/config/liquibase/changelog/20211203101530_dummy_feature_schema.xml");
  }

  private ModuleFile masterChangelog() {
    return file("src/test/resources/projects/files/master.xml", "src/main/resources/config/liquibase/master.xml");
  }
}
