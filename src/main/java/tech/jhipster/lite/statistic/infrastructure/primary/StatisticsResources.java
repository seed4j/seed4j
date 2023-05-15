package tech.jhipster.lite.statistic.infrastructure.primary;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.statistic.application.StatisticsApplicationService;
import tech.jhipster.lite.statistic.domain.criteria.StatisticsCriteria;

@RestController
@Tag(name = "Statistics")
@RequestMapping("/api/statistics")
class StatisticsResources {

  private final StatisticsApplicationService statistics;

  public StatisticsResources(StatisticsApplicationService statistics) {
    this.statistics = statistics;
  }

  @GetMapping
  @ApiResponse(description = "JHipster lite usage statistics", responseCode = "200")
  ResponseEntity<RestStatistics> getStatistics(RestStatisticsCriteria criteria) {
    StatisticsCriteria statisticsCriteria = criteria.toDomain();
    return ResponseEntity.ok(RestStatistics.from(statistics.get(statisticsCriteria)));
  }
}
