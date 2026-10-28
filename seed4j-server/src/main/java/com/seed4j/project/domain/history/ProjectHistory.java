package com.seed4j.project.domain.history;

import com.seed4j.project.domain.ModuleSlug;
import com.seed4j.shared.collection.domain.Seed4JCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;

public record ProjectHistory(Collection<ModuleSlug> modules, ModuleParameters properties) {
  public ProjectHistory {
    Assert.notNull("properties", properties);

    modules = Seed4JCollections.immutable(modules);
  }
}
