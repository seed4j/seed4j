package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
import com.seed4j.module.domain.JHipsterProjectFilePath;
import java.util.stream.Stream;

public final class JHipsterModuleMandatoryReplacementsFactory extends JHipsterModuleReplacementsFactory {

  private JHipsterModuleMandatoryReplacementsFactory(JHipsterModuleMandatoryReplacementsFactoryBuilder builder) {
    super(builder);
  }

  public static JHipsterModuleMandatoryReplacementsFactoryBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleMandatoryReplacementsFactoryBuilder(module);
  }

  public Stream<ContentReplacer> replacers() {
    return getReplacers().stream();
  }

  public static final class JHipsterModuleMandatoryReplacementsFactoryBuilder
    extends JHipsterModuleReplacementsFactoryBuilder<
      JHipsterModuleMandatoryReplacementsFactory,
      JHipsterModuleFileMandatoryReplacementsFactoryBuilder
    > {

    private JHipsterModuleMandatoryReplacementsFactoryBuilder(JHipsterModuleBuilder module) {
      super(module);
    }

    @Override
    public JHipsterModuleFileMandatoryReplacementsFactoryBuilder in(JHipsterProjectFilePath file) {
      return new JHipsterModuleFileMandatoryReplacementsFactoryBuilder(this, file);
    }

    @Override
    public JHipsterModuleMandatoryReplacementsFactory build() {
      return new JHipsterModuleMandatoryReplacementsFactory(this);
    }
  }

  public static final class JHipsterModuleFileMandatoryReplacementsFactoryBuilder
    extends JHipsterModuleFileReplacementsBuilder<
      JHipsterModuleMandatoryReplacementsFactoryBuilder,
      JHipsterModuleFileMandatoryReplacementsFactoryBuilder
    > {

    private JHipsterModuleFileMandatoryReplacementsFactoryBuilder(
      JHipsterModuleMandatoryReplacementsFactoryBuilder replacements,
      JHipsterProjectFilePath file
    ) {
      super(replacements, file);
    }

    public JHipsterModuleFileMandatoryReplacementsFactoryBuilder add(MandatoryReplacer mandatoryReplacer) {
      replacements().add(buildReplacer(file(), mandatoryReplacer.replacer(), mandatoryReplacer.updatedValue()));

      return this;
    }

    @Override
    protected ContentReplacer buildReplacer(JHipsterProjectFilePath file, ElementReplacer toReplace, String replacement) {
      return new MandatoryFileReplacer(file, new MandatoryReplacer(toReplace, replacement));
    }
  }
}
