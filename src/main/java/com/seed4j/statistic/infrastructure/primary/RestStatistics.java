package com.seed4j.statistic.infrastructure.primary;

import com.seed4j.statistic.domain.Statistics;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@Schema(name = "statistics", description = "Seed4J usage statistics")
final class RestStatistics {

  private final long appliedModules;

  private RestStatistics(long appliedModules) {
    this.appliedModules = appliedModules;
  }

  public static RestStatistics from(Statistics statistics) {
    return new RestStatistics(statistics.appliedModules());
  }

  @Schema(description = "Number of module applied on this instance", requiredMode = RequiredMode.REQUIRED)
  public long getAppliedModules() {
    return appliedModules;
  }
}
