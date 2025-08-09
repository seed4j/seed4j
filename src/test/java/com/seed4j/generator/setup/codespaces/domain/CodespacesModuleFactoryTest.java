package com.seed4j.generator.setup.codespaces.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModule;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class CodespacesModuleFactoryTest {

  private static final CodespacesModuleFactory factory = new CodespacesModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module).hasFiles(".devcontainer/Dockerfile").hasFile(".devcontainer/devcontainer.json").containing("8080");
  }
}
