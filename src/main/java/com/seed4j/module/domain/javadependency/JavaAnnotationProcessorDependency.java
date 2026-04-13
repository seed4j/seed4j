package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javabuild.command.AddJavaAnnotationProcessor;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommands;
import com.seed4j.module.domain.javabuild.command.RemoveJavaAnnotationProcessor;
import com.seed4j.module.domain.javabuild.command.SetVersion;
import com.seed4j.shared.collection.domain.Seed4JCollections;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jspecify.annotations.Nullable;

public final class JavaAnnotationProcessorDependency {

  private final DependencyId id;
  private final Optional<VersionSlug> versionSlug;
  private final Collection<DependencyId> exclusions;

  private JavaAnnotationProcessorDependency(JavaAnnotationProcessorDependencyBuilder builder) {
    id = buildId(builder);
    versionSlug = Optional.ofNullable(builder.versionSlug);
    exclusions = Seed4JCollections.immutable(builder.exclusions);
  }

  private DependencyId buildId(JavaAnnotationProcessorDependencyBuilder builder) {
    Assert.notNull("groupId", builder.groupId);
    Assert.notNull("artifactId", builder.artifactId);

    return DependencyId.builder()
      .groupId(builder.groupId)
      .artifactId(builder.artifactId)
      .classifier(builder.classifier)
      .type(builder.type)
      .build();
  }

  public static JavaAnnotationProcessorDependencyGroupIdBuilder builder() {
    return new JavaAnnotationProcessorDependencyBuilder();
  }

  public JavaBuildCommands changeCommands(JavaDependenciesVersions currentVersions, ProjectJavaDependencies projectDependencies) {
    Assert.notNull("currentVersion", currentVersions);
    Assert.notNull("projectDependencies", projectDependencies);

    Collection<JavaBuildCommand> depCommands = dependencyCommands(projectDependencies.annotationProcessor(id));
    Collection<JavaBuildCommand> verCommands = versionCommands(currentVersions, projectDependencies, depCommands);

    return new JavaBuildCommands(Stream.of(depCommands, verCommands).flatMap(Collection::stream).toList());
  }

  Collection<JavaBuildCommand> versionCommands(
    JavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies,
    Collection<JavaBuildCommand> dependencyCommands
  ) {
    return version()
      .flatMap(toVersion(currentVersions, projectDependencies, dependencyCommands))
      .map(toSetVersionCommand())
      .map(List::of)
      .orElse(List.of());
  }

  public static Function<VersionSlug, Optional<JavaDependencyVersion>> toVersion(
    JavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies
  ) {
    return toVersion(currentVersions, projectDependencies, List.of());
  }

  private static Function<VersionSlug, Optional<JavaDependencyVersion>> toVersion(
    JavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies,
    Collection<JavaBuildCommand> dependencyCommands
  ) {
    return slug -> {
      JavaDependencyVersion currentVersion = currentVersions.get(slug);

      return projectDependencies
        .version(slug)
        .map(toVersionToUse(currentVersion, dependencyCommands))
        .orElseGet(() -> Optional.of(currentVersion));
    };
  }

  private static Function<JavaDependencyVersion, Optional<JavaDependencyVersion>> toVersionToUse(
    JavaDependencyVersion currentVersion,
    Collection<JavaBuildCommand> dependencyCommands
  ) {
    return version -> {
      if (version.equals(currentVersion) && hasNoDependencyToAdd(dependencyCommands)) {
        return Optional.empty();
      }

      return Optional.of(currentVersion);
    };
  }

  private static boolean hasNoDependencyToAdd(Collection<JavaBuildCommand> dependencyCommands) {
    return dependencyCommands.stream().noneMatch(dependencyCommand -> dependencyCommand instanceof AddJavaAnnotationProcessor);
  }

  private Function<JavaDependencyVersion, JavaBuildCommand> toSetVersionCommand() {
    return SetVersion::new;
  }

  private Collection<JavaBuildCommand> dependencyCommands(Optional<JavaDependency> projectDependency) {
    return projectDependency.map(toDependenciesCommands()).orElseGet(() -> List.of(new AddJavaAnnotationProcessor(this)));
  }

