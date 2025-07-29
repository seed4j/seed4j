package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.landscape.JHipsterLandscapeElement;
import com.seed4j.module.domain.landscape.JHipsterLandscapeElementType;
import com.seed4j.module.domain.landscape.JHipsterLandscapeFeature;
import com.seed4j.module.domain.landscape.JHipsterLandscapeModule;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "JHipsterLandscapeElement", description = "An element in a landscape, can be either a module or a feature")
sealed interface RestJHipsterLandscapeElement permits RestJHipsterLandscapeModule, RestJHipsterLandscapeFeature {
  JHipsterLandscapeElementType getType();

  static RestJHipsterLandscapeElement from(JHipsterLandscapeElement element) {
    return switch (element) {
      case JHipsterLandscapeModule module -> RestJHipsterLandscapeModule.fromModule(module);
      case JHipsterLandscapeFeature feature -> RestJHipsterLandscapeFeature.fromFeature(feature);
    };
  }
}
