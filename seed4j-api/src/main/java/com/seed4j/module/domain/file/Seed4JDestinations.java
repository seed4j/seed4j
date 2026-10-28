package com.seed4j.module.domain.file;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.List;

public record Seed4JDestinations(Collection<Seed4JDestination> destinations) {
  public Seed4JDestinations(Collection<Seed4JDestination> destinations) {
    Assert.field("destinations", destinations).notNull().noNullElement();

    this.destinations = List.copyOf(destinations);
  }

  public boolean doesNotContain(Seed4JDestination destination) {
    Assert.notNull("destination", destination);

    return !destinations().contains(destination);
  }
}
