package com.seed4j.module.domain.gradleplugin;

import java.util.Optional;

public sealed interface GradlePlugin permits GradleMainBuildPlugin, GradleProfilePlugin {
  GradlePluginId id();
  GradlePluginImports imports();
  Optional<GradlePluginConfiguration> configuration();
}
