package com.seed4j.statistic.infrastructure.secondary;

import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class InMemoryStatisticsResetter {

  @Autowired
  private InMemoryStatisticsRepository statistics;

  @Before
  public void resetStatistics() {
    statistics.clear();
  }
}
