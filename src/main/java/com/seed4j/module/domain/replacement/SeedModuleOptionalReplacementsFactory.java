package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.GeneratedProjectRepository;
import com.seed4j.module.domain.SeedModule.SeedModuleBuilder;
import com.seed4j.module.domain.SeedProjectFilePath;
import com.seed4j.module.domain.properties.SeedProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public final class SeedModuleOptionalReplacementsFactory extends SeedModuleReplacementsFactory {

  private final Optional<SeedUpgradeFilesReplacements> upgrades;

  private SeedModuleOptionalReplacementsFactory(SeedModuleOptionalReplacementsFactoryBuilder builder) {
    super(builder);
    upgrades = Optional.empty();
  }

  private SeedModuleOptionalReplacementsFactory(Collection<? extends ContentReplacer> replacers, SeedUpgradeFilesReplacements upgrade) {
    super(replacers);
    this.upgrades = Optional.of(upgrade);
  }

  public static SeedModuleOptionalReplacementsFactoryBuilder builder(SeedModuleBuilder module) {
    return new SeedModuleOptionalReplacementsFactoryBuilder(module);
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

  public static final class SeedModuleOptionalReplacementsFactoryBuilder
    extends SeedModuleReplacementsFactoryBuilder<SeedModuleOptionalReplacementsFactory, SeedModuleFileOptionalReplacementsFactoryBuilder> {

    private SeedModuleOptionalReplacementsFactoryBuilder(SeedModuleBuilder module) {
      super(module);
    }

    @Override
    public SeedModuleFileOptionalReplacementsFactoryBuilder in(SeedProjectFilePath file) {
      return new SeedModuleFileOptionalReplacementsFactoryBuilder(this, file);
    }

    @Override
    public SeedModuleOptionalReplacementsFactory build() {
      return new SeedModuleOptionalReplacementsFactory(this);
    }
  }

  public static final class SeedModuleFileOptionalReplacementsFactoryBuilder
    extends SeedModuleFileReplacementsBuilder<
      SeedModuleOptionalReplacementsFactoryBuilder,
      SeedModuleFileOptionalReplacementsFactoryBuilder
    > {

    private SeedModuleFileOptionalReplacementsFactoryBuilder(
      SeedModuleOptionalReplacementsFactoryBuilder replacements,
      SeedProjectFilePath file
    ) {
      super(replacements, file);
    }

    public SeedModuleFileOptionalReplacementsFactoryBuilder add(OptionalReplacer mandatoryReplacer) {
      replacements().add(buildReplacer(file(), mandatoryReplacer.replacer(), mandatoryReplacer.updatedValue()));

      return this;
    }

    @Override
    protected ContentReplacer buildReplacer(SeedProjectFilePath file, ElementReplacer toReplace, String replacement) {
      return new OptionalFileReplacer(file, new OptionalReplacer(toReplace, replacement));
    }
  }
}
