package com.seed4j.generator.setup.gitpod.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.assertThatModule;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class GitpodModuleFactoryTest {

  private static final GitpodModuleFactory factory = new GitpodModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    SeedModule module = factory.buildModule(properties);

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
