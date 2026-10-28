package com.seed4j.module.domain.resource;

import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public final class Seed4JModuleTags {

  private static final Comparator<Seed4JModuleTag> TAG_COMPARATOR = Comparator.comparing(Seed4JModuleTag::get);

  private final Collection<Seed4JModuleTag> tags;

  private Seed4JModuleTags(Seed4JModuleTagsBuilder builder) {
    tags = builder.tags.stream().sorted(TAG_COMPARATOR).toList();
  }

  public static Seed4JModuleTagsBuilder builder() {
    return new Seed4JModuleTagsBuilder();
  }

  public Collection<Seed4JModuleTag> get() {
    return tags;
  }

  public boolean contains(Seed4JModuleTag other) {
    Assert.notNull("other", other);

    return tags.contains(other);
  }

  @Override
  public String toString() {
    return tags.toString();
  }

  public static class Seed4JModuleTagsBuilder {

    private final Collection<Seed4JModuleTag> tags = new ArrayList<>();

    public Seed4JModuleTagsBuilder add(String... tags) {
      add(List.of(tags));

      return this;
    }

    private Seed4JModuleTagsBuilder add(Collection<String> tags) {
      Assert.field("tags", tags).noNullElement();

      this.tags.addAll(tags.stream().map(Seed4JModuleTag::new).toList());

      return this;
    }

    public Seed4JModuleTags build() {
      return new Seed4JModuleTags(this);
    }
  }
}
