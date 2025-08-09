package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.landscape.SeedLandscapeElement;
import com.seed4j.module.domain.landscape.SeedLandscapeElementType;
import com.seed4j.module.domain.landscape.SeedLandscapeFeature;
import com.seed4j.module.domain.landscape.SeedLandscapeModule;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SeedLandscapeElement", description = "An element in a landscape, can be either a module or a feature")
sealed interface RestSeedLandscapeElement permits RestSeedLandscapeModule, RestSeedLandscapeFeature {
  SeedLandscapeElementType getType();

  static RestSeedLandscapeElement from(SeedLandscapeElement element) {
    return switch (element) {
      case SeedLandscapeModule module -> RestSeedLandscapeModule.fromModule(module);
      case SeedLandscapeFeature feature -> RestSeedLandscapeFeature.fromFeature(feature);
    };
  }
}
