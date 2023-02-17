package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import tech.jhipster.lite.module.domain.javabuild.command.AddBuildPluginManagement;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaBuildPlugin;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;

public interface JavaDependenciesCommandHandler {
  void handle(SetVersion command);

  void handle(AddDirectJavaDependency command);

  void handle(RemoveDirectJavaDependency command);

  void handle(RemoveJavaDependencyManagement command);

  void handle(AddJavaDependencyManagement command);

  void handle(AddDirectJavaBuildPlugin command);

  void handle(AddBuildPluginManagement command);
}
