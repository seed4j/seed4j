package com.seed4j.module.domain.resource;

import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public final class SeedModuleTags {

  private static final Comparator<SeedModuleTag> TAG_COMPARATOR = Comparator.comparing(SeedModuleTag::get);

  private final Collection<SeedModuleTag> tags;

  private SeedModuleTags(SeedModuleTagsBuilder builder) {
    tags = builder.tags.stream().sorted(TAG_COMPARATOR).toList();
  }

  public static SeedModuleTagsBuilder builder() {
    return new SeedModuleTagsBuilder();
  }

  public Collection<SeedModuleTag> get() {
    return tags;
  }

  public boolean contains(SeedModuleTag other) {
    Assert.notNull("other", other);

    return tags.contains(other);
  }

  @Override
  public String toString() {
    return tags.toString();
  }

  public static class SeedModuleTagsBuilder {

    private final Collection<SeedModuleTag> tags = new ArrayList<>();

    public SeedModuleTagsBuilder add(String... tags) {
      add(List.of(tags));

      return this;
    }

    private SeedModuleTagsBuilder add(Collection<String> tags) {
      Assert.field("tags", tags).noNullElement();

      this.tags.addAll(tags.stream().map(SeedModuleTag::new).toList());

      return this;
    }

    public SeedModuleTags build() {
      return new SeedModuleTags(this);
    }
  }
}
