package com.seed4j.module.domain.packagejson;

import com.seed4j.shared.collection.domain.Seed4JCollections;
import java.util.Collection;
import java.util.stream.Stream;

public record Scripts(Collection<Script> scripts) {
  public Scripts(Collection<Script> scripts) {
    this.scripts = Seed4JCollections.immutable(scripts);
  }

  public boolean isEmpty() {
    return scripts().isEmpty();
  }

  public Stream<Script> stream() {
    return scripts().stream();
  }
}
