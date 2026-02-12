package com.seed4j.generator.server.micronaut.shared;

import com.seed4j.module.domain.resource.Seed4JModuleRank;
import com.seed4j.module.domain.resource.Seed4JModuleSlugFactory;

public record MicronautModuleSlug(String slug) implements Seed4JModuleSlugFactory {
  @Override
  public String get() {
    return slug;
  }

  @Override
  public Seed4JModuleRank rank() {
    return Seed4JModuleRank.RANK_A;
  }
}
