package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javabuild.command.AddJavaAnnotationProcessor;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuild.command.RemoveJavaAnnotationProcessor;
import com.seed4j.shared.error.domain.Assert;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jspecify.annotations.Nullable;

public final class JavaAnnotationProcessor {

  private final DependencyId id;
  private final Optional<VersionSlug> versionSlug;

  private JavaAnnotationProcessor(JavaAnnotationProcessorBuilder builder) {
    id = buildId(builder);
    versionSlug = Optional.ofNullable(builder.versionSlug);
  }

  private DependencyId buildId(JavaAnnotationProcessorBuilder builder) {
    Assert.notNull("groupId", builder.groupId);
    Assert.notNull("artifactId", builder.artifactId);

    return DependencyId.of(builder.groupId, builder.artifactId);
  }

  public static JavaAnnotationProcessorGroupIdBuilder builder() {
    return new JavaAnnotationProcessorBuilder();
  }

  public JavaBuildCommands changeCommands(JavaDependenciesVersions currentVersions, ProjectJavaDependencies projectDependencies) {
    Assert.notNull("currentVersion", currentVersions);
    Assert.notNull("projectDependencies", projectDependencies);

    Collection<JavaBuildCommand> dependenciesCommands = dependencyCommands(projectDependencies.annotationProcessor(id));
    Collection<JavaBuildCommand> versionCommands = versionCommands(currentVersions, projectDependencies, dependenciesCommands);

    return new JavaBuildCommands(Stream.of(dependenciesCommands, versionCommands).flatMap(Collection::stream).toList());
  }

  private Collection<JavaBuildCommand> versionCommands(
    JavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies,
    Collection<JavaBuildCommand> dependencyCommands
  ) {
    return JavaDependencyVersionCommands.build(
      version(),
      currentVersions,
      projectDependencies,
      dependencyCommands,
      AddJavaAnnotationProcessor.class::isInstance
    );
  }

  private Collection<JavaBuildCommand> dependencyCommands(Optional<JavaAnnotationProcessor> projectDependency) {
    return projectDependency.map(toDependenciesCommands()).orElseGet(() -> List.of(new AddJavaAnnotationProcessor(this)));
  }

  private Function<JavaAnnotationProcessor, Collection<JavaBuildCommand>> toDependenciesCommands() {
    return projectDependency -> {
      Optional<VersionSlug> mergedVersion = versionSlug.or(projectDependency::version);

      if (mergedVersion.equals(projectDependency.version())) {
        return List.of();
      }

      JavaAnnotationProcessor resultingDependency = builder()
        .groupId(id.groupId())
        .artifactId(id.artifactId())
        .versionSlug(mergedVersion.orElse(null))
        .build();

      return List.of(new RemoveJavaAnnotationProcessor(id()), new AddJavaAnnotationProcessor(resultingDependency));
    };
  }

  public DependencyId id() {
    return id;
  }

  public Optional<VersionSlug> version() {
    return versionSlug;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(id).append(versionSlug).hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    JavaAnnotationProcessor other = (JavaAnnotationProcessor) obj;

    return new EqualsBuilder().append(id, other.id).append(versionSlug, other.versionSlug).isEquals();
  }

  private static final class JavaAnnotationProcessorBuilder
    implements JavaAnnotationProcessorGroupIdBuilder, JavaAnnotationProcessorArtifactIdBuilder, JavaAnnotationProcessorOptionalValueBuilder
  {

    private @Nullable GroupId groupId;
    private @Nullable ArtifactId artifactId;
    private @Nullable VersionSlug versionSlug;

    @Override
    public JavaAnnotationProcessorArtifactIdBuilder groupId(GroupId groupId) {
      this.groupId = groupId;

      return this;
    }

    @Override
    public JavaAnnotationProcessorOptionalValueBuilder artifactId(ArtifactId artifactId) {
      this.artifactId = artifactId;

      return this;
    }

    @Override
    public JavaAnnotationProcessorOptionalValueBuilder versionSlug(@Nullable VersionSlug versionSlug) {
      this.versionSlug = versionSlug;

      return this;
    }

    @Override
    public JavaAnnotationProcessor build() {
      return new JavaAnnotationProcessor(this);
    }
  }

  public interface JavaAnnotationProcessorGroupIdBuilder {
    JavaAnnotationProcessorArtifactIdBuilder groupId(GroupId groupId);

    default JavaAnnotationProcessorArtifactIdBuilder groupId(String groupId) {
      return groupId(new GroupId(groupId));
    }
  }

  public interface JavaAnnotationProcessorArtifactIdBuilder {
    JavaAnnotationProcessorOptionalValueBuilder artifactId(ArtifactId artifactId);

    default JavaAnnotationProcessorOptionalValueBuilder artifactId(String artifactId) {
      return artifactId(new ArtifactId(artifactId));
    }
  }

  public interface JavaAnnotationProcessorOptionalValueBuilder {
    JavaAnnotationProcessorOptionalValueBuilder versionSlug(@Nullable VersionSlug versionSlug);

    JavaAnnotationProcessor build();

    default JavaAnnotationProcessorOptionalValueBuilder versionSlug(String versionSlug) {
      return versionSlug(VersionSlug.of(versionSlug).orElse(null));
    }
  }
}
