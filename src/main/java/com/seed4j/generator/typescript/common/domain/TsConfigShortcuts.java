package com.seed4j.generator.typescript.common.domain;

import static com.seed4j.module.domain.JHipsterModule.lineAfterRegex;
import static com.seed4j.module.domain.JHipsterModule.path;

import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.replacement.MandatoryReplacer;
import java.util.function.Consumer;

public final class TsConfigShortcuts {

  private TsConfigShortcuts() {}

  public static Consumer<JHipsterModule.JHipsterModuleBuilder> tsConfigCompilerOption(
    String optionName,
    boolean optionValue,
    Indentation indentation
  ) {
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .mandatoryReplacements()
        .in(path("tsconfig.json"))
          .add(tsConfigCompilerOptionReplacement(optionName, optionValue, indentation))
          .and();
    // @formatter:on
  }

  private static MandatoryReplacer tsConfigCompilerOptionReplacement(String optionName, boolean optionValue, Indentation indentation) {
    String compilerOption = indentation.times(2) + "\"%s\": %s,".formatted(optionName, optionValue);
    return new MandatoryReplacer(lineAfterRegex("\"compilerOptions\":"), compilerOption);
  }
}
