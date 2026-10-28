package com.seed4j.statistic.infrastructure.secondary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.ANGULAR_CORE;
import static com.seed4j.statistic.domain.AppliedModuleFixture.appliedModule;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.statistic.domain.AppliedModule;
import com.seed4j.statistic.domain.criteria.StatisticsCriteria;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class InMemoryStatisticsRepositoryTest {

  private final InMemoryStatisticsRepository inMemoryStatisticsRepository = new InMemoryStatisticsRepository();

  static Stream<StatisticsCriteria> provideFilterCriteria() {
    return StatisticsCriteriaFixture.matchingCriteria();
  }

  static Stream<StatisticsCriteria> provideFalseCriteria() {
    return StatisticsCriteriaFixture.notMatchingCriteria();
  }

  @BeforeEach
  void beforeEach() {
    inMemoryStatisticsRepository.clear();
  }

  @Test
  void shouldGetZeroForNullCriteria() {
    assertThat(inMemoryStatisticsRepository.get(StatisticsCriteria.builder().build()).appliedModules()).isZero();
  }

  @ParameterizedTest
  @MethodSource("provideFilterCriteria")
  void shouldGetOneForEachFilteredCriteria(StatisticsCriteria criteria) {
    AppliedModule appliedModule = appliedModule(ANGULAR_CORE.get());
    inMemoryStatisticsRepository.save(appliedModule);

    assertThat(inMemoryStatisticsRepository.get(criteria).appliedModules()).isEqualTo(1);
  }

  @ParameterizedTest
  @MethodSource("provideFalseCriteria")
  void shouldGetZeroForFalseCriteria(StatisticsCriteria criteria) {
    AppliedModule appliedModule = appliedModule();

    inMemoryStatisticsRepository.save(appliedModule);

    assertThat(inMemoryStatisticsRepository.get(criteria).appliedModules()).isZero();
  }
}
