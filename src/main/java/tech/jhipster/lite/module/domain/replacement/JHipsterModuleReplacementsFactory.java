package tech.jhipster.lite.module.domain.replacement;

import java.util.ArrayList;
import java.util.Collection;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;
import tech.jhipster.lite.shared.collection.domain.JHipsterCollections;
import tech.jhipster.lite.shared.error.domain.Assert;

public abstract class JHipsterModuleReplacementsFactory {

  private final Collection<ContentReplacer> replacers;

  protected JHipsterModuleReplacementsFactory(JHipsterModuleReplacementsFactoryBuilder<?, ?> builder) {
    Assert.notNull("builder", builder);
    Assert.notNull("replacers", builder.replacers);

    this.replacers = JHipsterCollections.immutable(builder.replacers);
  }

  protected JHipsterModuleReplacementsFactory(Collection<ContentReplacer> replacers) {
    Assert.notNull("replacers", replacers);

    this.replacers = JHipsterCollections.immutable(replacers);
  }

  protected Collection<ContentReplacer> getReplacers() {
    return replacers;
  }

  public abstract static class JHipsterModuleReplacementsFactoryBuilder<
    Replacements extends JHipsterModuleReplacementsFactory, FileReplacementsBuilder extends JHipsterModuleFileReplacementsBuilder<?, ?>
  > {

    private final JHipsterModuleBuilder module;
    private final Collection<ContentReplacer> replacers = new ArrayList<>();

    protected JHipsterModuleReplacementsFactoryBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    void add(ContentReplacer fileReplacer) {
      Assert.notNull("fileReplacer", fileReplacer);

      replacers.add(fileReplacer);
    }

    public abstract FileReplacementsBuilder in(JHipsterProjectFilePath file);

    public abstract Replacements build();
  }

  public abstract static class JHipsterModuleFileReplacementsBuilder<
    ReplacementsBuilder extends JHipsterModuleReplacementsFactoryBuilder<?, ?>,
    Builder extends JHipsterModuleFileReplacementsBuilder<ReplacementsBuilder, Builder>
  > {

    private final ReplacementsBuilder replacements;
    private final JHipsterProjectFilePath file;

    protected JHipsterModuleFileReplacementsBuilder(ReplacementsBuilder replacements, JHipsterProjectFilePath file) {
      Assert.notNull("replacements", replacements);
      Assert.notNull("file", file);

      this.replacements = replacements;
      this.file = file;
    }

    public Builder add(ElementReplacer elementToReplace, String replacement) {
      Assert.notNull("elementToReplace", elementToReplace);

      replacements.add(buildReplacer(file, elementToReplace, replacement));

      return self();
    }

    @SuppressWarnings("unchecked")
    private Builder self() {
      return (Builder) this;
    }

    public ReplacementsBuilder and() {
      return replacements;
    }

    protected abstract ContentReplacer buildReplacer(JHipsterProjectFilePath file, ElementReplacer toReplace, String replacement);
  }
}
