package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.GeneratedProjectRepository;
import com.seed4j.module.domain.JHipsterModule.SeedModuleBuilder;
import com.seed4j.module.domain.SeedProjectFilePath;
import com.seed4j.module.domain.properties.SeedProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public final class SeedModuleOptionalReplacementsFactory extends SeedModuleReplacementsFactory {

  private final Optional<SeedUpgradeFilesReplacements> upgrades;

  private SeedModuleOptionalReplacementsFactory(JHipsterModuleOptionalReplacementsFactoryBuilder builder) {
    super(builder);
    upgrades = Optional.empty();
  }

  private SeedModuleOptionalReplacementsFactory(Collection<? extends ContentReplacer> replacers, SeedUpgradeFilesReplacements upgrade) {
    super(replacers);
    this.upgrades = Optional.of(upgrade);
  }

  public static JHipsterModuleOptionalReplacementsFactoryBuilder builder(SeedModuleBuilder module) {
    return new JHipsterModuleOptionalReplacementsFactoryBuilder(module);
  }

  public SeedModuleOptionalReplacementsFactory add(SeedUpgradeFilesReplacements upgrade) {
    Assert.notNull("upgrade", upgrade);

    return new SeedModuleOptionalReplacementsFactory(getReplacers(), upgrade);
  }

  public Stream<ContentReplacer> buildReplacers(SeedProjectFolder folder, GeneratedProjectRepository generatedProject) {
    Assert.notNull("folder", folder);
    Assert.notNull("generatedProject", generatedProject);

    return Stream.concat(
      upgrades.stream().flatMap(upgrade -> upgrade.toContentReplacers(folder, generatedProject)),
      getReplacers().stream()
    );
  }

  public static final class JHipsterModuleOptionalReplacementsFactoryBuilder
    extends JHipsterModuleReplacementsFactoryBuilder<
      SeedModuleOptionalReplacementsFactory,
      JHipsterModuleFileOptionalReplacementsFactoryBuilder
    > {

    private JHipsterModuleOptionalReplacementsFactoryBuilder(SeedModuleBuilder module) {
      super(module);
    }

    @Override
    public JHipsterModuleFileOptionalReplacementsFactoryBuilder in(SeedProjectFilePath file) {
      return new JHipsterModuleFileOptionalReplacementsFactoryBuilder(this, file);
    }

    @Override
    public SeedModuleOptionalReplacementsFactory build() {
      return new SeedModuleOptionalReplacementsFactory(this);
    }
  }

  public static final class JHipsterModuleFileOptionalReplacementsFactoryBuilder
    extends JHipsterModuleFileReplacementsBuilder<
      JHipsterModuleOptionalReplacementsFactoryBuilder,
      JHipsterModuleFileOptionalReplacementsFactoryBuilder
    > {

    private JHipsterModuleFileOptionalReplacementsFactoryBuilder(
      JHipsterModuleOptionalReplacementsFactoryBuilder replacements,
      SeedProjectFilePath file
    ) {
      super(replacements, file);
    }

    public JHipsterModuleFileOptionalReplacementsFactoryBuilder add(OptionalReplacer mandatoryReplacer) {
      replacements().add(buildReplacer(file(), mandatoryReplacer.replacer(), mandatoryReplacer.updatedValue()));

      return this;
    }

    @Override
    protected ContentReplacer buildReplacer(SeedProjectFilePath file, ElementReplacer toReplace, String replacement) {
      return new OptionalFileReplacer(file, new OptionalReplacer(toReplace, replacement));
    }
  }
}
