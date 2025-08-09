package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.JHipsterModule.SeedModuleBuilder;
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
    extends JHipsterModuleReplacementsFactoryBuilder<
      SeedModuleMandatoryReplacementsFactory,
      JHipsterModuleFileMandatoryReplacementsFactoryBuilder
    > {

    private SeedModuleMandatoryReplacementsFactoryBuilder(SeedModuleBuilder module) {
      super(module);
    }

    @Override
    public JHipsterModuleFileMandatoryReplacementsFactoryBuilder in(SeedProjectFilePath file) {
      return new JHipsterModuleFileMandatoryReplacementsFactoryBuilder(this, file);
    }

    @Override
    public SeedModuleMandatoryReplacementsFactory build() {
      return new SeedModuleMandatoryReplacementsFactory(this);
    }
  }

  public static final class JHipsterModuleFileMandatoryReplacementsFactoryBuilder
    extends JHipsterModuleFileReplacementsBuilder<
      SeedModuleMandatoryReplacementsFactoryBuilder,
      JHipsterModuleFileMandatoryReplacementsFactoryBuilder
    > {

    private JHipsterModuleFileMandatoryReplacementsFactoryBuilder(
      SeedModuleMandatoryReplacementsFactoryBuilder replacements,
      SeedProjectFilePath file
    ) {
      super(replacements, file);
    }

    public JHipsterModuleFileMandatoryReplacementsFactoryBuilder add(MandatoryReplacer mandatoryReplacer) {
      replacements().add(buildReplacer(file(), mandatoryReplacer.replacer(), mandatoryReplacer.updatedValue()));

      return this;
    }

    @Override
    protected ContentReplacer buildReplacer(SeedProjectFilePath file, ElementReplacer toReplace, String replacement) {
      return new MandatoryFileReplacer(file, new MandatoryReplacer(toReplace, replacement));
    }
  }
}
