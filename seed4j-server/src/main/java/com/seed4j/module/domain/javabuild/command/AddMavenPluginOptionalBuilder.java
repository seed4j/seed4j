package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.module.domain.javadependency.JavaDependencyVersion;

public interface AddMavenPluginOptionalBuilder<T extends AddMavenPlugin> {
  AddMavenPluginOptionalBuilder<T> pluginVersion(JavaDependencyVersion version);

  AddMavenPluginOptionalBuilder<T> buildProfile(BuildProfileId buildProfile);

  AddMavenPluginOptionalBuilder<T> addDependencyVersion(JavaDependencyVersion javaDependencyVersion);

  T build();
}
