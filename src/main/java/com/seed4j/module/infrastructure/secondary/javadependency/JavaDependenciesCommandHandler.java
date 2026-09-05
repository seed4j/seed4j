package com.seed4j.module.infrastructure.secondary.javadependency;

import com.seed4j.module.domain.javabuild.command.AddDirectJavaDependency;
import com.seed4j.module.domain.javabuild.command.AddDirectMavenPlugin;
import com.seed4j.module.domain.javabuild.command.AddGradleConfiguration;
import com.seed4j.module.domain.javabuild.command.AddGradlePlugin;
import com.seed4j.module.domain.javabuild.command.AddGradleTasksTestInstruction;
import com.seed4j.module.domain.javabuild.command.AddJavaAnnotationProcessor;
import com.seed4j.module.domain.javabuild.command.AddJavaBuildProfile;
import com.seed4j.module.domain.javabuild.command.AddJavaDependencyManagement;
import com.seed4j.module.domain.javabuild.command.AddMavenBuildExtension;
import com.seed4j.module.domain.javabuild.command.AddMavenPluginManagement;
import com.seed4j.module.domain.javabuild.command.RemoveDirectJavaDependency;
import com.seed4j.module.domain.javabuild.command.RemoveJavaAnnotationProcessor;
import com.seed4j.module.domain.javabuild.command.RemoveJavaDependencyManagement;
import com.seed4j.module.domain.javabuild.command.SetBuildProperty;
import com.seed4j.module.domain.javabuild.command.SetVersion;

public interface JavaDependenciesCommandHandler {
  void handle(SetVersion command);

  void handle(AddDirectJavaDependency command);

  void handle(RemoveDirectJavaDependency command);

  void handle(RemoveJavaDependencyManagement command);

  void handle(RemoveJavaAnnotationProcessor command);

  void handle(AddJavaDependencyManagement command);

  void handle(AddDirectMavenPlugin command);

  void handle(AddJavaAnnotationProcessor command);

  void handle(AddMavenPluginManagement command);

  void handle(AddMavenBuildExtension command);

  void handle(SetBuildProperty command);

  void handle(AddJavaBuildProfile command);

  void handle(AddGradlePlugin command);

  void handle(AddGradleConfiguration command);

  void handle(AddGradleTasksTestInstruction command);
}
