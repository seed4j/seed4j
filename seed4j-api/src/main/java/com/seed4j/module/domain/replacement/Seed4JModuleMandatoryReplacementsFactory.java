package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.Seed4JProjectFilePath;
import java.util.stream.Stream;

public final class Seed4JModuleMandatoryReplacementsFactory extends Seed4JModuleReplacementsFactory {

  private Seed4JModuleMandatoryReplacementsFactory(Seed4JModuleMandatoryReplacementsFactoryBuilder builder) {
    super(builder);
  }

  public static Seed4JModuleMandatoryReplacementsFactoryBuilder builder(Seed4JModuleBuilder module) {
    return new Seed4JModuleMandatoryReplacementsFactoryBuilder(module);
  }

  public Stream<ContentReplacer> replacers() {
    return getReplacers().stream();
  }

  public static final class Seed4JModuleMandatoryReplacementsFactoryBuilder
    extends Seed4JModuleReplacementsFactoryBuilder<
      Seed4JModuleMandatoryReplacementsFactory,
      Seed4JModuleFileMandatoryReplacementsFactoryBuilder
    >
  {

    private Seed4JModuleMandatoryReplacementsFactoryBuilder(Seed4JModuleBuilder module) {
      super(module);
    }

    @Override
    public Seed4JModuleFileMandatoryReplacementsFactoryBuilder in(Seed4JProjectFilePath file) {
      return new Seed4JModuleFileMandatoryReplacementsFactoryBuilder(this, file);
    }

    @Override
    public Seed4JModuleMandatoryReplacementsFactory build() {
      return new Seed4JModuleMandatoryReplacementsFactory(this);
    }
  }

  public static final class Seed4JModuleFileMandatoryReplacementsFactoryBuilder
    extends Seed4JModuleFileReplacementsBuilder<
      Seed4JModuleMandatoryReplacementsFactoryBuilder,
      Seed4JModuleFileMandatoryReplacementsFactoryBuilder
    >
  {

    private Seed4JModuleFileMandatoryReplacementsFactoryBuilder(
      Seed4JModuleMandatoryReplacementsFactoryBuilder replacements,
      Seed4JProjectFilePath file
    ) {
      super(replacements, file);
    }

    public Seed4JModuleFileMandatoryReplacementsFactoryBuilder add(MandatoryReplacer mandatoryReplacer) {
      replacements().add(buildReplacer(file(), mandatoryReplacer.replacer(), mandatoryReplacer.updatedValue()));

      return this;
    }

    @Override
    protected ContentReplacer buildReplacer(Seed4JProjectFilePath file, ElementReplacer toReplace, String replacement) {
      return new MandatoryFileReplacer(file, new MandatoryReplacer(toReplace, replacement));
    }
  }
}
