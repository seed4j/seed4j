package com.seed4j.generator.setup.gitpod.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModule;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class GitpodModuleFactoryTest {

  private static final GitpodModuleFactory factory = new GitpodModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFile(".gitpod.yml")
      .containing(
        """
        port: 8080
        """
      )
      .and()
      .hasFiles(".gitpod.Dockerfile");
  }
}
