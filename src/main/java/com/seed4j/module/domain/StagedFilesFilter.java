package com.seed4j.module.domain;

import com.seed4j.shared.error.domain.Assert;

public record StagedFilesFilter(String filter) {
  public StagedFilesFilter {
    Assert.notBlank("filter", filter);
  }

  @Override
  public String toString() {
    return filter;
  }
}
