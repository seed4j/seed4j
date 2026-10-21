package com.seed4j.generator.server.micronaut.common.domain;

import static com.seed4j.module.domain.resource.Seed4JModuleRank.RANK_D;

import com.seed4j.module.domain.resource.Seed4JModuleRank;
import com.seed4j.module.domain.resource.Seed4JModuleSlugFactory;

public enum MicronautModuleSlug implements Seed4JModuleSlugFactory {
  MICRONAUT("micronaut"),
  MICRONAUT_CACHE_CAFFEINE("micronaut-cache-caffeine"),
  MICRONAUT_CACHE_REDIS("micronaut-cache-redis"),
  MICRONAUT_FLYWAY("micronaut-flyway"),
  MICRONAUT_JPA_MARIADB("micronaut-jpa-mariadb"),
  MICRONAUT_JPA_MSSQL("micronaut-jpa-mssql"),
  MICRONAUT_JPA_MYSQL("micronaut-jpa-mysql"),
  MICRONAUT_JPA_POSTGRESQL("micronaut-jpa-postgresql"),
  MICRONAUT_JWT("micronaut-jwt"),
  MICRONAUT_LIQUIBASE("micronaut-liquibase"),
  MICRONAUT_MANAGEMENT("micronaut-management"),
  MICRONAUT_OPENAPI("micronaut-openapi"),
  MICRONAUT_SAMPLE_FEATURE("micronaut-sample-feature");

  private final String slug;

  MicronautModuleSlug(String slug) {
    this.slug = slug;
  }

  @Override
  public String get() {
    return slug;
  }

  @Override
  public Seed4JModuleRank rank() {
    // Micronaut support is new: modules will be promoted as they get used and stabilized
    return RANK_D;
  }
}
