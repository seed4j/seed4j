package com.seed4j.module.infrastructure.primary;

import com.seed4j.module.domain.landscape.Seed4JLandscapeElementType;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import org.jspecify.annotations.Nullable;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

@ExcludeFromGeneratedCodeCoverage(reason = "Not testing native runtime hints")
class NativeHints implements RuntimeHintsRegistrar {

  @Override
  public void registerHints(RuntimeHints hints, @Nullable ClassLoader classLoader) {
    hints
      .reflection()
      .registerType(RestSeed4JModuleProperties.class, MemberCategory.values())
      .registerType(RestSeed4JLandscape.class, MemberCategory.values())
      .registerType(RestSeed4JLandscapeLevel.class, MemberCategory.values())
      .registerType(RestSeed4JLandscapeElement.class, MemberCategory.values())
      .registerType(RestSeed4JLandscapeModule.class, MemberCategory.values())
      .registerType(RestSeed4JLandscapeFeature.class, MemberCategory.values())
      .registerType(RestSeed4JLandscapeDependency.class, MemberCategory.values())
      .registerType(RestSeed4JModulePropertiesDefinition.class, MemberCategory.values())
      .registerType(Seed4JLandscapeElementType.class, MemberCategory.values());
  }
}
