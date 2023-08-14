package tech.jhipster.lite.project.domain.history;

import tech.jhipster.lite.shared.error.domain.Assert;

public record ModuleSlug(String slug) {
  public ModuleSlug {
    Assert.notBlank("slug", slug);
  }

  public String get() {
    return slug();
  }
}
