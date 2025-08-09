package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.SeedModuleSlug;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ModuleToApply", description = "Information for a module to apply")
record RestModuleToApply(@Schema(description = "Slug of the module to apply") String slug) {
  public static RestModuleToApply from(SeedModuleSlug moduleSlug) {
    return new RestModuleToApply(moduleSlug.get());
  }
}
