package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.Seed4JSlug;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

public record Seed4JLandscapeLevel(Collection<? extends Seed4JLandscapeElement> elements) {
  public Seed4JLandscapeLevel(Collection<? extends Seed4JLandscapeElement> elements) {
    Assert.notEmpty("elements", elements);

    this.elements = Collections.unmodifiableCollection(elements);
  }

  public Stream<Seed4JSlug> slugs() {
    return elements.stream().flatMap(Seed4JLandscapeElement::slugs);
  }
}
