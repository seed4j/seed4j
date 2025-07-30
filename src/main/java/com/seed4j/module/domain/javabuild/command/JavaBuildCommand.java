package com.seed4j.module.domain.javabuild.command;

public sealed interface JavaBuildCommand
  permits
    AddMavenPluginManagement,
    AddDirectMavenPlugin,
    AddDirectJavaDependency,
    AddGradlePlugin,
    AddJavaBuildProfile,
    AddJavaDependencyManagement,
    AddMavenBuildExtension,
    RemoveDirectJavaDependency,
    RemoveJavaDependencyManagement,
    SetBuildProperty,
    SetVersion,
    AddGradleConfiguration,
    AddGradleTasksTestInstruction {}
