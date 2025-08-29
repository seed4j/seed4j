package com.seed4j.statistic.infrastructure.secondary;

import com.seed4j.statistic.domain.criteria.StatisticsCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;

final class StatisticsCriteriaSpecificationFactory {

  private StatisticsCriteriaSpecificationFactory() {}

  public static Specification<AppliedModuleEntity> from(StatisticsCriteria criteria) {
    return (root, query, builder) -> {
      List<Predicate> specifications = new ArrayList<>();

      startTimePredicate(criteria, root, builder).ifPresent(specifications::add);
      endTimePredicate(criteria, root, builder).ifPresent(specifications::add);
      modulePredicate(criteria, root, builder).ifPresent(specifications::add);

      return builder.and(specifications.toArray(new Predicate[0]));
    };
  }

  private static Optional<Predicate> startTimePredicate(
    StatisticsCriteria criteria,
    Root<AppliedModuleEntity> root,
    CriteriaBuilder builder
  ) {
    return criteria.startTime().map(start -> builder.greaterThanOrEqualTo(root.get("date"), start));
  }

  private static Optional<Predicate> endTimePredicate(
    StatisticsCriteria criteria,
    Root<AppliedModuleEntity> root,
    CriteriaBuilder builder
  ) {
    return criteria.endTime().map(end -> builder.lessThanOrEqualTo(root.get("date"), end));
  }

  private static Optional<Predicate> modulePredicate(StatisticsCriteria criteria, Root<AppliedModuleEntity> root, CriteriaBuilder builder) {
    return criteria.moduleSlug().map(slug -> builder.equal(root.get("module"), slug.get()));
  }
}
