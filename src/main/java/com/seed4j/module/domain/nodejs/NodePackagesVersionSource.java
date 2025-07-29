package com.seed4j.module.domain.nodejs;

import com.seed4j.shared.error.domain.Assert;

public record NodePackagesVersionSource(String name) {
  public NodePackagesVersionSource {
    Assert.notBlank("name", name);
  }

  @Override
  public String toString() {
    return name;
  }
}
