package com.seed4j.module.domain;

public final class JHipsterModuleSlug extends JHipsterSlug {

  public JHipsterModuleSlug(String slug) {
    super(slug);
  }

  @Override
  public String toString() {
    return get();
  }
}
