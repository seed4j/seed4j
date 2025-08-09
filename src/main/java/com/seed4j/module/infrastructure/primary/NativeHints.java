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
      .registerType(RestSeedModuleProperties.class, MemberCategory.values())
      .registerType(RestSeedLandscape.class, MemberCategory.values())
      .registerType(RestSeedLandscapeLevel.class, MemberCategory.values())
      .registerType(RestSeedLandscapeElement.class, MemberCategory.values())
      .registerType(RestSeedLandscapeModule.class, MemberCategory.values())
      .registerType(RestSeedLandscapeFeature.class, MemberCategory.values())
      .registerType(RestSeedLandscapeDependency.class, MemberCategory.values())
      .registerType(RestSeedModulePropertiesDefinition.class, MemberCategory.values())
      .registerType(SeedLandscapeElementType.class, MemberCategory.values());
  }
}
