package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.Seed4JProjectFilePath;
import com.seed4j.shared.collection.domain.Seed4JCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;

public abstract class Seed4JModuleReplacementsFactory {

  private final Collection<ContentReplacer> replacers;

  protected Seed4JModuleReplacementsFactory(Seed4JModuleReplacementsFactoryBuilder<?, ?> builder) {
    Assert.notNull("builder", builder);
    Assert.notNull("replacers", builder.replacers);

    this.replacers = Seed4JCollections.immutable(builder.replacers);
  }

  protected Seed4JModuleReplacementsFactory(Collection<? extends ContentReplacer> replacers) {
    Assert.notNull("replacers", replacers);

    this.replacers = Seed4JCollections.immutable(replacers);
  }

  protected Collection<ContentReplacer> getReplacers() {
    return replacers;
  }

  public abstract static class Seed4JModuleReplacementsFactoryBuilder<
    Replacements extends Seed4JModuleReplacementsFactory,
    FileReplacementsBuilder extends Seed4JModuleFileReplacementsBuilder<?, ?>
  > {

    private final Seed4JModuleBuilder module;
    private final Collection<ContentReplacer> replacers = new ArrayList<>();

    protected Seed4JModuleReplacementsFactoryBuilder(Seed4JModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public Seed4JModuleBuilder and() {
      return module;
    }

    void add(ContentReplacer fileReplacer) {
      Assert.notNull("fileReplacer", fileReplacer);

      replacers.add(fileReplacer);
    }

    public abstract FileReplacementsBuilder in(Seed4JProjectFilePath file);

    public abstract Replacements build();
  }

  public abstract static class Seed4JModuleFileReplacementsBuilder<
    ReplacementsBuilder extends Seed4JModuleReplacementsFactoryBuilder<?, ?>,
    Builder extends Seed4JModuleFileReplacementsBuilder<ReplacementsBuilder, Builder>
  > {

    private final ReplacementsBuilder replacements;
    private final Seed4JProjectFilePath file;

    protected Seed4JModuleFileReplacementsBuilder(ReplacementsBuilder replacements, Seed4JProjectFilePath file) {
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

    protected Seed4JProjectFilePath file() {
      return file;
    }

    @SuppressWarnings("unchecked")
    private Builder self() {
      return (Builder) this;
    }

    public ReplacementsBuilder and() {
      return replacements;
    }

    protected abstract ContentReplacer buildReplacer(Seed4JProjectFilePath file, ElementReplacer toReplace, String replacement);
  }
}
