package com.seed4j.generator.setup.aicontext.domain;

import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModule;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class AiContextModuleFactoryTest {

  private static final AiContextModuleFactory factory = new AiContextModuleFactory();

  @Test
  void shouldBuildModule() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectName("Growth Project")
      .build();

    Seed4JModule module = factory.buildModule(properties);

    // @formatter:off
    assertThatModule(module)
      .hasFile("AGENTS.md")
        .containing("# Growth Project — instructions for AI agents")
        .containing("[`.agents/context.md`](.agents/context.md)")
        .containing("`seed4j skill install`")
        .and()
      .hasFile("CLAUDE.md")
        .containing("@AGENTS.md")
        .and()
      .hasFile(".agents/context.md")
        .containing("- Base package: `com.seed4j.growth`")
        .containing("com.seed4j.growth.<context>/")
        .containing("@com.seed4j.growth.SharedKernel")
        .containing("Pattern.compile(\"^\\\\d{13}$\")")
        .and()
      .hasFile(".agents/skills/hexagonal-feature/SKILL.md")
        .containing("name: hexagonal-feature")
        .containing("`com.seed4j.growth.shared.error.domain.Assert`")
        .and()
      .hasFile(".agents/skills/tdd-domain/SKILL.md")
        .containing("name: tdd-domain")
        .containing("`@com.seed4j.growth.UnitTest`");
    // @formatter:on
  }
}
