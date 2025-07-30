package com.seed4j.module.domain.gradleplugin;

import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.function.Consumer;

public record GradlePluginImports(Collection<BuildGradleImport> pluginImports) {
  public GradlePluginImports {
    Assert.field("pluginImports", pluginImports).notNull().noNullElement();
  }

  public void forEach(Consumer<BuildGradleImport> consumer) {
    Assert.notNull("consumer", consumer);

    pluginImports.forEach(consumer);
  }
}
