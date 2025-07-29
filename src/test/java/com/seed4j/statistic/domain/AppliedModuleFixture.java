package com.seed4j.statistic.domain;

import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public final class AppliedModuleFixture {

  private AppliedModuleFixture() {}

  public static AppliedModule appliedModule() {
    return appliedModule(null);
  }

  public static AppliedModule appliedModule(String slug) {
    return AppliedModule.builder()
      .id(appliedModuleId())
      .path(folder())
      .module(moduleSlug(slug))
      .date(Instant.parse("2021-12-03T10:15:30.00Z"))
      .properties(moduleProperties());
  }

  private static AppliedModuleId appliedModuleId() {
    return new AppliedModuleId(UUID.fromString("065b2280-d0bd-4bea-b685-1a899f49fba7"));
  }

  private static ProjectPath folder() {
    return new ProjectPath("path");
  }

  private static Module moduleSlug(String slug) {
    return JHLiteModuleSlug.fromString(slug).map(JHLiteModuleSlug::get).map(Module::new).orElse(new Module("module"));
  }

  private static ModuleProperties moduleProperties() {
    return new ModuleProperties(Map.of("key", "value"));
  }
}
