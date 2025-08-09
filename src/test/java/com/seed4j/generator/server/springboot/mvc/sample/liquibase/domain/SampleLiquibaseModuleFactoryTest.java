package com.seed4j.generator.server.springboot.mvc.sample.liquibase.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class SampleLiquibaseModuleFactoryTest {

  private static final SampleLiquibaseModuleFactory factory = new SampleLiquibaseModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .put("date", "2021-12-03T10:15:30.00Z")
      .build();

    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, masterChangelog())
      .hasFile("src/main/resources/config/liquibase/master.xml")
      .containing(
        "<include file=\"config/liquibase/changelog/20211203101530_sample_feature_schema.xml\" relativeToChangelogFile=\"false\"/>"
      )
      .and()
      .hasFiles("src/main/resources/config/liquibase/changelog/20211203101530_sample_feature_schema.xml");
  }

  private ModuleFile masterChangelog() {
    return file("src/test/resources/projects/files/master.xml", "src/main/resources/config/liquibase/master.xml");
  }
}
