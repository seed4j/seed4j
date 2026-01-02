package com.seed4j.statistic.infrastructure.secondary;

import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import com.seed4j.shared.nullness.domain.Initializer;
import com.seed4j.statistic.domain.AppliedModule;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "applied_module")
@SuppressWarnings("NullAway.Init")
class AppliedModuleEntity {

  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "module")
  private String module;

  @Column(name = "date")
  private Instant date;

  @Initializer
  public static AppliedModuleEntity from(AppliedModule moduleApplied) {
    return new AppliedModuleEntity().id(moduleApplied.id().get()).module(moduleApplied.module().slug()).date(moduleApplied.date());
  }

  private AppliedModuleEntity id(UUID id) {
    this.id = id;

    return this;
  }

  private AppliedModuleEntity module(String module) {
    this.module = module;

    return this;
  }

  private AppliedModuleEntity date(Instant date) {
    this.date = date;

    return this;
  }

  public AppliedModule toDomain() {
    return AppliedModule.builder().id(id).module(module).date(date);
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (!(other instanceof AppliedModuleEntity otherEntity)) {
      return false;
    }

    return new EqualsBuilder().append(id, otherEntity.id).isEquals();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(id).toHashCode();
  }
}
