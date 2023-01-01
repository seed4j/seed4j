package tech.jhipster.lite.module.domain.javadependency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.common.domain.JHipsterCollections;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.DependencySlug;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommand;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;

public class JavaDependency {

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
    exclusions = JHipsterCollections.immutable(builder.exclusions);
  }

  private DependencyId buildId(JavaDependencyBuilder builder) {
    return DependencyId
      .builder()
      .groupId(builder.groupId)
      .artifactId(builder.artifactId)
      .classifier(builder.classifier)
      .type(builder.type)
      .build();
  }

  public static JavaDependencyGroupIdBuilder builder() {
    return new JavaDependencyBuilder();
  }

  Collection<JavaBuildCommand> versionCommands(JavaDependenciesVersions currentVersions, ProjectJavaDependencies projectDependencies) {
    return version().flatMap(toVersion(currentVersions, projectDependencies)).map(toSetVersionCommand()).map(List::of).orElse(List.of());
  }

  private Function<VersionSlug, Optional<JavaDependencyVersion>> toVersion(
    JavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies
  ) {
    return slug -> {
      JavaDependencyVersion currentVersion = currentVersions.get(slug);

      return projectDependencies.version(slug).map(toVersionToUse(currentVersion)).orElseGet(() -> Optional.of(currentVersion));
    };
  }

  private Function<JavaDependencyVersion, Optional<JavaDependencyVersion>> toVersionToUse(JavaDependencyVersion currentVersion) {
    return version -> {
      if (version.equals(currentVersion)) {
        return Optional.empty();
      }

      return Optional.of(currentVersion);
    };
  }

  private Function<JavaDependencyVersion, JavaBuildCommand> toSetVersionCommand() {
    return SetVersion::new;
  }

  Collection<JavaBuildCommand> dependencyCommands(DependenciesCommandsFactory commands, Optional<JavaDependency> projectDependency) {
    return projectDependency.map(toDependenciesCommands(commands)).orElseGet(() -> List.of(commands.addDependency(this)));
  }

  private Function<JavaDependency, Collection<JavaBuildCommand>> toDependenciesCommands(DependenciesCommandsFactory commands) {
    return projectDependency -> {
      JavaDependency resultingDependency = merge(projectDependency);

      if (resultingDependency.equals(projectDependency)) {
        return List.of();
      }

      return List.of(commands.removeDependency(id()), commands.addDependency(resultingDependency));
    };
  }

  private JavaDependency merge(JavaDependency other) {
    return JavaDependency
      .builder()
      .groupId(groupId())
      .artifactId(artifactId())
      .versionSlug(mergeVersionsSlugs(other))
      .dependencySlug(mergeDependencySlugs(other))
      .classifier(classifier().orElse(null))
      .scope(mergeScopes(other))
      .optional(mergeOptionalFlag(other))
      .type(type().orElse(null))
      .build();
  }

  private DependencySlug mergeDependencySlugs(JavaDependency other) {
    return dependencySlug.orElseGet(() -> other.dependencySlug.orElse(null));
  }

  private VersionSlug mergeVersionsSlugs(JavaDependency other) {
    return versionSlug.orElseGet(() -> other.versionSlug.orElse(null));
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
  @Generated
  public int hashCode() {
    return new HashCodeBuilder().append(id).append(versionSlug).append(scope).append(optional).hashCode();
  }

  @Override
  @Generated
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

  public static class JavaDependencyBuilder
    implements JavaDependencyGroupIdBuilder, JavaDependencyArtifactIdBuilder, JavaDependencyOptionalValueBuilder {

    private GroupId groupId;
    private ArtifactId artifactId;
    private DependencySlug dependencySlug;
    private VersionSlug versionSlug;
    private JavaDependencyClassifier classifier;
    private JavaDependencyScope scope;
    private boolean optional;
    private JavaDependencyType type;
    private final Collection<DependencyId> exclusions = new ArrayList<>();

    private JavaDependencyBuilder() {}

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
    public JavaDependencyOptionalValueBuilder versionSlug(VersionSlug versionSlug) {
      this.versionSlug = versionSlug;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder dependencySlug(DependencySlug dependencySlug) {
      this.dependencySlug = dependencySlug;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder classifier(JavaDependencyClassifier classifier) {
      this.classifier = classifier;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder scope(JavaDependencyScope scope) {
      this.scope = scope;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder optional(boolean optional) {
      this.optional = optional;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder type(JavaDependencyType type) {
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
    JavaDependencyOptionalValueBuilder versionSlug(VersionSlug versionSlug);

    JavaDependencyOptionalValueBuilder dependencySlug(DependencySlug dependencySlug);

    JavaDependencyOptionalValueBuilder classifier(JavaDependencyClassifier classifier);

    JavaDependencyOptionalValueBuilder scope(JavaDependencyScope scope);

    JavaDependencyOptionalValueBuilder optional(boolean optional);

    JavaDependencyOptionalValueBuilder type(JavaDependencyType type);

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
