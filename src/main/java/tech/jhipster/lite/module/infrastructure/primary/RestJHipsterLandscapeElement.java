package tech.jhipster.lite.module.infrastructure.primary;

import io.swagger.v3.oas.annotations.media.Schema;
import tech.jhipster.lite.module.domain.landscape.JHipsterLandscapeElement;
import tech.jhipster.lite.module.domain.landscape.JHipsterLandscapeElementType;
import tech.jhipster.lite.module.domain.landscape.JHipsterLandscapeFeature;
import tech.jhipster.lite.module.domain.landscape.JHipsterLandscapeModule;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@Schema(name = "JHipsterLandscapeElement", description = "An element in a landscape, can be either a module or a feature")
sealed interface RestJHipsterLandscapeElement permits RestJHipsterLandscapeModule, RestJHipsterLandscapeFeature {
  JHipsterLandscapeElementType getType();

  @ExcludeFromGeneratedCodeCoverage(reason = "Jacoco think there is a missed case here")
  static RestJHipsterLandscapeElement from(JHipsterLandscapeElement element) {
    return switch (element.type()) {
      case MODULE -> RestJHipsterLandscapeModule.fromModule((JHipsterLandscapeModule) element);
      case FEATURE -> RestJHipsterLandscapeFeature.fromFeature((JHipsterLandscapeFeature) element);
    };
  }
}
