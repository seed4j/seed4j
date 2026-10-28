package com.seed4j.module.domain.gradleplugin;

import com.seed4j.shared.error.domain.Assert;

public record GradlePluginId(String id) {
  public GradlePluginId {
    Assert.notNull("id", id);
  }

  public String get() {
    return id();
  }
}
