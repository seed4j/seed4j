package com.seed4j.statistic.infrastructure.secondary;

import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
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
class AppliedModuleEntity {

  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "module")
  private String module;

  @Column(name = "date")
  private Instant date;

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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    AppliedModuleEntity that = (AppliedModuleEntity) o;
    return new EqualsBuilder().append(id, that.id).isEquals();
  }

  @Override
  @ExcludeFromGeneratedCodeCoverage
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(id).toHashCode();
  }
}
