package com.seed4j.statistic.domain;

import com.seed4j.shared.error.domain.Assert;

public record Module(String slug) {
  public Module {
    Assert.field("slug", slug).notBlank().maxLength(255);
  }
}
