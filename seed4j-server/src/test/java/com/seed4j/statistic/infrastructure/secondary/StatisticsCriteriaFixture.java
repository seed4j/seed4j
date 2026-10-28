package com.seed4j.statistic.infrastructure.secondary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_CORE;

import com.seed4j.statistic.domain.criteria.StatisticsCriteria;
import java.time.Instant;
import java.util.stream.Stream;

final class StatisticsCriteriaFixture {

  private StatisticsCriteriaFixture() {}

  static Stream<StatisticsCriteria> notMatchingCriteria() {
    return Stream.of(
      StatisticsCriteria.builder()
        .startTime(Instant.parse("2020-12-03T10:15:30.00Z"))
        .endTime(Instant.parse("2022-12-03T10:15:30.00Z"))
        .moduleSlug(ANGULAR_CORE.get())
        .build(),
      StatisticsCriteria.builder()
        .startTime(Instant.parse("2022-12-03T10:15:30.00Z"))
        .endTime(Instant.parse("2022-12-03T10:15:30.00Z"))
        .build(),
      StatisticsCriteria.builder()
        .startTime(Instant.parse("2020-12-03T10:15:30.00Z"))
        .endTime(Instant.parse("2020-12-03T10:15:30.00Z"))
        .build()
    );
  }

  static Stream<StatisticsCriteria> matchingCriteria() {
    return Stream.of(
      StatisticsCriteria.builder().moduleSlug(ANGULAR_CORE.get()).build(),
      StatisticsCriteria.builder().endTime(Instant.parse("2021-12-04T10:15:30.00Z")).build(),
      StatisticsCriteria.builder().endTime(Instant.parse("2021-12-04T10:15:30.00Z")).build()
    );
  }
}
