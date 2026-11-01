package com.seed4j.generator.typescript.common.domain;

import static com.seed4j.module.domain.Seed4JModule.path;
import static com.seed4j.module.domain.replacement.ReplacementCondition.notContaining;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.replacement.MandatoryReplacer;
import com.seed4j.module.domain.replacement.TextReplacer;
import java.util.function.Consumer;

public final class VitestShortcuts {

  private VitestShortcuts() {}

  public static Consumer<Seed4JModule.Seed4JModuleBuilder> vitestCoverageExclusion(String exclusionFilePattern) {
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .mandatoryReplacements()
        .in(path("vitest.config.ts"))
          .add(vitestCoverageExclusionReplacement(exclusionFilePattern));
    // @formatter:on
  }

  private static MandatoryReplacer vitestCoverageExclusionReplacement(String filePattern) {
    String formattedExclusion = ", '%s'".formatted(filePattern);
    return new MandatoryReplacer(
      new TextReplacer(notContaining(formattedExclusion), "(configDefaults.coverage.exclude as string[])"),
      "(configDefaults.coverage.exclude as string[])" + formattedExclusion
    );
  }
}
