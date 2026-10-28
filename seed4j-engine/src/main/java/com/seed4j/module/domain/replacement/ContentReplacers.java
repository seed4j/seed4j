package com.seed4j.module.domain.replacement;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public record ContentReplacers(Collection<? extends ContentReplacer> replacers) {
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
