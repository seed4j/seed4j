package tech.jhipster.lite.module.domain.javadependency;

import java.util.Optional;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.generation.domain.Generated;

public class DependencyId {

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
    return DependencyId.builder().groupId(groupId).artifactId(artifactId).build();
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
  @Generated
  public int hashCode() {
    return new HashCodeBuilder().append(artifactId).append(classifier).append(groupId).append(type).hashCode();
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

    DependencyId other = (DependencyId) obj;
    return new EqualsBuilder()
      .append(artifactId, other.artifactId)
      .append(classifier, other.classifier)
      .append(groupId, other.groupId)
      .append(type, other.type)
      .isEquals();
  }

  private static class DependencyIdBuilder
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
  }

  public interface DependencyIdArtifactIdBuilder {
    DependencyIdOptionalFieldsBuilder artifactId(ArtifactId artifactId);
  }

  public interface DependencyIdOptionalFieldsBuilder {
    DependencyIdOptionalFieldsBuilder classifier(JavaDependencyClassifier classifier);

    DependencyIdOptionalFieldsBuilder type(JavaDependencyType type);

    DependencyId build();
  }
}
