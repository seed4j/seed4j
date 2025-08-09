package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.landscape.SeedLandscapeElement;
import com.seed4j.module.domain.landscape.SeedLandscapeElementType;
import com.seed4j.module.domain.landscape.SeedLandscapeFeature;
import com.seed4j.module.domain.landscape.SeedLandscapeModule;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "JHipsterLandscapeElement", description = "An element in a landscape, can be either a module or a feature")
sealed interface RestJHipsterLandscapeElement permits RestJHipsterLandscapeModule, RestJHipsterLandscapeFeature {
  SeedLandscapeElementType getType();

  static RestJHipsterLandscapeElement from(SeedLandscapeElement element) {
    return switch (element) {
      case SeedLandscapeModule module -> RestJHipsterLandscapeModule.fromModule(module);
      case SeedLandscapeFeature feature -> RestJHipsterLandscapeFeature.fromFeature(feature);
    };
  }
}
