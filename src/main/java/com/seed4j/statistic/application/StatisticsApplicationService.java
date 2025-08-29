package com.seed4j.statistic.application;

import com.seed4j.statistic.domain.AppliedModule;
import com.seed4j.statistic.domain.Statistics;
import com.seed4j.statistic.domain.StatisticsRepository;
import com.seed4j.statistic.domain.criteria.StatisticsCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatisticsApplicationService {

  private final StatisticsRepository statistics;

  public StatisticsApplicationService(StatisticsRepository statistics) {
    this.statistics = statistics;
  }

  @Transactional
  public void moduleApplied(AppliedModule moduleApplied) {
    statistics.save(moduleApplied);
  }

  @Transactional(readOnly = true)
  public Statistics get(StatisticsCriteria criteria) {
    return statistics.get(criteria);
  }
}
