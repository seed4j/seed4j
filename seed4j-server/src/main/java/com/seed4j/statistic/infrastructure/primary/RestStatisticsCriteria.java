package com.seed4j.statistic.infrastructure.primary;

import com.seed4j.statistic.domain.criteria.StatisticsCriteria;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.time.Instant;

@Schema(name = "RestStatisticsCriteria", description = "Seed4J rest statistics criteria")
record RestStatisticsCriteria(
  @Schema(description = "Start time to apply filter", nullable = true, requiredMode = RequiredMode.NOT_REQUIRED) Instant startTime,
  @Schema(description = "End time to apply filter", nullable = true, requiredMode = RequiredMode.NOT_REQUIRED) Instant endTime,
  @Schema(description = "Module slug to apply filter with", nullable = true, requiredMode = RequiredMode.NOT_REQUIRED) String moduleSlug
) {
  public StatisticsCriteria toDomain() {
    return StatisticsCriteria.builder().startTime(this.startTime).endTime(this.endTime).moduleSlug(this.moduleSlug).build();
  }
}
