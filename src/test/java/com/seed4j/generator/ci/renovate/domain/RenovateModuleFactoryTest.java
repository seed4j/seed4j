package com.seed4j.generator.ci.renovate.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.readmeFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class RenovateModuleFactoryTest {

  private final RenovateModuleFactory factory = new RenovateModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModule module = factory.buildModule(properties());

    assertThatModuleWithFiles(module, pomFile(), readmeFile())
      .hasFile("renovate.json")
      .containing("config:recommended")
      .containing("docker-compose");
  }

  private Seed4JModuleProperties properties() {
    return Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();
  }
}
