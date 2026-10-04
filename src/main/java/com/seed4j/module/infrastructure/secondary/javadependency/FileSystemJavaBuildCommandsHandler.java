package com.seed4j.module.infrastructure.secondary.javadependency;

import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.Seed4JModuleContext;
import com.seed4j.module.domain.javabuild.JavaBuildTool;
import com.seed4j.module.domain.javabuild.ProjectJavaBuildToolRepository;
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
import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuild.command.RemoveDirectJavaDependency;
import com.seed4j.module.domain.javabuild.command.RemoveJavaAnnotationProcessor;
import com.seed4j.module.domain.javabuild.command.RemoveJavaDependencyManagement;
import com.seed4j.module.domain.javabuild.command.SetBuildProperty;
import com.seed4j.module.domain.javabuild.command.SetVersion;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.module.infrastructure.secondary.FileSystemReplacer;
import com.seed4j.module.infrastructure.secondary.FileSystemSeed4JModuleFiles;
import com.seed4j.module.infrastructure.secondary.javadependency.gradle.GradleCommandHandler;
import com.seed4j.module.infrastructure.secondary.javadependency.maven.MavenCommandHandler;
import com.seed4j.shared.error.domain.Assert;
import org.springframework.stereotype.Service;

@Service
public class FileSystemJavaBuildCommandsHandler {

  private final ProjectJavaBuildToolRepository javaBuildTools;
  private final FileSystemSeed4JModuleFiles files;
  private final FileSystemReplacer fileReplacer;

  public FileSystemJavaBuildCommandsHandler(
    ProjectJavaBuildToolRepository javaBuildTools,
    FileSystemSeed4JModuleFiles files,
    FileSystemReplacer fileReplacer
  ) {
    this.javaBuildTools = javaBuildTools;
    this.files = files;
    this.fileReplacer = fileReplacer;
  }

  public void handle(Indentation indentation, Seed4JProjectFolder projectFolder, Seed4JModuleContext context, JavaBuildCommands commands) {
    Assert.notNull("indentation", indentation);
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("context", context);
    Assert.notNull("commands", commands);

    if (commands.isEmpty()) {
      return;
    }

    JavaDependenciesCommandHandler handler = buildCommandHandler(indentation, projectFolder, context);

    commands.get().forEach(command -> handle(handler, command));
  }

  private JavaDependenciesCommandHandler buildCommandHandler(
    Indentation indentation,
    Seed4JProjectFolder projectFolder,
    Seed4JModuleContext context
  ) {
    JavaBuildTool javaBuildTool = javaBuildTools
      .detect(projectFolder)
      .orElseThrow(() -> new MissingJavaBuildConfigurationException(projectFolder));
    return switch (javaBuildTool) {
      case MAVEN -> new MavenCommandHandler(indentation, projectFolder.filePath("pom.xml"));
      case GRADLE -> new GradleCommandHandler(indentation, projectFolder, context, files, fileReplacer);
    };
  }

  private void handle(JavaDependenciesCommandHandler handler, JavaBuildCommand command) {
    switch (command) {
      case SetVersion setVersion -> handler.handle(setVersion);
      case SetBuildProperty setBuildProperty -> handler.handle(setBuildProperty);
      case RemoveJavaDependencyManagement removeJavaDependencyManagement -> handler.handle(removeJavaDependencyManagement);
      case AddJavaDependencyManagement addJavaDependencyManagement -> handler.handle(addJavaDependencyManagement);
      case RemoveDirectJavaDependency removeDirectJavaDependency -> handler.handle(removeDirectJavaDependency);
      case AddDirectJavaDependency addDirectJavaDependency -> handler.handle(addDirectJavaDependency);
      case AddDirectMavenPlugin addDirectMavenPlugin -> handler.handle(addDirectMavenPlugin);
      case AddMavenPluginManagement addMavenPluginManagement -> handler.handle(addMavenPluginManagement);
      case AddMavenBuildExtension addMavenBuildExtension -> handler.handle(addMavenBuildExtension);
      case AddJavaBuildProfile addJavaBuildProfile -> handler.handle(addJavaBuildProfile);
      case AddGradlePlugin addGradlePlugin -> handler.handle(addGradlePlugin);
      case AddGradleConfiguration addGradleConfiguration -> handler.handle(addGradleConfiguration);
      case AddGradleTasksTestInstruction addGradleTasksTestInstruction -> handler.handle(addGradleTasksTestInstruction);
      case AddJavaAnnotationProcessor addJavaAnnotationProcessor -> handler.handle(addJavaAnnotationProcessor);
      case RemoveJavaAnnotationProcessor removeJavaAnnotationProcessor -> handler.handle(removeJavaAnnotationProcessor);
    }
  }
}
