package com.seed4j.statistic.infrastructure.secondary;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
@WithPostgreSQL
interface JpaStatisticsRepository extends JpaRepository<AppliedModuleEntity, UUID>, JpaSpecificationExecutor<AppliedModuleEntity> {}
