package com.seed4j.statistic.infrastructure.secondary;

import com.seed4j.shared.error.domain.Assert;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import com.seed4j.statistic.domain.AppliedModule;
import java.time.Instant;
import java.util.UUID;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "applied_module")
@SuppressWarnings("NullAway.Init")
@CompoundIndexes(@CompoundIndex(name = "date_moduleSlug", def = "{'date': 1, 'moduleSlug': 1}"))
class AppliedModuleDocument {

  @Id
  @Field(name = "id")
  private UUID id;

  @Field(name = "module_slug")
  private String moduleSlug;

  @Field(name = "date")
  private Instant date;

  static AppliedModuleDocument from(AppliedModule appliedModule) {
    Assert.notNull("appliedModule", appliedModule);

    return new AppliedModuleDocument().id(appliedModule.id().get()).moduleSlug(appliedModule.module().slug()).date(appliedModule.date());
  }

  private AppliedModuleDocument id(UUID id) {
    this.id = id;

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

  AppliedModule toDomain() {
    return AppliedModule.builder().id(id).module(moduleSlug).date(date);
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

    if (!(obj instanceof AppliedModuleDocument other)) {
      return false;
    }

    return new EqualsBuilder().append(id, other.id).isEquals();
  }
}
