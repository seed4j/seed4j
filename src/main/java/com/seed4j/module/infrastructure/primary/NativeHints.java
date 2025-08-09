package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.landscape.SeedLandscapeElementType;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

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
      .registerType(SeedLandscapeElementType.class, MemberCategory.values());
  }
}
