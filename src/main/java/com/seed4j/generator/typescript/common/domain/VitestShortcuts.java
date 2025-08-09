package com.seed4j.generator.typescript.common.domain;

import static com.seed4j.module.domain.JHipsterModule.path;
import static com.seed4j.module.domain.JHipsterModule.text;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.replacement.MandatoryReplacer;
import java.util.function.Consumer;

public final class VitestShortcuts {

  private VitestShortcuts() {}

  public static Consumer<JHipsterModule.SeedModuleBuilder> vitestCoverageExclusion(String exclusionFilePattern) {
    // @formatter:off
    return moduleBuilder -> moduleBuilder
      .mandatoryReplacements()
        .in(path("vitest.config.ts"))
          .add(vitestCoverageExclusionReplacement(exclusionFilePattern));
    // @formatter:on
  }

  private static MandatoryReplacer vitestCoverageExclusionReplacement(String filePattern) {
    return new MandatoryReplacer(
      text("(configDefaults.coverage.exclude as string[])"),
      "(configDefaults.coverage.exclude as string[])" + ", '%s'".formatted(filePattern)
    );
  }
}
