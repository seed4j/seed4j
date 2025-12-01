package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javabuild.command.AddDirectJavaDependency;
import com.seed4j.module.domain.javabuild.command.AddJavaDependencyManagement;
import com.seed4j.module.domain.javabuild.command.JavaBuildCommand;
import com.seed4j.module.domain.javabuild.command.SetVersion;
import com.seed4j.module.domain.javabuildprofile.BuildProfileId;
import com.seed4j.shared.collection.domain.Seed4JCollections;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
    return dependencyCommands
      .stream()
      .noneMatch(
        dependencyCommand ->
          dependencyCommand instanceof AddDirectJavaDependency || dependencyCommand instanceof AddJavaDependencyManagement
      );
  }

  private Function<JavaDependencyVersion, JavaBuildCommand> toSetVersionCommand() {
    return SetVersion::new;
  }

  Collection<JavaBuildCommand> dependencyCommands(
    DependenciesCommandsFactory commands,
    Optional<JavaAnnotationProcessorDependency> projectDependency,
    Optional<BuildProfileId> buildProfile
  ) {
    return projectDependency
      .map(toDependenciesCommands(commands, buildProfile))
      .orElseGet(() -> List.of(commands.addDependency(this, buildProfile)));
  }

  private Function<JavaAnnotationProcessorDependency, Collection<JavaBuildCommand>> toDependenciesCommands(
    DependenciesCommandsFactory commands,
    Optional<BuildProfileId> buildProfile
  ) {
    return projectDependency -> {
      JavaAnnotationProcessorDependency resultingDependency = merge(projectDependency);

      if (resultingDependency.equals(projectDependency)) {
        return List.of();
      }

      return List.of(commands.removeDependency(id(), buildProfile), commands.addDependency(resultingDependency, buildProfile));
    };
  }

  private JavaAnnotationProcessorDependency merge(JavaAnnotationProcessorDependency other) {
    return builder()
      .groupId(groupId())
      .artifactId(artifactId())
      .versionSlug(mergeVersionsSlugs(other))
      .classifier(classifier().orElse(null))
      .type(type().orElse(null))
      .build();
  }

  private VersionSlug mergeVersionsSlugs(JavaAnnotationProcessorDependency other) {
    return versionSlug.orElseGet(() -> other.versionSlug.orElse(null));
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
      JavaAnnotationProcessorDependencyOptionalValueBuilder {

    private GroupId groupId;
    private ArtifactId artifactId;
    private VersionSlug versionSlug;
    private JavaDependencyClassifier classifier;
    private JavaDependencyType type;
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
    public JavaAnnotationProcessorDependencyOptionalValueBuilder versionSlug(VersionSlug versionSlug) {
      this.versionSlug = versionSlug;

      return this;
    }

    @Override
    public JavaAnnotationProcessorDependencyOptionalValueBuilder classifier(JavaDependencyClassifier classifier) {
      this.classifier = classifier;

      return this;
    }

    @Override
    public JavaAnnotationProcessorDependencyOptionalValueBuilder type(JavaDependencyType type) {
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
    JavaAnnotationProcessorDependencyOptionalValueBuilder versionSlug(VersionSlug versionSlug);

    JavaAnnotationProcessorDependencyOptionalValueBuilder classifier(JavaDependencyClassifier classifier);

    JavaAnnotationProcessorDependencyOptionalValueBuilder scope(JavaDependencyScope scope);

    JavaAnnotationProcessorDependencyOptionalValueBuilder optional(boolean optional);

    JavaAnnotationProcessorDependencyOptionalValueBuilder type(JavaDependencyType type);

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
