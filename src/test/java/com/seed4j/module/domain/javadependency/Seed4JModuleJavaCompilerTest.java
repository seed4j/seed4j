package com.seed4j.module.domain.javadependency;

import static com.seed4j.module.domain.Seed4JModulesFixture.currentJavaDependenciesVersion;
import static com.seed4j.module.domain.Seed4JModulesFixture.googleAutoServiceAnnotationProcessor;
import static com.seed4j.module.domain.Seed4JModulesFixture.hibernateAnnotationProcessor;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.javabuild.command.AddJavaAnnotationProcessor;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuild.command.RemoveJavaAnnotationProcessor;
import org.junit.jupiter.api.Test;

@UnitTest
class Seed4JModuleJavaCompilerTest {

  @Test
  void shouldBuildEmptyChangesWhenNoAnnotationProcessors() {
    JavaBuildCommands commands = Seed4JModuleJavaCompiler.builder(new Object())
      .build()
      .buildChanges(currentJavaDependenciesVersion(), ProjectJavaDependencies.EMPTY);

    assertThat(commands.get()).isEmpty();
  }

  @Test
  void shouldBuildAddAnnotationProcessorCommand() {
    JavaBuildCommands commands = Seed4JModuleJavaCompiler.builder(new Object())
      .addAnnotationProcessor(hibernateAnnotationProcessor())
      .build()
      .buildChanges(currentJavaDependenciesVersion(), ProjectJavaDependencies.EMPTY);

    assertThat(commands.get()).containsExactly(new AddJavaAnnotationProcessor(hibernateAnnotationProcessor()));
  }

  @Test
  void shouldBuildRemoveAnnotationProcessorCommand() {
    JavaBuildCommands commands = Seed4JModuleJavaCompiler.builder(new Object())
      .removeAnnotationProcessor(googleAutoServiceAnnotationProcessor().id())
      .build()
      .buildChanges(currentJavaDependenciesVersion(), ProjectJavaDependencies.EMPTY);

    assertThat(commands.get()).containsExactly(new RemoveJavaAnnotationProcessor(googleAutoServiceAnnotationProcessor().id()));
  }
}
