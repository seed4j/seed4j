package com.seed4j.project.domain;

import com.seed4j.shared.error.domain.Assert;

public record ModuleSlug(String slug) {
  public ModuleSlug {
    Assert.notBlank("slug", slug);
  }

  public String get() {
    return slug();
  }
}
