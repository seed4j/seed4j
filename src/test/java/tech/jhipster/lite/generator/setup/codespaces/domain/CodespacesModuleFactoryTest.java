package tech.jhipster.lite.generator.setup.codespaces.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class CodespacesModuleFactoryTest {

  private static final CodespacesModuleFactory factory = new CodespacesModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .put("serverPort", 8080)
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module).hasFiles(".devcontainer/Dockerfile").hasFile(".devcontainer/devcontainer.json").containing("8080");
  }
}
