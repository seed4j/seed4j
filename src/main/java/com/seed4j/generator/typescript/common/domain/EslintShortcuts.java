package com.seed4j.generator.typescript.common.domain;

import static com.seed4j.module.domain.JHipsterModule.lineAfterRegex;
import static com.seed4j.module.domain.JHipsterModule.path;

import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.replacement.MandatoryReplacer;
import java.util.function.Consumer;

public final class EslintShortcuts {

  private EslintShortcuts() {}

  public static Consumer<JHipsterModule.SeedModuleBuilder> eslintTypescriptRule(String rule, Indentation indentation) {
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .mandatoryReplacements()
        .in(path("eslint.config.js"))
        .add(eslintTypescriptVueRuleReplacement(rule, indentation))
        .and()
      .and();
    // @formatter:on
  }

  private static MandatoryReplacer eslintTypescriptVueRuleReplacement(String rule, Indentation indentation) {
    return new MandatoryReplacer(lineAfterRegex("rules: \\{"), indentation.times(3) + rule + ",");
  }
}
