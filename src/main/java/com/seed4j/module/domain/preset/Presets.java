package com.seed4j.module.domain.preset;

import com.seed4j.shared.collection.domain.JHipsterCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Stream;

public record Presets(Collection<Preset> presets) {
  public Presets(Collection<Preset> presets) {
    Assert.notNull("presets", presets);

    this.presets = JHipsterCollections.immutable(sortByAlphabeticalOrder(presets));
  }

  private static Collection<Preset> sortByAlphabeticalOrder(Collection<Preset> presets) {
    return new ArrayList<>(presets).stream().sorted(alphabeticalOrder()).toList();
  }

  private static Comparator<Preset> alphabeticalOrder() {
    return Comparator.comparing(preset -> preset.name().name());
  }

  public Stream<Preset> stream() {
    return presets.stream();
  }
}
