package com.seed4j.module.domain.gitignore;

import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.gitignore.GitIgnoreEntry.GitIgnoreComment;
import com.seed4j.module.domain.gitignore.GitIgnoreEntry.GitIgnorePattern;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public final class Seed4JModuleGitIgnore {

  private final Collection<GitIgnoreEntry> entries;

  private Seed4JModuleGitIgnore(Collection<GitIgnoreEntry> entries) {
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

  public static Seed4JModuleGitIgnoreBuilder builder(Seed4JModuleBuilder parentModuleBuilder) {
    return new Seed4JModuleGitIgnoreBuilder(parentModuleBuilder);
  }

  public static final class Seed4JModuleGitIgnoreBuilder {

    private final Seed4JModuleBuilder parentModuleBuilder;
    private final Collection<GitIgnoreEntry> entries = new ArrayList<>();

    private Seed4JModuleGitIgnoreBuilder(Seed4JModuleBuilder parentModuleBuilder) {
      Assert.notNull("module", parentModuleBuilder);

      this.parentModuleBuilder = parentModuleBuilder;
    }

    /**
     * Declare a pattern to be added to the {@code .gitignore} file.
     */
    public Seed4JModuleGitIgnoreBuilder pattern(GitIgnorePattern pattern) {
      Assert.notNull("pattern", pattern);
      entries.add(pattern);

      return this;
    }

    /**
     * Declare a pattern to be added to the {@code .gitignore} file.
     */
    public Seed4JModuleGitIgnoreBuilder pattern(String pattern) {
      return pattern(new GitIgnorePattern(pattern));
    }

    /**
     * Declare a comment to be added to the {@code .gitignore} file.
     */
    public Seed4JModuleGitIgnoreBuilder comment(GitIgnoreComment comment) {
      Assert.notNull("comment", comment);
      entries.add(comment);

      return this;
    }

    /**
     * Declare a comment to be added to the {@code .gitignore} file.
     */
    public Seed4JModuleGitIgnoreBuilder comment(String comment) {
      return comment(new GitIgnoreComment(comment));
    }

    public Seed4JModuleBuilder and() {
      return parentModuleBuilder;
    }

    public Seed4JModuleGitIgnore build() {
      return new Seed4JModuleGitIgnore(entries);
    }
  }
}
