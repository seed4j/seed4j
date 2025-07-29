package com.seed4j.module.domain.gradleplugin;

import com.seed4j.shared.error.domain.Assert;

public record GradlePluginSlug(String slug) {
  public GradlePluginSlug {
    Assert.notBlank("slug", slug);
  }

  public String get() {
    return slug();
  }
}
