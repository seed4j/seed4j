package com.seed4j.module.domain.resource;

import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public final class JHipsterModuleTags {

  private static final Comparator<JHipsterModuleTag> TAG_COMPARATOR = Comparator.comparing(JHipsterModuleTag::get);

  private final Collection<JHipsterModuleTag> tags;

  private JHipsterModuleTags(JHipsterModuleTagsBuilder builder) {
    tags = builder.tags.stream().sorted(TAG_COMPARATOR).toList();
  }

  public static JHipsterModuleTagsBuilder builder() {
    return new JHipsterModuleTagsBuilder();
  }

  public Collection<JHipsterModuleTag> get() {
    return tags;
  }

  public boolean contains(JHipsterModuleTag other) {
    Assert.notNull("other", other);

    return tags.contains(other);
  }

  @Override
  public String toString() {
    return tags.toString();
  }

  public static class JHipsterModuleTagsBuilder {

    private final Collection<JHipsterModuleTag> tags = new ArrayList<>();

    public JHipsterModuleTagsBuilder add(String... tags) {
      add(List.of(tags));

      return this;
    }

    private JHipsterModuleTagsBuilder add(Collection<String> tags) {
      Assert.field("tags", tags).noNullElement();

      this.tags.addAll(tags.stream().map(JHipsterModuleTag::new).toList());

      return this;
    }

    public JHipsterModuleTags build() {
      return new JHipsterModuleTags(this);
    }
  }
}
