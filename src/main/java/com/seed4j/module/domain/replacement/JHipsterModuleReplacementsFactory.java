package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
import com.seed4j.module.domain.SeedProjectFilePath;
import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;

public abstract class JHipsterModuleReplacementsFactory {

  private final Collection<ContentReplacer> replacers;

  protected JHipsterModuleReplacementsFactory(JHipsterModuleReplacementsFactoryBuilder<?, ?> builder) {
    Assert.notNull("builder", builder);
    Assert.notNull("replacers", builder.replacers);

    this.replacers = SeedCollections.immutable(builder.replacers);
  }

  protected JHipsterModuleReplacementsFactory(Collection<? extends ContentReplacer> replacers) {
    Assert.notNull("replacers", replacers);

    this.replacers = SeedCollections.immutable(replacers);
  }

  protected Collection<ContentReplacer> getReplacers() {
    return replacers;
  }

  public abstract static class JHipsterModuleReplacementsFactoryBuilder<
    Replacements extends JHipsterModuleReplacementsFactory,
    FileReplacementsBuilder extends JHipsterModuleFileReplacementsBuilder<?, ?>
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

    public abstract FileReplacementsBuilder in(SeedProjectFilePath file);

    public abstract Replacements build();
  }

  public abstract static class JHipsterModuleFileReplacementsBuilder<
    ReplacementsBuilder extends JHipsterModuleReplacementsFactoryBuilder<?, ?>,
    Builder extends JHipsterModuleFileReplacementsBuilder<ReplacementsBuilder, Builder>
  > {

    private final ReplacementsBuilder replacements;
    private final SeedProjectFilePath file;

    protected JHipsterModuleFileReplacementsBuilder(ReplacementsBuilder replacements, SeedProjectFilePath file) {
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

    protected ReplacementsBuilder replacements() {
      return replacements;
    }

    protected SeedProjectFilePath file() {
      return file;
    }

    @SuppressWarnings("unchecked")
    private Builder self() {
      return (Builder) this;
    }

    public ReplacementsBuilder and() {
      return replacements;
    }

    protected abstract ContentReplacer buildReplacer(SeedProjectFilePath file, ElementReplacer toReplace, String replacement);
  }
}
