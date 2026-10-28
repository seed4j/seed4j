package com.seed4j.module.domain.javabuild.command;

public sealed interface JavaBuildCommand
  permits
    AddDirectJavaDependency,
    AddDirectMavenPlugin,
    AddGradleConfiguration,
    AddGradlePlugin,
    AddGradleTasksTestInstruction,
    AddJavaAnnotationProcessor,
    AddJavaBuildProfile,
    AddJavaDependencyManagement,
    AddMavenBuildExtension,
    AddMavenPluginManagement,
    RemoveDirectJavaDependency,
    RemoveJavaAnnotationProcessor,
    RemoveJavaDependencyManagement,
    SetBuildProperty,
    SetVersion {}
