package com.seed4j.module.domain.file;

import com.seed4j.shared.collection.domain.Seed4JCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;

public record Seed4JDestinations(Collection<Seed4JDestination> destinations) {
  public Seed4JDestinations(Collection<Seed4JDestination> destinations) {
    this.destinations = Seed4JCollections.immutable(destinations);
  }

  public boolean doesNotContain(Seed4JDestination destination) {
    Assert.notNull("destination", destination);

    return !destinations().contains(destination);
  }
}
