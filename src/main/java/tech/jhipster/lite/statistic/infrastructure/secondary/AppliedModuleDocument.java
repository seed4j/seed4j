package tech.jhipster.lite.statistic.infrastructure.secondary;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import tech.jhipster.lite.statistic.domain.AppliedModule;

@Document(collection = "applied_module")
@CompoundIndexes(@CompoundIndex(name = "date_moduleSlug", def = "{'date': 1, 'moduleSlug': 1}"))
class AppliedModuleDocument {

  @Id
  @Field(name = "id")
  private UUID id;

  @Field(name = "path")
  private String path;

  @Field(name = "module_slug")
  private String moduleSlug;

  @Field(name = "date")
  private Instant date;

  @Field(name = "properties")
  private Map<String, Object> properties;

  static AppliedModuleDocument from(AppliedModule appliedModule) {
    Assert.notNull("appliedModule", appliedModule);

    return new AppliedModuleDocument()
      .id(appliedModule.id().get())
      .path(appliedModule.path().get())
      .moduleSlug(appliedModule.module().slug())
      .date(appliedModule.date())
      .properties(appliedModule.properties().get());
  }

  private AppliedModuleDocument id(UUID id) {
    this.id = id;

    return this;
  }

  private AppliedModuleDocument path(String path) {
    this.path = path;

    return this;
  }

  private AppliedModuleDocument moduleSlug(String moduleSlug) {
    this.moduleSlug = moduleSlug;

    return this;
  }

  private AppliedModuleDocument date(Instant date) {
    this.date = date;

    return this;
  }

  private AppliedModuleDocument properties(Map<String, Object> properties) {
    this.properties = properties;

    return this;
  }

  AppliedModule toDomain() {
    return AppliedModule.builder().id(id).path(path).module(moduleSlug).date(date).properties(properties);
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public int hashCode() {
    return new HashCodeBuilder().append(id).hashCode();
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

    AppliedModuleDocument other = (AppliedModuleDocument) obj;

    return new EqualsBuilder().append(id, other.id).isEquals();
  }
}
