package com.seed4j.statistic.infrastructure.secondary;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@WithMongoDB
@Repository
interface SpringDataMongoDBStatisticsRepository extends CrudRepository<AppliedModuleDocument, UUID> {}
