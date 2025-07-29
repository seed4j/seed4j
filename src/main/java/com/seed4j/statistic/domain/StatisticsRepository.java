package com.seed4j.statistic.domain;

import com.seed4j.statistic.domain.criteria.StatisticsCriteria;

public interface StatisticsRepository {
  void save(AppliedModule moduleApplied);

  Statistics get(StatisticsCriteria criteria);
}
