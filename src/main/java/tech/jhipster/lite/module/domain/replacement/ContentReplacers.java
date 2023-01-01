package tech.jhipster.lite.module.domain.replacement;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import tech.jhipster.lite.error.domain.Assert;

public record ContentReplacers(Collection<ContentReplacer> replacers) {
  public ContentReplacers {
    Assert.field("replacers", replacers).notNull().noNullElement();
  }

  public static ContentReplacers of(ContentReplacer... replacers) {
    Assert.field("replacers", replacers).notNull();
    return new ContentReplacers(List.of(replacers));
  }

  public void forEach(Consumer<ContentReplacer> action) {
    replacers().forEach(action);
  }
}
