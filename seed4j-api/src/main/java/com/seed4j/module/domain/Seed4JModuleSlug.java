package com.seed4j.module.domain;

public final class Seed4JModuleSlug extends Seed4JSlug {

  public Seed4JModuleSlug(String slug) {
    super(slug);
  }

  @Override
  public String toString() {
    return get();
  }
}
