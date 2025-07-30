package com.seed4j.module.domain.landscape;

import com.seed4j.module.domain.JHipsterSlug;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

public record JHipsterLandscapeLevel(Collection<? extends JHipsterLandscapeElement> elements) {
  public JHipsterLandscapeLevel(Collection<? extends JHipsterLandscapeElement> elements) {
    Assert.notEmpty("elements", elements);

    this.elements = Collections.unmodifiableCollection(elements);
  }

  public Stream<JHipsterSlug> slugs() {
    return elements.stream().flatMap(JHipsterLandscapeElement::slugs);
  }
}
