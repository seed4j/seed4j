package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.SeedModule.SeedModuleBuilder;
import com.seed4j.module.domain.SeedProjectFilePath;
import java.util.stream.Stream;

public final class SeedModuleMandatoryReplacementsFactory extends SeedModuleReplacementsFactory {

  private SeedModuleMandatoryReplacementsFactory(SeedModuleMandatoryReplacementsFactoryBuilder builder) {
    super(builder);
  }

  public static SeedModuleMandatoryReplacementsFactoryBuilder builder(SeedModuleBuilder module) {
    return new SeedModuleMandatoryReplacementsFactoryBuilder(module);
  }

  public Stream<ContentReplacer> replacers() {
    return getReplacers().stream();
  }

  public static final class SeedModuleMandatoryReplacementsFactoryBuilder
    extends SeedModuleReplacementsFactoryBuilder<
      SeedModuleMandatoryReplacementsFactory,
      SeedModuleFileMandatoryReplacementsFactoryBuilder
    > {

    private SeedModuleMandatoryReplacementsFactoryBuilder(SeedModuleBuilder module) {
      super(module);
    }

    @Override
    public SeedModuleFileMandatoryReplacementsFactoryBuilder in(SeedProjectFilePath file) {
      return new SeedModuleFileMandatoryReplacementsFactoryBuilder(this, file);
    }

    @Override
    public SeedModuleMandatoryReplacementsFactory build() {
      return new SeedModuleMandatoryReplacementsFactory(this);
    }
  }

  public static final class SeedModuleFileMandatoryReplacementsFactoryBuilder
    extends SeedModuleFileReplacementsBuilder<
      SeedModuleMandatoryReplacementsFactoryBuilder,
      SeedModuleFileMandatoryReplacementsFactoryBuilder
    > {

    private SeedModuleFileMandatoryReplacementsFactoryBuilder(
      SeedModuleMandatoryReplacementsFactoryBuilder replacements,
      SeedProjectFilePath file
    ) {
      super(replacements, file);
    }

    public SeedModuleFileMandatoryReplacementsFactoryBuilder add(MandatoryReplacer mandatoryReplacer) {
      replacements().add(buildReplacer(file(), mandatoryReplacer.replacer(), mandatoryReplacer.updatedValue()));

      return this;
    }

    @Override
    protected ContentReplacer buildReplacer(SeedProjectFilePath file, ElementReplacer toReplace, String replacement) {
      return new MandatoryFileReplacer(file, new MandatoryReplacer(toReplace, replacement));
    }
  }
}
