package tech.jhipster.lite.generator.client.tikui.core.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class TikuiModulesFactoryTest {

  private static final TikuiModulesFactory factory = new TikuiModulesFactory();

  @Test
  void shouldCreateTikuiModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildTikuiModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile())
      .createFile("package.json")
      .containing(nodeDependency("@tikui/core"))
      .containing(nodeDependency("tikuidoc-tikui"))
      .and()
      .createFile("tikuiconfig.json")
      .containing("\"src\": \"src/main/style\"")
      .containing("\"dist\": \"target/style\"");
  }
}
