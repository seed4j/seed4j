package tech.jhipster.lite.dsl.parser.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.parser.domain.DslApplication;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@UnitTest
class DslApplicationTest {

  @Test
  void shouldBuildDefaultApplication() {
    DslApplication.DslApplicationBuilder builder = DslApplication.dslApplilcationBuilder();
    DslApplication dslApp = builder.build();
    assertNotNull(dslApp);
    assertNotNull(dslApp.getLstDslContext());
    assertTrue(dslApp.getLstDslContext().isEmpty());
    assertTrue(dslApp.toString().contains("lstDslContext"));
  }

  @Test
  void shouldBuildApplicationWithFolderPriority() {
    DslApplication.DslApplicationBuilder builder = DslApplication.dslApplilcationBuilder();
    ConfigApp.ConfigAppBuilder configBuilder = ConfigApp.configBuilder();
    ConfigApp defaultConfig = configBuilder.build();
    DslApplication dslApp = builder.config(defaultConfig).overrideProjectFolder(new JHipsterProjectFolder("/tmp/override")).build();
    assertNotNull(dslApp);
    assertEquals("/tmp/override", dslApp.getConfig().getProjectFolder().get());
  }

  @Test
  void shouldBuildApplicationWithFolderPriorityIfNoConfig() {
    DslApplication.DslApplicationBuilder builder = DslApplication.dslApplilcationBuilder();
    ConfigApp.ConfigAppBuilder configBuilder = ConfigApp.configBuilder();
    DslApplication dslApp = builder.overrideProjectFolder(new JHipsterProjectFolder("/tmp/override")).build();
    assertNotNull(dslApp);
    assertEquals("/tmp/override", dslApp.getConfig().getProjectFolder().get());
  }
}
