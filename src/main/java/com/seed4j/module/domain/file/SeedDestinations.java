package com.seed4j.module.domain.file;

import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;

public record SeedDestinations(Collection<SeedDestination> destinations) {
  public SeedDestinations(Collection<SeedDestination> destinations) {
    this.destinations = SeedCollections.immutable(destinations);
  }

  public boolean doesNotContain(SeedDestination destination) {
    Assert.notNull("destination", destination);

    return !destinations().contains(destination);
  }
}
