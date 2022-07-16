package tech.jhipster.lite.module.domain;

import java.util.*;
import tech.jhipster.lite.error.domain.Assert;

public class JHipsterModuleTags {

  public static final Comparator<JHipsterModuleTag> COMPARING = Comparator.comparing(JHipsterModuleTag::get);
  private final Collection<JHipsterModuleTag> tags;

  private JHipsterModuleTags(JHipsterModuleTagsBuilder builder) {
    tags = Collections.unmodifiableCollection(builder.tags.stream().sorted(COMPARING).toList());
  }

  public static JHipsterModuleTagsBuilder builder() {
    return new JHipsterModuleTagsBuilder();
  }

  public Collection<JHipsterModuleTag> get() {
    return tags;
  }

  public static class JHipsterModuleTagsBuilder {

    private final Collection<JHipsterModuleTag> tags = new ArrayList<>();

    public JHipsterModuleTagsBuilder add(JHipsterModuleTag tag) {
      Assert.notNull("tag", tag);
      tags.add(tag);

      return this;
    }

    public JHipsterModuleTagsBuilder add(String tag) {
      tags.add(new JHipsterModuleTag(tag));

      return this;
    }

    public JHipsterModuleTagsBuilder add(String... tags) {
      add(List.of(tags));

      return this;
    }

    public JHipsterModuleTagsBuilder add(Collection<String> tags) {
      Assert.field("tags", tags).noNullElement();
      this.tags.addAll(tags.stream().map(JHipsterModuleTag::new).toList());
      return this;
    }

    public JHipsterModuleTags build() {
      return new JHipsterModuleTags(this);
    }
  }
}
