package com.seed4j.module.infrastructure.secondary.javadependency;

import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.SeedModuleContext;
import com.seed4j.module.domain.javabuild.JavaBuildTool;
import com.seed4j.module.domain.javabuild.ProjectJavaBuildToolRepository;
import com.seed4j.module.domain.javabuild.command.*;
import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.module.infrastructure.secondary.FileSystemJHipsterModuleFiles;
import com.seed4j.module.infrastructure.secondary.FileSystemReplacer;
import com.seed4j.module.infrastructure.secondary.javadependency.gradle.GradleCommandHandler;
import com.seed4j.module.infrastructure.secondary.javadependency.maven.MavenCommandHandler;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import org.springframework.stereotype.Service;

@Service
public class FileSystemJavaBuildCommandsHandler {

  private final ProjectJavaBuildToolRepository javaBuildTools;
  private final FileSystemJHipsterModuleFiles files;
  private final FileSystemReplacer fileReplacer;

  public FileSystemJavaBuildCommandsHandler(
    ProjectJavaBuildToolRepository javaBuildTools,
    FileSystemJHipsterModuleFiles files,
    FileSystemReplacer fileReplacer
  ) {
    this.javaBuildTools = javaBuildTools;
    this.files = files;
    this.fileReplacer = fileReplacer;
  }

  public void handle(Indentation indentation, JHipsterProjectFolder projectFolder, SeedModuleContext context, JavaBuildCommands commands) {
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
    JHipsterProjectFolder projectFolder,
    SeedModuleContext context
  ) {
    JavaBuildTool javaBuildTool = javaBuildTools
      .detect(projectFolder)
      .orElseThrow(() -> new MissingJavaBuildConfigurationException(projectFolder));
    return switch (javaBuildTool) {
      case MAVEN -> new MavenCommandHandler(indentation, projectFolder.filePath("pom.xml"));
      case GRADLE -> new GradleCommandHandler(indentation, projectFolder, context, files, fileReplacer);
    };
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Jacoco thinks there is a missed branch")
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
    }
  }
}
