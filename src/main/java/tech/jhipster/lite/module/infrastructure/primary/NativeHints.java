package tech.jhipster.lite.module.infrastructure.primary;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import tech.jhipster.lite.module.domain.landscape.JHipsterLandscapeElementType;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@ExcludeFromGeneratedCodeCoverage(reason = "Not testing native runtime hints")
class NativeHints implements RuntimeHintsRegistrar {

  @Override
  public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
    hints
      .reflection()
      .registerType(RestJHipsterModuleProperties.class, MemberCategory.values())
      .registerType(RestJHipsterLandscape.class, MemberCategory.values())
      .registerType(RestJHipsterLandscapeLevel.class, MemberCategory.values())
      .registerType(RestJHipsterLandscapeElement.class, MemberCategory.values())
      .registerType(RestJHipsterLandscapeModule.class, MemberCategory.values())
      .registerType(RestJHipsterLandscapeFeature.class, MemberCategory.values())
      .registerType(RestJHipsterLandscapeDependency.class, MemberCategory.values())
      .registerType(RestJHipsterModulePropertiesDefinition.class, MemberCategory.values())
      .registerType(JHipsterLandscapeElementType.class, MemberCategory.values());
  }
}
