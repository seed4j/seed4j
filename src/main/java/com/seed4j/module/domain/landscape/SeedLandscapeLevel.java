package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.SeedSlug;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

public record SeedLandscapeLevel(Collection<? extends SeedLandscapeElement> elements) {
  public SeedLandscapeLevel(Collection<? extends SeedLandscapeElement> elements) {
    Assert.notEmpty("elements", elements);

    this.elements = Collections.unmodifiableCollection(elements);
  }

  public Stream<SeedSlug> slugs() {
    return elements.stream().flatMap(SeedLandscapeElement::slugs);
  }
}
