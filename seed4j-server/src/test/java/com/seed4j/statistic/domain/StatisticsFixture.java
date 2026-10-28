package com.seed4j.statistic.domain;

public final class StatisticsFixture {

  private StatisticsFixture() {}

  public static Statistics statistics() {
    return new Statistics(42);
  }
}
