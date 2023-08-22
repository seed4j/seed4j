package tech.jhipster.lite.module.domain.replacement;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import tech.jhipster.lite.module.domain.GeneratedProjectRepository;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JHipsterModuleOptionalReplacementsFactory extends JHipsterModuleReplacementsFactory {

  private final Optional<JHipsterUpgradeFilesReplacements> upgrades;

  private JHipsterModuleOptionalReplacementsFactory(JHipsterModuleOptionalReplacementsFactoryBuilder builder) {
    super(builder);
    upgrades = Optional.empty();
  }

  private JHipsterModuleOptionalReplacementsFactory(Collection<ContentReplacer> replacers, JHipsterUpgradeFilesReplacements upgrade) {
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

  public static class JHipsterModuleOptionalReplacementsFactoryBuilder
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

  public static class JHipsterModuleFileOptionalReplacementsFactoryBuilder
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

    @Override
    protected ContentReplacer buildReplacer(JHipsterProjectFilePath file, ElementReplacer toReplace, String replacement) {
      return new OptionalFileReplacer(file, new OptionalReplacer(toReplace, replacement));
    }
  }
}
