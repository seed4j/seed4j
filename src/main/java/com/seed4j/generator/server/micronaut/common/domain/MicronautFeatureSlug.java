package com.seed4j.generator.server.micronaut.common.domain;

import com.seed4j.module.domain.resource.Seed4JFeatureSlugFactory;

public enum MicronautFeatureSlug implements Seed4JFeatureSlugFactory {
  MICRONAUT_CACHE("micronaut-cache"),
  MICRONAUT_DATABASE_MIGRATION("micronaut-database-migration"),
  MICRONAUT_JPA_PERSISTENCE("micronaut-jpa-persistence");

  private final String slug;

  MicronautFeatureSlug(String slug) {
    this.slug = slug;
  }

  @Override
  public String get() {
    return slug;
  }
}
