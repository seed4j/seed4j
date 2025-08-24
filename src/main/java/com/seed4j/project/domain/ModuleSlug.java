package com.seed4j.project.domain;

import com.seed4j.shared.error.domain.Assert;

public record ModuleSlug(String slug) {
  public ModuleSlug {
    Assert.field("slug", slug).notBlank().maxLength(255);
  }

  public String get() {
    return slug();
  }
}
