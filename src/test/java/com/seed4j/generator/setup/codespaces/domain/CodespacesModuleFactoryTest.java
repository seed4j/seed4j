package com.seed4j.generator.setup.codespaces.domain;

import static com.seed4j.module.infrastructure.secondary.SeedModulesAssertions.assertThatModule;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.SeedModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class CodespacesModuleFactoryTest {

  private static final CodespacesModuleFactory factory = new CodespacesModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = SeedModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    SeedModule module = factory.buildModule(properties);

    assertThatModule(module).hasFiles(".devcontainer/Dockerfile").hasFile(".devcontainer/devcontainer.json").containing("8080");
  }
}
