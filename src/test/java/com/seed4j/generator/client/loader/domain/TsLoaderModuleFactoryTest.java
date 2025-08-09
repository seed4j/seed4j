package com.seed4j.generator.client.loader.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class TsLoaderModuleFactoryTest {

  private static TsLoaderModuleFactory factory = new TsLoaderModuleFactory();

  @Test
  void shouldBuildModule() {
    SeedModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    SeedModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile())
      .hasFile("src/main/webapp/app/shared/loader/infrastructure/primary/Loader.ts")
      .and()
      .hasFile("src/test/webapp/unit/shared/loader/infrastructure/primary/Loader.spec.ts");
  }
}
