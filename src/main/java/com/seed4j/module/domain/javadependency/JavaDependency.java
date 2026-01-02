package com.seed4j.module.domain.javadependency;

import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.DependencySlug;
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
import org.jspecify.annotations.Nullable;

public final class JavaDependency {

  private final DependencyId id;
  private final Optional<DependencySlug> dependencySlug;
  private final Optional<VersionSlug> versionSlug;
  private final JavaDependencyScope scope;
  private final boolean optional;
  private final Collection<DependencyId> exclusions;

  private JavaDependency(JavaDependencyBuilder builder) {
    id = buildId(builder);
    versionSlug = Optional.ofNullable(builder.versionSlug);
    dependencySlug = Optional.ofNullable(builder.dependencySlug);
    scope = JavaDependencyScope.from(builder.scope);
    optional = builder.optional;
    exclusions = Seed4JCollections.immutable(builder.exclusions);
  }

  private DependencyId buildId(JavaDependencyBuilder builder) {
    Assert.notNull("groupId", builder.groupId);
    Assert.notNull("artifactId", builder.artifactId);

    return DependencyId.builder()
      .groupId(builder.groupId)
      .artifactId(builder.artifactId)
      .classifier(builder.classifier)
      .type(builder.type)
      .build();
  }

  public static JavaDependencyGroupIdBuilder builder() {
    return new JavaDependencyBuilder();
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
    Optional<JavaDependency> projectDependency,
    Optional<BuildProfileId> buildProfile
  ) {
    return projectDependency
      .map(toDependenciesCommands(commands, buildProfile))
      .orElseGet(() -> List.of(commands.addDependency(this, buildProfile)));
  }

  private Function<JavaDependency, Collection<JavaBuildCommand>> toDependenciesCommands(
    DependenciesCommandsFactory commands,
    Optional<BuildProfileId> buildProfile
  ) {
    return projectDependency -> {
      JavaDependency resultingDependency = merge(projectDependency);

      if (resultingDependency.equals(projectDependency)) {
        return List.of();
      }

      return List.of(commands.removeDependency(id(), buildProfile), commands.addDependency(resultingDependency, buildProfile));
    };
  }

  private JavaDependency merge(JavaDependency other) {
    JavaDependencyOptionalValueBuilder builder = builder()
      .groupId(groupId())
      .artifactId(artifactId())
      .scope(mergeScopes(other))
      .optional(mergeOptionalFlag(other));

    mergeDependencySlugs(other).ifPresent(builder::dependencySlug);
    mergeVersionsSlugs(other).ifPresent(builder::versionSlug);
    classifier().ifPresent(builder::classifier);
    type().ifPresent(builder::type);

    return builder.build();
  }

  private Optional<DependencySlug> mergeDependencySlugs(JavaDependency other) {
    return dependencySlug.or(() -> other.dependencySlug);
  }

  private Optional<VersionSlug> mergeVersionsSlugs(JavaDependency other) {
    return versionSlug.or(() -> other.versionSlug);
  }

  private JavaDependencyScope mergeScopes(JavaDependency other) {
    return scope.merge(other.scope);
  }

  private boolean mergeOptionalFlag(JavaDependency other) {
    return optional && other.optional;
  }

  public DependencyId id() {
    return id;
  }

  public Optional<VersionSlug> version() {
    return versionSlug;
  }

  public Optional<DependencySlug> slug() {
    return dependencySlug;
  }

  public Optional<JavaDependencyClassifier> classifier() {
    return id.classifier();
  }

  public boolean optional() {
    return optional;
  }

  public JavaDependencyScope scope() {
    return scope;
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
    return new HashCodeBuilder().append(id).append(versionSlug).append(scope).append(optional).hashCode();
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

    JavaDependency other = (JavaDependency) obj;

    return new EqualsBuilder()
      .append(id, other.id)
      .append(dependencySlug, other.dependencySlug)
      .append(versionSlug, other.versionSlug)
      .append(scope, other.scope)
      .append(optional, other.optional)
      .isEquals();
  }

  private static final class JavaDependencyBuilder
    implements JavaDependencyGroupIdBuilder, JavaDependencyArtifactIdBuilder, JavaDependencyOptionalValueBuilder {

    private @Nullable GroupId groupId;
    private @Nullable ArtifactId artifactId;
    private @Nullable DependencySlug dependencySlug;
    private @Nullable VersionSlug versionSlug;
    private @Nullable JavaDependencyClassifier classifier;
    private @Nullable JavaDependencyScope scope;
    private boolean optional;
    private @Nullable JavaDependencyType type;
    private final Collection<DependencyId> exclusions = new ArrayList<>();

    @Override
    public JavaDependencyArtifactIdBuilder groupId(GroupId groupId) {
      this.groupId = groupId;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder artifactId(ArtifactId artifactId) {
      this.artifactId = artifactId;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder versionSlug(@Nullable VersionSlug versionSlug) {
      this.versionSlug = versionSlug;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder dependencySlug(@Nullable DependencySlug dependencySlug) {
      this.dependencySlug = dependencySlug;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder classifier(@Nullable JavaDependencyClassifier classifier) {
      this.classifier = classifier;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder scope(@Nullable JavaDependencyScope scope) {
      this.scope = scope;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder optional(boolean optional) {
      this.optional = optional;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder type(@Nullable JavaDependencyType type) {
      this.type = type;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder addExclusion(DependencyId dependency) {
      Assert.notNull("dependency", dependency);

      exclusions.add(dependency);

      return this;
    }

    @Override
    public JavaDependency build() {
      return new JavaDependency(this);
    }
  }

  public interface JavaDependencyGroupIdBuilder {
    JavaDependencyArtifactIdBuilder groupId(GroupId groupId);

    default JavaDependencyArtifactIdBuilder groupId(String groupId) {
      return groupId(new GroupId(groupId));
    }
  }

  public interface JavaDependencyArtifactIdBuilder {
    JavaDependencyOptionalValueBuilder artifactId(ArtifactId artifactId);

    default JavaDependencyOptionalValueBuilder artifactId(String artifactId) {
      return artifactId(new ArtifactId(artifactId));
    }
  }

  public interface JavaDependencyOptionalValueBuilder {
    JavaDependencyOptionalValueBuilder versionSlug(@Nullable VersionSlug versionSlug);

    JavaDependencyOptionalValueBuilder dependencySlug(@Nullable DependencySlug dependencySlug);

    JavaDependencyOptionalValueBuilder classifier(@Nullable JavaDependencyClassifier classifier);

    JavaDependencyOptionalValueBuilder scope(@Nullable JavaDependencyScope scope);

    JavaDependencyOptionalValueBuilder optional(boolean optional);

    JavaDependencyOptionalValueBuilder type(@Nullable JavaDependencyType type);

    JavaDependencyOptionalValueBuilder addExclusion(DependencyId dependency);

    JavaDependency build();

    default JavaDependencyOptionalValueBuilder dependencySlug(String dependencySlug) {
      return dependencySlug(DependencySlug.of(dependencySlug).orElse(null));
    }

    default JavaDependencyOptionalValueBuilder versionSlug(String versionSlug) {
      return versionSlug(VersionSlug.of(versionSlug).orElse(null));
    }

    default JavaDependencyOptionalValueBuilder classifier(String classifier) {
      return classifier(JavaDependencyClassifier.of(classifier).orElse(null));
    }

    default JavaDependencyOptionalValueBuilder optional() {
      return optional(true);
    }

    default JavaDependencyOptionalValueBuilder addExclusion(GroupId groupId, ArtifactId artifactId) {
      return addExclusion(DependencyId.of(groupId, artifactId));
    }
  }
}
