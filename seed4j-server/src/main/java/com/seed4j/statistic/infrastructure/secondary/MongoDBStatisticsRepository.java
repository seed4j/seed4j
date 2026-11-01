package com.seed4j.statistic.infrastructure.secondary;

import com.seed4j.shared.error.domain.Assert;
import com.seed4j.statistic.domain.*;
import com.seed4j.statistic.domain.criteria.StatisticsCriteria;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

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
  public Statistics get(StatisticsCriteria criteria) {
    Assert.notNull("criteria", criteria);

    if (criteria.isAnyCriteriaApplied()) {
      Query query = generateQuery(criteria);
      return new Statistics(mongoTemplate.count(query, AppliedModuleDocument.class));
    }
    return new Statistics(statistics.count());
  }

  private Query generateQuery(@NotNull StatisticsCriteria criteria) {
    List<Criteria> criteriaList = new ArrayList<>();
    criteria.startTime().ifPresent(startTime -> criteriaList.add(Criteria.where("date").gte(startTime)));
    criteria.endTime().ifPresent(endTime -> criteriaList.add(Criteria.where("date").lte(endTime)));
    criteria.moduleSlug().ifPresent(moduleSlug -> criteriaList.add(Criteria.where("moduleSlug").is(moduleSlug.get())));

    return new Query(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
  }
}
