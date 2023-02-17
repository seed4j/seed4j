package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.common.domain.ExcludeFromGeneratedCodeCoverage;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.javabuild.command.AddBuildPluginManagement;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaBuildPlugin;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommand;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.infrastructure.secondary.javadependency.gradle.GradleCommandHandler;
import tech.jhipster.lite.module.infrastructure.secondary.javadependency.maven.MavenCommandHandler;

@Service
public class FileSystemJavaBuildCommandsHandler {

  public void handle(Indentation indentation, JHipsterProjectFolder projectFolder, JavaBuildCommands commands) {
    Assert.notNull("indentation", indentation);
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("commands", commands);

    if (commands.isEmpty()) {
      return;
    }

    JavaDependenciesCommandHandler handler = buildCommandHandler(indentation, projectFolder);

    commands.get().forEach(command -> handle(handler, command));
  }

  private static JavaDependenciesCommandHandler buildCommandHandler(Indentation indentation, JHipsterProjectFolder projectFolder) {
    Path pomPath = projectFolder.filePath("pom.xml");
    if (Files.exists(pomPath)) {
      return new MavenCommandHandler(indentation, pomPath);
    }
    if (Files.exists(projectFolder.filePath("build.gradle.kts"))) {
      return new GradleCommandHandler(indentation, projectFolder);
    }
    throw new MissingJavaBuildConfigurationException(projectFolder);
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Jacoco thinks there is a missed branch")
  private void handle(JavaDependenciesCommandHandler handler, JavaBuildCommand command) {
    switch (command.type()) {
      case SET_VERSION -> handler.handle((SetVersion) command);
      case REMOVE_DEPENDENCY_MANAGEMENT -> handler.handle((RemoveJavaDependencyManagement) command);
      case ADD_DEPENDENCY_MANAGEMENT -> handler.handle((AddJavaDependencyManagement) command);
      case REMOVE_DEPENDENCY -> handler.handle((RemoveDirectJavaDependency) command);
      case ADD_DEPENDENCY -> handler.handle((AddDirectJavaDependency) command);
      case ADD_DIRECT_JAVA_BUILD_PLUGIN -> handler.handle((AddDirectJavaBuildPlugin) command);
      case ADD_JAVA_BUILD_PLUGIN_MANAGEMENT -> handler.handle((AddBuildPluginManagement) command);
    }
  }
}
