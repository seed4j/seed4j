package com.seed4j.statistic.infrastructure.secondary;

import com.seed4j.module.domain.SeedModuleSlug;
import com.seed4j.shared.error.domain.Assert;
import com.seed4j.statistic.domain.AppliedModule;
import com.seed4j.statistic.domain.Statistics;
import com.seed4j.statistic.domain.StatisticsRepository;
import com.seed4j.statistic.domain.criteria.StatisticsCriteria;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import org.springframework.stereotype.Repository;

@Repository
@WithoutMongoDB
class InMemoryStatisticsRepository implements StatisticsRepository {

  private final Collection<AppliedModule> appliedModules = Collections.newSetFromMap(new ConcurrentHashMap<>());

  @Override
  public void save(AppliedModule moduleApplied) {
    Assert.notNull("moduleApplied", moduleApplied);

    appliedModules.add(moduleApplied);
  }

  @Override
  public Statistics get(StatisticsCriteria criteria) {
    Assert.notNull("criteria", criteria);

    long appliedModulesCount = appliedModules.size();
    if (criteria.isAnyCriteriaApplied()) {
      appliedModulesCount = appliedModules
        .stream()
        .filter(isAfter(criteria.startTime()))
        .filter(isBefore(criteria.endTime()))
        .filter(hasModuleSlug(criteria.moduleSlug()))
        .count();
    }
    return new Statistics(appliedModulesCount);
  }

  private static Predicate<AppliedModule> isAfter(Optional<Instant> startTime) {
    return appliedModule -> startTime.map(start -> appliedModule.date().isAfter(start)).orElse(true);
  }

  private static Predicate<AppliedModule> isBefore(Optional<Instant> endTime) {
    return appliedModule -> endTime.map(end -> appliedModule.date().isBefore(end)).orElse(true);
  }

  private static Predicate<AppliedModule> hasModuleSlug(Optional<SeedModuleSlug> moduleSlug) {
    return appliedModule ->
      moduleSlug
        .map(SeedModuleSlug::get)
        .map(slug -> appliedModule.module().slug().equals(slug))
        .orElse(true);
  }

  void clear() {
    appliedModules.clear();
  }
}
