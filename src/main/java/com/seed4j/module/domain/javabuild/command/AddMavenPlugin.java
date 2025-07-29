package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javadependency.DependencyId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyVersion;
import com.seed4j.module.domain.mavenplugin.MavenPlugin;
import com.seed4j.module.domain.mavenplugin.MavenPluginConfiguration;
import com.seed4j.module.domain.mavenplugin.MavenPluginExecution;
import java.util.Collection;
import java.util.Optional;

public interface AddMavenPlugin {
  MavenPlugin plugin();

  default Optional<VersionSlug> versionSlug() {
    return plugin().versionSlug();
  }

  default Optional<MavenPluginConfiguration> configuration() {
    return plugin().configuration();
  }

  default Collection<MavenPluginExecution> executions() {
    return plugin().executions();
  }

  default Collection<JavaDependency> dependencies() {
    return plugin().dependencies();
  }

  default DependencyId dependencyId() {
    return plugin().dependencyId();
  }

  Optional<JavaDependencyVersion> pluginVersion();
}
