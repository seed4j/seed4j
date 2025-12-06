package com.seed4j.module.domain.javadependency;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.Optional;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class DependencyId {

  private final GroupId groupId;
  private final ArtifactId artifactId;
  private final Optional<JavaDependencyClassifier> classifier;
  private final Optional<JavaDependencyType> type;

  private DependencyId(DependencyIdBuilder builder) {
    Assert.notNull("groupId", builder.groupId);
    Assert.notNull("artifactId", builder.artifactId);

    groupId = builder.groupId;
    artifactId = builder.artifactId;
    classifier = Optional.ofNullable(builder.classifier);
    type = Optional.ofNullable(builder.type);
  }

  public static DependencyIdGroupIdBuilder builder() {
    return new DependencyIdBuilder();
  }

  public static DependencyId of(GroupId groupId, ArtifactId artifactId) {
    return builder().groupId(groupId).artifactId(artifactId).build();
  }

  public GroupId groupId() {
    return groupId;
  }

  public ArtifactId artifactId() {
    return artifactId;
  }

  public Optional<JavaDependencyClassifier> classifier() {
    return classifier;
  }

  public Optional<JavaDependencyType> type() {
    return type;
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public int hashCode() {
    return new HashCodeBuilder().append(artifactId).append(classifier).append(groupId).append(type).hashCode();
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

    DependencyId other = (DependencyId) obj;
    return new EqualsBuilder()
      .append(artifactId, other.artifactId)
      .append(classifier, other.classifier)
      .append(groupId, other.groupId)
      .append(type, other.type)
      .isEquals();
  }

  private static final class DependencyIdBuilder
    implements DependencyIdGroupIdBuilder, DependencyIdArtifactIdBuilder, DependencyIdOptionalFieldsBuilder {

    private GroupId groupId;
    private ArtifactId artifactId;
    private JavaDependencyClassifier classifier;
    private JavaDependencyType type;

    @Override
    public DependencyIdArtifactIdBuilder groupId(GroupId groupId) {
      this.groupId = groupId;

      return this;
    }

    @Override
    public DependencyIdOptionalFieldsBuilder artifactId(ArtifactId artifactId) {
      this.artifactId = artifactId;

      return this;
    }

    @Override
    public DependencyIdOptionalFieldsBuilder classifier(JavaDependencyClassifier classifier) {
      this.classifier = classifier;

      return this;
    }

    @Override
    public DependencyIdOptionalFieldsBuilder type(JavaDependencyType type) {
      this.type = type;

      return this;
    }

    @Override
    public DependencyId build() {
      return new DependencyId(this);
    }
  }

  public interface DependencyIdGroupIdBuilder {
    DependencyIdArtifactIdBuilder groupId(GroupId groupId);

    default DependencyIdArtifactIdBuilder groupId(String groupId) {
      return groupId(new GroupId(groupId));
    }
  }

  public interface DependencyIdArtifactIdBuilder {
    DependencyIdOptionalFieldsBuilder artifactId(ArtifactId artifactId);

    default DependencyIdOptionalFieldsBuilder artifactId(String artifactId) {
      return artifactId(new ArtifactId(artifactId));
    }
  }

  public interface DependencyIdOptionalFieldsBuilder {
    DependencyIdOptionalFieldsBuilder classifier(JavaDependencyClassifier classifier);

    DependencyIdOptionalFieldsBuilder type(JavaDependencyType type);

    DependencyId build();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public String toString() {
    ToStringBuilder builder = new ToStringBuilder(this, SHORT_PREFIX_STYLE)
      .append("groupId", groupId)
      .append("artifactId", artifactId)
      .append("classifier", classifier.map(JavaDependencyClassifier::toString).orElse(""))
      .append("type", type.map(JavaDependencyType::toString).orElse(""));
    return builder.toString();
  }
}
