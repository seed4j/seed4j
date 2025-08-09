package com.seed4j.module.domain.gitignore;

import com.seed4j.module.domain.SeedModule.SeedModuleBuilder;
import com.seed4j.module.domain.gitignore.GitIgnoreEntry.GitIgnoreComment;
import com.seed4j.module.domain.gitignore.GitIgnoreEntry.GitIgnorePattern;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public final class SeedModuleGitIgnore {

  private final Collection<GitIgnoreEntry> entries;

  private SeedModuleGitIgnore(Collection<GitIgnoreEntry> entries) {
    Assert.field("entries", entries).notNull().noNullElement();
    this.entries = entries;
  }

  public void forEach(Consumer<GitIgnoreEntry> consumer) {
    Assert.notNull("consumer", consumer);

    entries.forEach(consumer);
  }

  public boolean isNotEmpty() {
    return !entries.isEmpty();
  }

  @Override
  public String toString() {
    return entries.toString();
  }

  public static SeedModuleGitIgnoreBuilder builder(SeedModuleBuilder parentModuleBuilder) {
    return new SeedModuleGitIgnoreBuilder(parentModuleBuilder);
  }

  public static final class SeedModuleGitIgnoreBuilder {

    private final SeedModuleBuilder parentModuleBuilder;
    private final Collection<GitIgnoreEntry> entries = new ArrayList<>();

    private SeedModuleGitIgnoreBuilder(SeedModuleBuilder parentModuleBuilder) {
      Assert.notNull("module", parentModuleBuilder);

      this.parentModuleBuilder = parentModuleBuilder;
    }

    /**
     * Declare a pattern to be added to the {@code .gitignore} file.
     */
    public SeedModuleGitIgnoreBuilder pattern(GitIgnorePattern pattern) {
      Assert.notNull("pattern", pattern);
      entries.add(pattern);

      return this;
    }

    /**
     * Declare a pattern to be added to the {@code .gitignore} file.
     */
    public SeedModuleGitIgnoreBuilder pattern(String pattern) {
      return pattern(new GitIgnorePattern(pattern));
    }

    /**
     * Declare a comment to be added to the {@code .gitignore} file.
     */
    public SeedModuleGitIgnoreBuilder comment(GitIgnoreComment comment) {
      Assert.notNull("comment", comment);
      entries.add(comment);

      return this;
    }

    /**
     * Declare a comment to be added to the {@code .gitignore} file.
     */
    public SeedModuleGitIgnoreBuilder comment(String comment) {
      return comment(new GitIgnoreComment(comment));
    }

    public SeedModuleBuilder and() {
      return parentModuleBuilder;
    }

    public SeedModuleGitIgnore build() {
      return new SeedModuleGitIgnore(entries);
    }
  }
}
