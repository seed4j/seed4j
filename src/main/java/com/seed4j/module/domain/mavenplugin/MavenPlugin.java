package com.seed4j.module.domain.mavenplugin;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javadependency.DependencyId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.mavenplugin.MavenPluginExecution.MavenPluginExecutionOptionalBuilder;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jspecify.annotations.Nullable;

public final class MavenPlugin {

  private final DependencyId dependencyId;
  private final Collection<MavenPluginExecution> executions;
  private final Collection<JavaDependency> dependencies;
  private final Optional<VersionSlug> versionSlug;
  private final Optional<MavenPluginConfiguration> configuration;

  private MavenPlugin(MavenPluginBuilder builder) {
    Assert.notNull("groupId", builder.groupId);
    Assert.notNull("artifactId", builder.artifactId);
    Assert.notNull("executions", builder.executions);
    Assert.notNull("dependencies", builder.dependencies);

    dependencyId = DependencyId.of(builder.groupId, builder.artifactId);
    versionSlug = Optional.ofNullable(builder.versionSlug);
    configuration = Optional.ofNullable(builder.configuration);
    executions = builder.executions;
    dependencies = builder.dependencies;
  }

  public static MavenPluginGroupIdBuilder builder() {
    return new MavenPluginBuilder();
  }

  public Optional<VersionSlug> versionSlug() {
    return versionSlug;
  }

  public Optional<MavenPluginConfiguration> configuration() {
    return configuration;
  }

  public Collection<MavenPluginExecution> executions() {
    return executions;
  }

  public DependencyId dependencyId() {
    return dependencyId;
  }

  public Collection<JavaDependency> dependencies() {
    return dependencies;
  }

  private static final class MavenPluginBuilder
    implements MavenPluginGroupIdBuilder, MavenPluginArtifactIdBuilder, MavenPluginOptionalBuilder {

    private @Nullable GroupId groupId;
    private @Nullable ArtifactId artifactId;
    private @Nullable VersionSlug versionSlug;
    private @Nullable MavenPluginConfiguration configuration;
    private final List<MavenPluginExecution> executions = new ArrayList<>();
    private final Collection<JavaDependency> dependencies = new LinkedHashSet<>();

    @Override
    public MavenPluginArtifactIdBuilder groupId(GroupId groupId) {
      this.groupId = groupId;

      return this;
    }

    @Override
    public MavenPluginOptionalBuilder artifactId(ArtifactId artifactId) {
      this.artifactId = artifactId;

      return this;
    }

    @Override
    public MavenPluginOptionalBuilder versionSlug(VersionSlug versionSlug) {
      this.versionSlug = versionSlug;

      return this;
    }

    @Override
    public MavenPluginOptionalBuilder configuration(MavenPluginConfiguration configuration) {
      this.configuration = configuration;
      return this;
    }

    @Override
    public MavenPluginOptionalBuilder addExecution(MavenPluginExecution executions) {
      this.executions.add(executions);
      return this;
    }

    @Override
    public MavenPluginOptionalBuilder addDependency(JavaDependency dependency) {
      this.dependencies.add(dependency);
      return this;
    }

    @Override
    public MavenPlugin build() {
      return new MavenPlugin(this);
    }
  }

  public interface MavenPluginGroupIdBuilder {
    MavenPluginArtifactIdBuilder groupId(GroupId groupId);

    default MavenPluginArtifactIdBuilder groupId(String groupId) {
      return groupId(new GroupId(groupId));
    }
  }

  public interface MavenPluginArtifactIdBuilder {
    MavenPluginOptionalBuilder artifactId(ArtifactId artifactId);

    default MavenPluginOptionalBuilder artifactId(String artifactId) {
      return artifactId(new ArtifactId(artifactId));
    }
  }

  public interface MavenPluginOptionalBuilder {
    MavenPluginOptionalBuilder versionSlug(VersionSlug slug);

    MavenPlugin build();

    default MavenPluginOptionalBuilder versionSlug(String slug) {
      return versionSlug(new VersionSlug(slug));
    }

    MavenPluginOptionalBuilder configuration(MavenPluginConfiguration configuration);

    default MavenPluginOptionalBuilder configuration(String configuration) {
      return configuration(new MavenPluginConfiguration(configuration));
    }

    MavenPluginOptionalBuilder addExecution(MavenPluginExecution executions);

    default MavenPluginOptionalBuilder addExecution(MavenPluginExecutionOptionalBuilder builder) {
      return addExecution(builder.build());
    }

    MavenPluginOptionalBuilder addDependency(JavaDependency javaDependency);

    default MavenPluginOptionalBuilder addDependency(GroupId groupId, ArtifactId artifactId, VersionSlug version) {
      return addDependency(JavaDependency.builder().groupId(groupId).artifactId(artifactId).versionSlug(version).build());
    }
  }

  @Override
  @SuppressWarnings("java:S1192")
  @ExcludeFromGeneratedCodeCoverage
  public String toString() {
    ToStringBuilder builder = new ToStringBuilder(this, SHORT_PREFIX_STYLE)
      .append("dependencyId", dependencyId)
      .append("versionSlug", versionSlug.map(VersionSlug::toString).orElse(""))
      .append("configuration", configuration.map(MavenPluginConfiguration::toString).orElse(""))
      .append("executions", executions)
      .append("dependencies", dependencies);
    return builder.toString();
  }
}
