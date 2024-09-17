package tech.jhipster.lite.module.domain.npm;

import tech.jhipster.lite.shared.error.domain.Assert;

public record NpmVersionSource(String name) {
  public NpmVersionSource {
    Assert.notBlank("name", name);
  }

  @Override
  public String toString() {
    return name;
  }
}
