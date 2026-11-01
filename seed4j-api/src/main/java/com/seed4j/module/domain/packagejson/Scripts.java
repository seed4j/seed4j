package com.seed4j.module.domain.packagejson;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public record Scripts(Collection<Script> scripts) {
  public Scripts(Collection<Script> scripts) {
    Assert.field("scripts", scripts).notNull().noNullElement();

    this.scripts = List.copyOf(scripts);
  }

  public boolean isEmpty() {
    return scripts().isEmpty();
  }

  public Stream<Script> stream() {
    return scripts().stream();
  }
}
