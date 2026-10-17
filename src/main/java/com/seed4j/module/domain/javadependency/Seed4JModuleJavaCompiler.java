package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuild.command.RemoveJavaAnnotationProcessor;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public final class Seed4JModuleJavaCompiler {

  private final Collection<JavaAnnotationProcessorDependency> annotationProcessingDependencies;
  private final Collection<DependencyId> annotationProcessingDependenciesToRemove;

  private Seed4JModuleJavaCompiler(Seed4JModuleJavaCompilerBuilder<?> builder) {
    annotationProcessingDependencies = builder.annotationProcessingDependencies;
    annotationProcessingDependenciesToRemove = builder.annotationProcessingDependenciesToRemove;
  }

  public static <M> Seed4JModuleJavaCompilerBuilder<M> builder(M module) {
    return new Seed4JModuleJavaCompilerBuilder<>(module);
  }

  public JavaBuildCommands buildChanges(JavaDependenciesVersions versions, ProjectJavaDependencies projectDependencies) {
    Assert.notNull("versions", versions);
    Assert.notNull("projectDependencies", projectDependencies);

    return Stream.of(
      annotationProcessingDependenciesToRemoveCommands(),
      annotationProcessingDependenciesChanges(versions, projectDependencies)
    )
      .flatMap(Function.identity())
      .reduce(JavaBuildCommands.EMPTY, JavaBuildCommands::merge);
  }

  private Stream<JavaBuildCommands> annotationProcessingDependenciesToRemoveCommands() {
    return Stream.of(
      new JavaBuildCommands(annotationProcessingDependenciesToRemove.stream().map(RemoveJavaAnnotationProcessor::new).toList())
    );
  }

  private Stream<JavaBuildCommands> annotationProcessingDependenciesChanges(
    JavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies
  ) {
    return annotationProcessingDependencies.stream().map(dependency -> dependency.changeCommands(currentVersions, projectDependencies));
  }

  public static final class Seed4JModuleJavaCompilerBuilder<T> {

    private static final String DEPENDENCY = "dependency";

    private final T parentModuleBuilder;
    private final Collection<JavaAnnotationProcessorDependency> annotationProcessingDependencies = new ArrayList<>();
    private final Collection<DependencyId> annotationProcessingDependenciesToRemove = new ArrayList<>();

    private Seed4JModuleJavaCompilerBuilder(T parentModuleBuilder) {
      Assert.notNull("module", parentModuleBuilder);

      this.parentModuleBuilder = parentModuleBuilder;
    }

    public Seed4JModuleJavaCompilerBuilder<T> addAnnotationProcessor(JavaAnnotationProcessorDependency dependency) {
      Assert.notNull(DEPENDENCY, dependency);

      annotationProcessingDependencies.add(dependency);

      return this;
    }

    public Seed4JModuleJavaCompilerBuilder<T> removeAnnotationProcessor(DependencyId dependency) {
      Assert.notNull(DEPENDENCY, dependency);

      annotationProcessingDependenciesToRemove.add(dependency);

      return this;
    }

    public T and() {
      return parentModuleBuilder;
    }

    public Seed4JModuleJavaCompiler build() {
      return new Seed4JModuleJavaCompiler(this);
    }
  }
}
