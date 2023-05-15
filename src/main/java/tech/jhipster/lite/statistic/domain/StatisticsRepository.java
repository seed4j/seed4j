package tech.jhipster.lite.statistic.domain;

import tech.jhipster.lite.statistic.domain.criteria.StatisticsCriteria;

public interface StatisticsRepository {
  void save(AppliedModule moduleApplied);

  Statistics get(StatisticsCriteria criteria);
}
