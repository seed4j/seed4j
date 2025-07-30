package com.seed4j.module.domain.file;

import com.seed4j.shared.collection.domain.JHipsterCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;

public record JHipsterDestinations(Collection<JHipsterDestination> destinations) {
  public JHipsterDestinations(Collection<JHipsterDestination> destinations) {
    this.destinations = JHipsterCollections.immutable(destinations);
  }

  public boolean doesNotContain(JHipsterDestination destination) {
    Assert.notNull("destination", destination);

    return !destinations().contains(destination);
  }
}
