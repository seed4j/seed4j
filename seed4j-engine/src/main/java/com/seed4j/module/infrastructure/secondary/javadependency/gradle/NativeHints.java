package com.seed4j.module.infrastructure.secondary.javadependency.gradle;

import com.electronwill.nightconfig.toml.TomlFormat;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import org.jspecify.annotations.Nullable;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

@ExcludeFromGeneratedCodeCoverage(reason = "Not testing native runtime hints")
class NativeHints implements RuntimeHintsRegistrar {

  @Override
  public void registerHints(RuntimeHints hints, @Nullable ClassLoader classLoader) {
    hints.reflection().registerType(TomlFormat.class, MemberCategory.values());
  }
}
