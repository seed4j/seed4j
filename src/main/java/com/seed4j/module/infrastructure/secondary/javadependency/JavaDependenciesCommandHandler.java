package com.seed4j.module.infrastructure.secondary.javadependency;

import com.seed4j.module.domain.javabuild.command.*;

public interface JavaDependenciesCommandHandler {
  void handle(SetVersion command);

  void handle(AddDirectJavaDependency command);

  void handle(RemoveDirectJavaDependency command);

  void handle(RemoveJavaDependencyManagement command);

  void handle(AddJavaDependencyManagement command);

  void handle(AddDirectMavenPlugin command);

  void handle(AddMavenPluginManagement command);

  void handle(AddMavenBuildExtension command);

  void handle(SetBuildProperty command);

  void handle(AddJavaBuildProfile command);

  void handle(AddGradlePlugin command);

  void handle(AddGradleConfiguration command);

  void handle(AddGradleTasksTestInstruction command);
}
