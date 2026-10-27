package com.seed4j.module.domain.preset;

import com.seed4j.shared.error.domain.Assert;

public record PresetName(String name) {
  public PresetName {
    Assert.notBlank("name", name);
  }

  public static PresetName from(String name) {
    return new PresetName(name);
  }
}
