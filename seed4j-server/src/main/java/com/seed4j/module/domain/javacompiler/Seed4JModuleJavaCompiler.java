package com.seed4j.module.domain.javacompiler;

import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuild.command.RemoveJavaAnnotationProcessor;
import com.seed4j.module.domain.javadependency.DependencyId;
import com.seed4j.module.domain.javadependency.JavaAnnotationProcessor;
import com.seed4j.module.domain.javadependency.JavaDependenciesVersions;
import com.seed4j.module.domain.javadependency.ProjectJavaDependencies;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public final class Seed4JModuleJavaCompiler {

  private final Collection<JavaAnnotationProcessor> annotationProcessors;
  private final Collection<DependencyId> annotationProcessorsToRemove;

  private Seed4JModuleJavaCompiler(Seed4JModuleJavaCompilerBuilder<?> builder) {
    annotationProcessors = builder.annotationProcessors;
    annotationProcessorsToRemove = builder.annotationProcessorsToRemove;
  }

  public static <M> Seed4JModuleJavaCompilerBuilder<M> builder(M module) {
    return new Seed4JModuleJavaCompilerBuilder<>(module);
  }

  public JavaBuildCommands buildChanges(JavaDependenciesVersions versions, ProjectJavaDependencies projectDependencies) {
    Assert.notNull("versions", versions);
    Assert.notNull("projectDependencies", projectDependencies);

    return Stream.of(annotationProcessorsToRemoveCommands(), annotationProcessorsChanges(versions, projectDependencies))
      .flatMap(Function.identity())
      .reduce(JavaBuildCommands.EMPTY, JavaBuildCommands::merge);
  }

  private Stream<JavaBuildCommands> annotationProcessorsToRemoveCommands() {
    return Stream.of(new JavaBuildCommands(annotationProcessorsToRemove.stream().map(RemoveJavaAnnotationProcessor::new).toList()));
  }

  private Stream<JavaBuildCommands> annotationProcessorsChanges(
    JavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies
  ) {
    return annotationProcessors
      .stream()
      .map(annotationProcessor -> annotationProcessor.changeCommands(currentVersions, projectDependencies));
  }

  public static final class Seed4JModuleJavaCompilerBuilder<T> {

    private final T parentModuleBuilder;
    private final Collection<JavaAnnotationProcessor> annotationProcessors = new ArrayList<>();
    private final Collection<DependencyId> annotationProcessorsToRemove = new ArrayList<>();

    private Seed4JModuleJavaCompilerBuilder(T parentModuleBuilder) {
      Assert.notNull("module", parentModuleBuilder);

      this.parentModuleBuilder = parentModuleBuilder;
    }

    public Seed4JModuleJavaCompilerBuilder<T> addAnnotationProcessor(JavaAnnotationProcessor annotationProcessor) {
      Assert.notNull("annotationProcessor", annotationProcessor);

      annotationProcessors.add(annotationProcessor);

      return this;
    }

    public Seed4JModuleJavaCompilerBuilder<T> removeAnnotationProcessor(DependencyId annotationProcessor) {
      Assert.notNull("annotationProcessor", annotationProcessor);

      annotationProcessorsToRemove.add(annotationProcessor);

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
