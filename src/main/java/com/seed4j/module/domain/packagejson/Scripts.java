package com.seed4j.module.domain.packagejson;

import com.seed4j.shared.collection.domain.SeedCollections;
import java.util.Collection;
import java.util.stream.Stream;

public record Scripts(Collection<Script> scripts) {
  public Scripts(Collection<Script> scripts) {
    this.scripts = SeedCollections.immutable(scripts);
  }

  public boolean isEmpty() {
    return scripts().isEmpty();
  }

  public Stream<Script> stream() {
    return scripts().stream();
  }
}
