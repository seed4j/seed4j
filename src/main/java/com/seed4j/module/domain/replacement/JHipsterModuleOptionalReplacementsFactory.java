package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.GeneratedProjectRepository;
import com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
import com.seed4j.module.domain.JHipsterProjectFilePath;
import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public final class JHipsterModuleOptionalReplacementsFactory extends JHipsterModuleReplacementsFactory {

  private final Optional<JHipsterUpgradeFilesReplacements> upgrades;

  private JHipsterModuleOptionalReplacementsFactory(JHipsterModuleOptionalReplacementsFactoryBuilder builder) {
    super(builder);
    upgrades = Optional.empty();
  }

  private JHipsterModuleOptionalReplacementsFactory(
    Collection<? extends ContentReplacer> replacers,
    JHipsterUpgradeFilesReplacements upgrade
  ) {
    super(replacers);
    this.upgrades = Optional.of(upgrade);
  }

  public static JHipsterModuleOptionalReplacementsFactoryBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleOptionalReplacementsFactoryBuilder(module);
  }

  public JHipsterModuleOptionalReplacementsFactory add(JHipsterUpgradeFilesReplacements upgrade) {
    Assert.notNull("upgrade", upgrade);

    return new JHipsterModuleOptionalReplacementsFactory(getReplacers(), upgrade);
  }

  public Stream<ContentReplacer> buildReplacers(JHipsterProjectFolder folder, GeneratedProjectRepository generatedProject) {
    Assert.notNull("folder", folder);
    Assert.notNull("generatedProject", generatedProject);

    return Stream.concat(
      upgrades.stream().flatMap(upgrade -> upgrade.toContentReplacers(folder, generatedProject)),
      getReplacers().stream()
    );
  }

  public static final class JHipsterModuleOptionalReplacementsFactoryBuilder
    extends JHipsterModuleReplacementsFactoryBuilder<
      JHipsterModuleOptionalReplacementsFactory,
      JHipsterModuleFileOptionalReplacementsFactoryBuilder
    > {

    private JHipsterModuleOptionalReplacementsFactoryBuilder(JHipsterModuleBuilder module) {
      super(module);
    }

    @Override
    public JHipsterModuleFileOptionalReplacementsFactoryBuilder in(JHipsterProjectFilePath file) {
      return new JHipsterModuleFileOptionalReplacementsFactoryBuilder(this, file);
    }

    @Override
    public JHipsterModuleOptionalReplacementsFactory build() {
      return new JHipsterModuleOptionalReplacementsFactory(this);
    }
  }

  public static final class JHipsterModuleFileOptionalReplacementsFactoryBuilder
    extends JHipsterModuleFileReplacementsBuilder<
      JHipsterModuleOptionalReplacementsFactoryBuilder,
      JHipsterModuleFileOptionalReplacementsFactoryBuilder
    > {

    private JHipsterModuleFileOptionalReplacementsFactoryBuilder(
      JHipsterModuleOptionalReplacementsFactoryBuilder replacements,
      JHipsterProjectFilePath file
    ) {
      super(replacements, file);
    }

    public JHipsterModuleFileOptionalReplacementsFactoryBuilder add(OptionalReplacer mandatoryReplacer) {
      replacements().add(buildReplacer(file(), mandatoryReplacer.replacer(), mandatoryReplacer.updatedValue()));

      return this;
    }

    @Override
    protected ContentReplacer buildReplacer(JHipsterProjectFilePath file, ElementReplacer toReplace, String replacement) {
      return new OptionalFileReplacer(file, new OptionalReplacer(toReplace, replacement));
    }
  }
}