  private Function<JavaDependency, Collection<JavaBuildCommand>> toDependenciesCommands() {
    return projectDependency -> {
      Optional<VersionSlug> mergedVersion = versionSlug.or(projectDependency::version);
      Optional<JavaDependencyClassifier> mergedClassifier = classifier().or(projectDependency::classifier);
      Optional<JavaDependencyType> mergedType = type().or(projectDependency::type);

      if (
        mergedVersion.equals(projectDependency.version())
        && mergedClassifier.equals(projectDependency.classifier())
        && mergedType.equals(projectDependency.type())
      ) {
        return List.of();
      }

      JavaAnnotationProcessorDependency resultingDependency = builder()
        .groupId(groupId())
        .artifactId(artifactId())
        .versionSlug(mergedVersion.orElse(null))
        .classifier(mergedClassifier.orElse(null))
        .type(mergedType.orElse(null))
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

  public Optional<JavaDependencyClassifier> classifier() {
    return id.classifier();
  }

  public Optional<JavaDependencyType> type() {
    return id.type();
  }

  public Collection<DependencyId> exclusions() {
    return exclusions;
  }

  private GroupId groupId() {
    return id.groupId();
  }

  private ArtifactId artifactId() {
    return id.artifactId();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public int hashCode() {
    return new HashCodeBuilder().append(id).append(versionSlug).hashCode();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    JavaAnnotationProcessorDependency other = (JavaAnnotationProcessorDependency) obj;

    return new EqualsBuilder().append(id, other.id).append(versionSlug, other.versionSlug).isEquals();
  }

  private static final class JavaAnnotationProcessorDependencyBuilder
    implements
      JavaAnnotationProcessorDependencyGroupIdBuilder,
      JavaAnnotationProcessorDependencyArtifactIdBuilder,
      JavaAnnotationProcessorDependencyOptionalValueBuilder
  {

    private @Nullable GroupId groupId;
    private @Nullable ArtifactId artifactId;
    private @Nullable VersionSlug versionSlug;
    private @Nullable JavaDependencyClassifier classifier;
    private @Nullable JavaDependencyType type;
    private final Collection<DependencyId> exclusions = new ArrayList<>();

    @Override
    public JavaAnnotationProcessorDependencyArtifactIdBuilder groupId(GroupId groupId) {
      this.groupId = groupId;

      return this;
    }

    @Override
    public JavaAnnotationProcessorDependencyOptionalValueBuilder artifactId(ArtifactId artifactId) {
      this.artifactId = artifactId;

      return this;
    }

    @Override
    public JavaAnnotationProcessorDependencyOptionalValueBuilder versionSlug(@Nullable VersionSlug versionSlug) {
      this.versionSlug = versionSlug;

      return this;
    }

    @Override
    public JavaAnnotationProcessorDependencyOptionalValueBuilder classifier(@Nullable JavaDependencyClassifier classifier) {
      this.classifier = classifier;

      return this;
    }

    @Override
    public JavaAnnotationProcessorDependencyOptionalValueBuilder type(@Nullable JavaDependencyType type) {
      this.type = type;

      return this;
    }

    @Override
    public JavaAnnotationProcessorDependencyOptionalValueBuilder addExclusion(DependencyId dependency) {
      Assert.notNull("dependency", dependency);

      exclusions.add(dependency);

      return this;
    }

    @Override
    public JavaAnnotationProcessorDependency build() {
      return new JavaAnnotationProcessorDependency(this);
    }
  }

  public interface JavaAnnotationProcessorDependencyGroupIdBuilder {
    JavaAnnotationProcessorDependencyArtifactIdBuilder groupId(GroupId groupId);

    default JavaAnnotationProcessorDependencyArtifactIdBuilder groupId(String groupId) {
      return groupId(new GroupId(groupId));
    }
  }

  public interface JavaAnnotationProcessorDependencyArtifactIdBuilder {
    JavaAnnotationProcessorDependencyOptionalValueBuilder artifactId(ArtifactId artifactId);

    default JavaAnnotationProcessorDependencyOptionalValueBuilder artifactId(String artifactId) {
      return artifactId(new ArtifactId(artifactId));
    }
  }

  public interface JavaAnnotationProcessorDependencyOptionalValueBuilder {
    JavaAnnotationProcessorDependencyOptionalValueBuilder versionSlug(@Nullable VersionSlug versionSlug);

    JavaAnnotationProcessorDependencyOptionalValueBuilder classifier(@Nullable JavaDependencyClassifier classifier);

    JavaAnnotationProcessorDependencyOptionalValueBuilder type(@Nullable JavaDependencyType type);

    JavaAnnotationProcessorDependencyOptionalValueBuilder addExclusion(DependencyId dependency);

    JavaAnnotationProcessorDependency build();

    default JavaAnnotationProcessorDependencyOptionalValueBuilder versionSlug(String versionSlug) {
      return versionSlug(VersionSlug.of(versionSlug).orElse(null));
    }

    default JavaAnnotationProcessorDependencyOptionalValueBuilder classifier(String classifier) {
      return classifier(JavaDependencyClassifier.of(classifier).orElse(null));
    }

    default JavaAnnotationProcessorDependencyOptionalValueBuilder addExclusion(GroupId groupId, ArtifactId artifactId) {
      return addExclusion(DependencyId.of(groupId, artifactId));
    }
  }
}
