package com.seed4j.generator.setup.aicontext.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.to;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class AiContextModuleFactory {

  private static final Seed4JSource SOURCE = from("setup/aicontext");
  private static final Seed4JSource SKILLS_SOURCE = SOURCE.append("agents/skills");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.template("AGENTS.md"), to("AGENTS.md"))
        .add(SOURCE.file("CLAUDE.md"), to("CLAUDE.md"))
        .add(SOURCE.append("agents").template("context.md"), to(".agents/context.md"))
        .add(SKILLS_SOURCE.append("hexagonal-feature").template("SKILL.md"), to(".agents/skills/hexagonal-feature/SKILL.md"))
        .add(SKILLS_SOURCE.append("tdd-domain").template("SKILL.md"), to(".agents/skills/tdd-domain/SKILL.md"))
        .and()
      .build();
    // @formatter:on
  }
}
