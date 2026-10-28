package com.seed4j.module.domain.file;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public record Seed4JFilesToMove(Collection<Seed4JFileToMove> files) {
  public Seed4JFilesToMove(Collection<Seed4JFileToMove> files) {
    Assert.field("files", files).notNull().noNullElement();

    this.files = List.copyOf(files);
  }

  public Stream<Seed4JFileToMove> stream() {
    return files().stream();
  }
}
