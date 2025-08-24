package com.seed4j.statistic.infrastructure.secondary;

import com.seed4j.shared.error.domain.Assert;
import com.seed4j.statistic.domain.AppliedModule;
import com.seed4j.statistic.domain.Statistics;
import com.seed4j.statistic.domain.StatisticsRepository;
import com.seed4j.statistic.domain.criteria.StatisticsCriteria;
import org.springframework.stereotype.Repository;

@Repository
@WithPostgreSQL
class PostgreSQLStatisticsRepository implements StatisticsRepository {

  private final JpaStatisticsRepository statistics;

  public PostgreSQLStatisticsRepository(JpaStatisticsRepository statistics) {
    this.statistics = statistics;
  }

  @Override
  public void save(AppliedModule moduleApplied) {
    Assert.notNull("moduleApplied", moduleApplied);

    statistics.save(AppliedModuleEntity.from(moduleApplied));
  }

  @Override
  public Statistics get(StatisticsCriteria criteria) {
    Assert.notNull("criteria", criteria);

    return new Statistics(statistics.count(StatisticsCriteriaSpecificationFactory.from(criteria)));
  }
}
