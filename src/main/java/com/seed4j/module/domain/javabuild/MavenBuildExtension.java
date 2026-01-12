package com.seed4j.module.domain.javabuild;

import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.Optional;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jspecify.annotations.Nullable;

public final class MavenBuildExtension {

  private final GroupId groupId;
  private final ArtifactId artifactId;
  private final Optional<VersionSlug> versionSlug;

  private MavenBuildExtension(MavenBuildExtensionBuilder builder) {
    Assert.notNull("groupId", builder.groupId);
    Assert.notNull("artifactId", builder.artifactId);

    groupId = builder.groupId;
    artifactId = builder.artifactId;
    versionSlug = Optional.ofNullable(builder.versionSlug);
  }

  public static MavenBuildExtensionGroupIdBuilder builder() {
    return new MavenBuildExtensionBuilder();
  }

  public Optional<VersionSlug> versionSlug() {
    return versionSlug;
  }

  public GroupId groupId() {
    return groupId;
  }

  public ArtifactId artifactId() {
    return artifactId;
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public int hashCode() {
    return new HashCodeBuilder().append(groupId).append(artifactId).append(versionSlug).hashCode();
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

    MavenBuildExtension other = (MavenBuildExtension) obj;

    return new EqualsBuilder()
      .append(groupId, other.groupId)
      .append(artifactId, other.artifactId)
      .append(versionSlug, other.versionSlug)
      .isEquals();
  }

  private static final class MavenBuildExtensionBuilder
    implements MavenBuildExtensionGroupIdBuilder, MavenBuildExtensionArtifactIdBuilder, MavenBuildExtensionOptionalValueBuilder
  {

    private @Nullable GroupId groupId;
    private @Nullable ArtifactId artifactId;
    private @Nullable VersionSlug versionSlug;

    @Override
    public MavenBuildExtensionArtifactIdBuilder groupId(GroupId groupId) {
      this.groupId = groupId;

      return this;
    }

    @Override
    public MavenBuildExtensionOptionalValueBuilder artifactId(ArtifactId artifactId) {
      this.artifactId = artifactId;

      return this;
    }

    @Override
    public MavenBuildExtensionOptionalValueBuilder versionSlug(@Nullable VersionSlug versionSlug) {
      this.versionSlug = versionSlug;

      return this;
    }

    @Override
    public MavenBuildExtension build() {
      return new MavenBuildExtension(this);
    }
  }

  public interface MavenBuildExtensionGroupIdBuilder {
    MavenBuildExtensionArtifactIdBuilder groupId(GroupId groupId);

    default MavenBuildExtensionArtifactIdBuilder groupId(String groupId) {
      return groupId(new GroupId(groupId));
    }
  }

  public interface MavenBuildExtensionArtifactIdBuilder {
    MavenBuildExtensionOptionalValueBuilder artifactId(ArtifactId artifactId);

    default MavenBuildExtensionOptionalValueBuilder artifactId(String artifactId) {
      return artifactId(new ArtifactId(artifactId));
    }
  }

  public interface MavenBuildExtensionOptionalValueBuilder {
    MavenBuildExtensionOptionalValueBuilder versionSlug(@Nullable VersionSlug versionSlug);

    MavenBuildExtension build();

    default MavenBuildExtensionOptionalValueBuilder versionSlug(String versionSlug) {
      return versionSlug(VersionSlug.of(versionSlug).orElse(null));
    }
  }
}
