package tech.jhipster.lite.statistic.infrastructure.secondary;

import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.statistic.domain.AppliedModule;
import tech.jhipster.lite.statistic.domain.Statistics;
import tech.jhipster.lite.statistic.domain.StatisticsRepository;
import tech.jhipster.lite.statistic.domain.criteria.StatisticsCriteria;

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
  public Statistics get(@NotNull StatisticsCriteria criteria) {
    long appliedModulesCount = appliedModules.size();
    if (criteria.isAnyCriteriaApplied()) {
      appliedModulesCount =
      appliedModules
        .stream()
        .filter(am ->
          (criteria.getStartTime().isEmpty() || am.date().isAfter(criteria.getStartTime().get())) &&
          (criteria.getEndTime().isEmpty() || am.date().isBefore(criteria.getEndTime().get())) &&
          (criteria.getModuleSlug().isEmpty() || criteria.getModuleSlug().map(ms -> am.module().slug().equals(ms.get())).orElse(false))
        )
        .count();
    }
    return new Statistics(appliedModulesCount);
  }

  void clear() {
    appliedModules.clear();
  }
}
