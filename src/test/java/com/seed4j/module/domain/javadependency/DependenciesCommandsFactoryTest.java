package com.seed4j.module.domain.javadependency;

import static com.seed4j.module.domain.SeedModulesFixture.localBuildProfile;
import static com.seed4j.module.domain.SeedModulesFixture.springBootDependencyId;
import static com.seed4j.module.domain.SeedModulesFixture.springBootStarterWebDependency;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.javabuild.command.AddDirectJavaDependency;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuild.command.RemoveDirectJavaDependency;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class DependenciesCommandsFactoryTest {

  private final DependenciesCommandsFactory commandsFactory = DependenciesCommandsFactory.DIRECT;

  @Nested
  class AddDependency {

    @Test
    void shouldAddDependencyWithoutProfile() {
      JavaBuildCommand buildCommand = commandsFactory.addDependency(springBootStarterWebDependency(), Optional.empty());

      assertThat(buildCommand).isEqualTo(new AddDirectJavaDependency(springBootStarterWebDependency()));
    }

    @Test
    void shouldAddDependencyWithProfile() {
      JavaBuildCommand buildCommand = commandsFactory.addDependency(springBootStarterWebDependency(), Optional.of(localBuildProfile()));

      assertThat(buildCommand).isEqualTo(new AddDirectJavaDependency(springBootStarterWebDependency(), localBuildProfile()));
    }
  }

  @Nested
  class RemoveDependency {

    @Test
    void shouldRemoveDependencyWithoutProfile() {
      JavaBuildCommand buildCommand = commandsFactory.removeDependency(springBootDependencyId(), Optional.empty());

      assertThat(buildCommand).isEqualTo(new RemoveDirectJavaDependency(springBootDependencyId()));
    }

    @Test
    void shouldRemoveDependencyWithProfile() {
      JavaBuildCommand buildCommand = commandsFactory.removeDependency(springBootDependencyId(), Optional.of(localBuildProfile()));

      assertThat(buildCommand).isEqualTo(new RemoveDirectJavaDependency(springBootDependencyId(), localBuildProfile()));
    }
  }
}
