package com.seed4j.generator.ci.renovate.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;
import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.readmeFile;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class RenovateModuleFactoryTest {

  private final RenovateModuleFactory factory = new RenovateModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModule module = factory.buildModule(properties());

    assertThatModuleWithFiles(module, pomFile(), readmeFile())
      .hasFile("renovate.json")
      .containing("config:recommended")
      .containing("docker-compose");
  }

  private JHipsterModuleProperties properties() {
    return JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.jhlitest")
      .projectBaseName("myapp")
      .build();
  }
}
