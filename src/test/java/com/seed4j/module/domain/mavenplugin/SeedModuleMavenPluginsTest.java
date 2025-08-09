package com.seed4j.module.domain.mavenplugin;

import static com.seed4j.module.domain.SeedModulesFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.javabuild.command.AddDirectMavenPlugin;
import com.seed4j.module.domain.javabuild.command.AddMavenPluginManagement;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javadependency.ProjectJavaDependencies;
import org.junit.jupiter.api.Test;

@UnitTest
class SeedModuleMavenPluginsTest {

  @Test
  void shouldAddNewDependencyManagement() {
    JavaBuildCommands changes = SeedModuleMavenPlugins.builder(emptyModuleBuilder())
      .pluginManagement(mavenEnforcerPluginManagement())
      .build()
      .buildChanges(currentJavaDependenciesVersion(), ProjectJavaDependencies.EMPTY);

    assertThat(changes.get())
      .usingRecursiveFieldByFieldElementComparator()
      .containsExactly(
        AddMavenPluginManagement.builder()
          .plugin(mavenEnforcerPluginManagement())
          .pluginVersion(mavenEnforcerVersion())
          .addDependencyVersion(jsonWebTokenVersion())
          .build()
      );
  }

  @Test
  void shouldAddNewDependency() {
    JavaBuildCommands changes = SeedModuleMavenPlugins.builder(emptyModuleBuilder())
      .plugin(mavenEnforcerPluginManagement())
      .build()
      .buildChanges(currentJavaDependenciesVersion(), ProjectJavaDependencies.EMPTY);

    assertThat(changes.get())
      .usingRecursiveFieldByFieldElementComparator()
      .containsExactly(
        AddDirectMavenPlugin.builder()
          .plugin(mavenEnforcerPluginManagement())
          .pluginVersion(mavenEnforcerVersion())
          .addDependencyVersion(jsonWebTokenVersion())
          .build()
      );
  }
}
