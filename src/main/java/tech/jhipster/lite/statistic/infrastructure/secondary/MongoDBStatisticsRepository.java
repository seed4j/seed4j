package tech.jhipster.lite.statistic.infrastructure.secondary;

import org.springframework.stereotype.Repository;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.statistic.domain.AppliedModule;
import tech.jhipster.lite.statistic.domain.Statistics;
import tech.jhipster.lite.statistic.domain.StatisticsRepository;

@WithMongoDB
@Repository
class MongoDBStatisticsRepository implements StatisticsRepository {

  private final SpringDataMongoDBStatisticsRepository statistics;

  MongoDBStatisticsRepository(SpringDataMongoDBStatisticsRepository statistics) {
    this.statistics = statistics;
  }

  @Override
  public void save(AppliedModule moduleApplied) {
    Assert.notNull("moduleApplied", moduleApplied);

    statistics.save(AppliedModuleDocument.from(moduleApplied));
  }

  @Override
  public Statistics get() {
    return new Statistics(statistics.count());
  }
}
