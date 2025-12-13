package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.javabuild.command.AddDirectJavaDependency;
import com.seed4j.module.domain.javabuild.command.AddJavaAnnotationProcessor;
import com.seed4j.module.domain.javabuild.command.AddJavaDependencyManagement;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuild.command.RemoveDirectJavaDependency;
import com.seed4j.module.domain.javabuild.command.RemoveJavaAnnotationProcessor;
import com.seed4j.module.domain.javabuild.command.RemoveJavaDependencyManagement;
import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

final class DependenciesCommandsFactory {

  public static final DependenciesCommandsFactory MANAGEMENT = new DependenciesCommandsFactory(
    AddJavaDependencyManagement::new,
    AddJavaDependencyManagement::new,
    RemoveJavaDependencyManagement::new,
    RemoveJavaDependencyManagement::new
  );
  public static final DependenciesCommandsFactory DIRECT = new DependenciesCommandsFactory(
    AddDirectJavaDependency::new,
    AddDirectJavaDependency::new,
    RemoveDirectJavaDependency::new,
    RemoveDirectJavaDependency::new
  );
  public static final DependenciesCommandsFactory ANNOTATION_PROCESSING = new DependenciesCommandsFactory(
    AddJavaAnnotationProcessor::new,
    AddJavaAnnotationProcessor::new,
    RemoveJavaAnnotationProcessor::new,
    RemoveJavaAnnotationProcessor::new
  );

  private final Function<JavaDependency, JavaBuildCommand> addDependency;
  private final BiFunction<JavaDependency, BuildProfileId, JavaBuildCommand> addDependencyToProfile;
  private final Function<DependencyId, JavaBuildCommand> removeDependency;
  private final BiFunction<DependencyId, BuildProfileId, JavaBuildCommand> removeDependencyToProfile;

  private DependenciesCommandsFactory(
    Function<JavaDependency, JavaBuildCommand> addDependency,
    BiFunction<JavaDependency, BuildProfileId, JavaBuildCommand> addDependencyToProfile,
    Function<DependencyId, JavaBuildCommand> removeDependency,
    BiFunction<DependencyId, BuildProfileId, JavaBuildCommand> removeDependencyToProfile
  ) {
    this.addDependency = addDependency;
    this.addDependencyToProfile = addDependencyToProfile;
    this.removeDependency = removeDependency;
    this.removeDependencyToProfile = removeDependencyToProfile;
  }

  public JavaBuildCommand addDependency(JavaDependency javaDependency, Optional<BuildProfileId> buildProfile) {
    if (buildProfile.isPresent()) {
      return addDependencyToProfile.apply(javaDependency, buildProfile.orElseThrow());
    }
    return addDependency.apply(javaDependency);
  }

  public JavaBuildCommand removeDependency(DependencyId id, Optional<BuildProfileId> buildProfile) {
    if (buildProfile.isPresent()) {
      return removeDependencyToProfile.apply(id, buildProfile.orElseThrow());
    }
    return removeDependency.apply(id);
  }
}
