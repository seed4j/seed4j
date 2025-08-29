package com.seed4j.statistic.infrastructure.secondary;

import static com.seed4j.statistic.domain.AppliedModuleFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.IntegrationTest;
import com.seed4j.shared.slug.domain.Seed4JModuleSlug;
import com.seed4j.statistic.domain.AppliedModule;
import com.seed4j.statistic.domain.StatisticsRepository;
import com.seed4j.statistic.domain.criteria.StatisticsCriteria;
import jakarta.transaction.Transactional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@Transactional
@IntegrationTest
@EnabledOnOs(OS.LINUX)
@ActiveProfiles("postgresql")
class PostgreSQLStatisticsRepositoryIT {

  @Autowired
  private StatisticsRepository statistics;

  @Test
  void shouldGetPostgreSQLStatisticsRepository() {
    assertThat(statistics).isInstanceOf(PostgreSQLStatisticsRepository.class);
  }

  @Test
  void shouldSaveAndGetStatistic() {
    statistics.save(appliedModule());

    assertThat(statistics.get(StatisticsCriteria.builder().build()).appliedModules()).isEqualTo(1);
  }

  @ParameterizedTest
  @MethodSource("matchingCriteria")
  void shouldGetOneForEachFilteredCriteria(StatisticsCriteria criteria) {
    AppliedModule appliedModule = appliedModule(Seed4JModuleSlug.ANGULAR_CORE.get());
    statistics.save(appliedModule);

    assertThat(statistics.get(criteria).appliedModules()).isEqualTo(1);
  }

  @ParameterizedTest
  @MethodSource("notMatchingCriteria")
  void shouldGetZeroForFalseCriteria(StatisticsCriteria criteria) {
    AppliedModule appliedModule = appliedModule();

    statistics.save(appliedModule);

    assertThat(statistics.get(criteria).appliedModules()).isZero();
  }

  static Stream<StatisticsCriteria> matchingCriteria() {
    return StatisticsCriteriaFixture.matchingCriteria();
  }

  static Stream<StatisticsCriteria> notMatchingCriteria() {
    return StatisticsCriteriaFixture.notMatchingCriteria();
  }
}
