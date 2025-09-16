package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.GeneratedProjectRepository;
import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.Seed4JProjectFilePath;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public final class Seed4JModuleOptionalReplacementsFactory extends Seed4JModuleReplacementsFactory {

  private final Optional<Seed4JUpgradeFilesReplacements> upgrades;

  private Seed4JModuleOptionalReplacementsFactory(Seed4JModuleOptionalReplacementsFactoryBuilder builder) {
    super(builder);
    upgrades = Optional.empty();
  }

  private Seed4JModuleOptionalReplacementsFactory(Collection<? extends ContentReplacer> replacers, Seed4JUpgradeFilesReplacements upgrade) {
    super(replacers);
    this.upgrades = Optional.of(upgrade);
  }

  public static Seed4JModuleOptionalReplacementsFactoryBuilder builder(Seed4JModuleBuilder module) {
    return new Seed4JModuleOptionalReplacementsFactoryBuilder(module);
  }

  public Seed4JModuleOptionalReplacementsFactory add(Seed4JUpgradeFilesReplacements upgrade) {
    Assert.notNull("upgrade", upgrade);

    return new Seed4JModuleOptionalReplacementsFactory(getReplacers(), upgrade);
  }

  public Stream<ContentReplacer> buildReplacers(Seed4JProjectFolder folder, GeneratedProjectRepository generatedProject) {
    Assert.notNull("folder", folder);
    Assert.notNull("generatedProject", generatedProject);

    return Stream.concat(
      upgrades.stream().flatMap(upgrade -> upgrade.toContentReplacers(folder, generatedProject)),
      getReplacers().stream()
    );
  }

  public static final class Seed4JModuleOptionalReplacementsFactoryBuilder
    extends Seed4JModuleReplacementsFactoryBuilder<
      Seed4JModuleOptionalReplacementsFactory,
      Seed4JModuleFileOptionalReplacementsFactoryBuilder
    > {

    private Seed4JModuleOptionalReplacementsFactoryBuilder(Seed4JModuleBuilder module) {
      super(module);
    }

    @Override
    public Seed4JModuleFileOptionalReplacementsFactoryBuilder in(Seed4JProjectFilePath file) {
      return new Seed4JModuleFileOptionalReplacementsFactoryBuilder(this, file);
    }

    @Override
    public Seed4JModuleOptionalReplacementsFactory build() {
      return new Seed4JModuleOptionalReplacementsFactory(this);
    }
  }

  public static final class Seed4JModuleFileOptionalReplacementsFactoryBuilder
    extends Seed4JModuleFileReplacementsBuilder<
      Seed4JModuleOptionalReplacementsFactoryBuilder,
      Seed4JModuleFileOptionalReplacementsFactoryBuilder
    > {

    private Seed4JModuleFileOptionalReplacementsFactoryBuilder(
      Seed4JModuleOptionalReplacementsFactoryBuilder replacements,
      Seed4JProjectFilePath file
    ) {
      super(replacements, file);
    }

    public Seed4JModuleFileOptionalReplacementsFactoryBuilder add(OptionalReplacer mandatoryReplacer) {
      replacements().add(buildReplacer(file(), mandatoryReplacer.replacer(), mandatoryReplacer.updatedValue()));

      return this;
    }

    @Override
    protected ContentReplacer buildReplacer(Seed4JProjectFilePath file, ElementReplacer toReplace, String replacement) {
      return new OptionalFileReplacer(file, new OptionalReplacer(toReplace, replacement));
    }
  }
}
