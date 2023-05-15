package tech.jhipster.lite.statistic.infrastructure.secondary;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.shared.error.domain.Assert;
import tech.jhipster.lite.statistic.domain.AppliedModule;
import tech.jhipster.lite.statistic.domain.Statistics;
import tech.jhipster.lite.statistic.domain.StatisticsRepository;
import tech.jhipster.lite.statistic.domain.criteria.StatisticsCriteria;

@WithMongoDB
@Repository
class MongoDBStatisticsRepository implements StatisticsRepository {

  private final SpringDataMongoDBStatisticsRepository statistics;
  private final MongoTemplate mongoTemplate;

  MongoDBStatisticsRepository(SpringDataMongoDBStatisticsRepository statistics, MongoTemplate mongoTemplate) {
    this.statistics = statistics;
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void save(AppliedModule moduleApplied) {
    Assert.notNull("moduleApplied", moduleApplied);

    statistics.save(AppliedModuleDocument.from(moduleApplied));
  }

  @Override
  public Statistics get(@NotNull StatisticsCriteria criteria) {
    if (criteria.isAnyCriteriaApplied()) {
      Query query = generateQuery(criteria);
      return new Statistics(mongoTemplate.count(query, AppliedModuleDocument.class));
    }
    return new Statistics(statistics.count());
  }

  private Query generateQuery(@NotNull StatisticsCriteria criteria) {
    List<Criteria> criteriaList = new ArrayList<>();
    criteria.getStartTime().ifPresent(startTime -> criteriaList.add(Criteria.where("date").gte(startTime)));
    criteria.getEndTime().ifPresent(endTime -> criteriaList.add(Criteria.where("date").lte(endTime)));
    criteria.getModuleSlug().ifPresent(moduleSlug -> criteriaList.add(Criteria.where("moduleSlug").is(moduleSlug.get())));

    return new Query(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
  }
}
