package com.seed4j.generator.setup.codespaces.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModule;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class CodespacesModuleFactoryTest {

  private static final CodespacesModuleFactory factory = new CodespacesModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    Seed4JModule module = factory.buildModule(properties);

    assertThatModule(module).hasFiles(".devcontainer/Dockerfile").hasFile(".devcontainer/devcontainer.json").containing("8080");
  }
}
