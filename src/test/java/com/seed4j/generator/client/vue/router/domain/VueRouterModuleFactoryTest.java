package com.seed4j.generator.client.vue.router.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class VueRouterModuleFactoryTest {

  @InjectMocks
  private VueRouterModuleFactory factory;

  @Test
  void shouldBuildVueRouterModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("jhiTest")
      .basePackage("com.seed4j.growth")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModuleWithFiles(module, packageJsonFile(), vitestConfigFile(), appVueFile(), mainVueFile())
      .hasFile("package.json")
      .containing(nodeDependency("vue-router"))
      .and()
      .hasPrefixedFiles("src/main/webapp/app", "router.ts")
      .hasPrefixedFiles("src/main/webapp/app/home","application/HomeRouter.ts")
      .hasFiles("src/test/webapp/unit/router/infrastructure/primary/HomeRouter.spec.ts");
    // @formatter:on
  }
}
