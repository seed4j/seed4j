package tech.jhipster.lite.module.domain.resource;

import java.util.*;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JHipsterModuleTags {

  private static final Comparator<JHipsterModuleTag> TAG_COMPARATOR = Comparator.comparing(JHipsterModuleTag::get);

  private final Collection<JHipsterModuleTag> tags;

  private JHipsterModuleTags(JHipsterModuleTagsBuilder builder) {
    tags = Collections.unmodifiableCollection(builder.tags.stream().sorted(TAG_COMPARATOR).toList());
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
