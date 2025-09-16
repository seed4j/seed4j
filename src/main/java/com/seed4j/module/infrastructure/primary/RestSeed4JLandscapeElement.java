package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.landscape.Seed4JLandscapeElement;
import com.seed4j.module.domain.landscape.Seed4JLandscapeElementType;
import com.seed4j.module.domain.landscape.Seed4JLandscapeFeature;
import com.seed4j.module.domain.landscape.Seed4JLandscapeModule;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Seed4JLandscapeElement", description = "An element in a landscape, can be either a module or a feature")
sealed interface RestSeed4JLandscapeElement permits RestSeed4JLandscapeModule, RestSeed4JLandscapeFeature {
  Seed4JLandscapeElementType getType();

  static RestSeed4JLandscapeElement from(Seed4JLandscapeElement element) {
    return switch (element) {
      case Seed4JLandscapeModule module -> RestSeed4JLandscapeModule.fromModule(module);
      case Seed4JLandscapeFeature feature -> RestSeed4JLandscapeFeature.fromFeature(feature);
    };
  }
}
