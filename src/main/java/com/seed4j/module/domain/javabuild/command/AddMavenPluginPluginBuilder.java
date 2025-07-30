package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.mavenplugin.MavenPlugin;

public interface AddMavenPluginPluginBuilder<T extends AddMavenPlugin> {
  AddMavenPluginOptionalBuilder<T> plugin(MavenPlugin plugin);
}
