package com.seed4j.statistic.infrastructure.secondary;

import static com.seed4j.statistic.domain.AppliedModuleFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.IntegrationTest;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import com.seed4j.statistic.domain.AppliedModule;
import com.seed4j.statistic.domain.StatisticsRepository;
import com.seed4j.statistic.domain.criteria.StatisticsCriteria;
import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@IntegrationTest
@EnabledOnOs(OS.LINUX)
@ActiveProfiles("mongodb")
class MongoDBStatisticsRepositoryIT {

  private static MongoDBContainer mongoDbContainer;

  @Autowired
  private StatisticsRepository statistics;

  @BeforeAll
  @SuppressWarnings("resource")
  static void startMongo() {
    mongoDbContainer = new MongoDBContainer(DockerImageName.parse("mongo:5.0.11"))
      .withTmpFs(Collections.singletonMap("/testtmpfs", "rw"))
      .withCommand(
        """
        --wiredTigerCacheSizeGB 0.25 \
        --wiredTigerCollectionBlockCompressor none \
        --slowOpSampleRate 0 \
        --setParameter ttlMonitorEnabled=false \
        --setParameter diagnosticDataCollectionEnabled=false \
        --setParameter logicalSessionRefreshMillis=6000000 \
        --setParameter enableFlowControl=false \
        --setParameter oplogFetcherUsesExhaust=false \
        --setParameter disableResumableRangeDeleter=true \
        --setParameter enableShardedIndexConsistencyCheck=false \
        --setParameter enableFinerGrainedCatalogCacheRefresh=false \
        --setParameter readHedgingMode=off \
        --setParameter loadRoutingTableOnStartup=false \
        --setParameter rangeDeleterBatchDelayMS=2000000 \
        --setParameter skipShardingConfigurationChecks=true \
        --setParameter syncdelay=3600\
        """
      );

    mongoDbContainer.start();

    System.setProperty("TEST_MONGODB_URI", mongoDbContainer.getReplicaSetUrl());
  }

  @AfterAll
  static void stopMongo() {
    mongoDbContainer.stop();
  }

  static Stream<StatisticsCriteria> provideFilterCriteria() {
    return Stream.of(
      StatisticsCriteria.builder().startTime(null).endTime(null).moduleSlug(JHLiteModuleSlug.ANGULAR_CORE.get()).build(),
      StatisticsCriteria.builder().startTime(null).endTime(Instant.parse("2021-12-04T10:15:30.00Z")).moduleSlug(null).build(),
      StatisticsCriteria.builder().startTime(null).endTime(Instant.parse("2021-12-04T10:15:30.00Z")).moduleSlug(null).build()
    );
  }

  static Stream<StatisticsCriteria> provideFalseCriteria() {
    return Stream.of(
      StatisticsCriteria.builder()
        .startTime(Instant.parse("2020-12-03T10:15:30.00Z"))
        .endTime(Instant.parse("2022-12-03T10:15:30.00Z"))
        .moduleSlug(JHLiteModuleSlug.ANGULAR_CORE.get())
        .build(),
      StatisticsCriteria.builder()
        .startTime(Instant.parse("2022-12-03T10:15:30.00Z"))
        .endTime(Instant.parse("2022-12-03T10:15:30.00Z"))
        .moduleSlug(null)
        .build(),
      StatisticsCriteria.builder()
        .startTime(Instant.parse("2020-12-03T10:15:30.00Z"))
        .endTime(Instant.parse("2020-12-03T10:15:30.00Z"))
        .moduleSlug(null)
        .build()
    );
  }

  @Test
  void shouldGetStatisticsFromMongoInCloudEnvironment() {
    assertThat(statistics).isInstanceOf(MongoDBStatisticsRepository.class);
  }

  @Test
  void shouldUpdateAndGetStatistics() {
    statistics.save(appliedModule());
    assertThat(statistics.get(StatisticsCriteria.builder().build()).appliedModules()).isEqualTo(1);
  }

  @ParameterizedTest
  @MethodSource("provideFilterCriteria")
  void shouldGetOneForEachFilteredCriteria(StatisticsCriteria criteria) {
    AppliedModule appliedModule = appliedModule(JHLiteModuleSlug.ANGULAR_CORE.get());
    statistics.save(appliedModule);

    assertThat(statistics.get(criteria).appliedModules()).isEqualTo(1);
  }

  @ParameterizedTest
  @MethodSource("provideFalseCriteria")
  void shouldGetZeroForFalseCriteria(StatisticsCriteria criteria) {
    AppliedModule appliedModule = appliedModule();

    statistics.save(appliedModule);

    assertThat(statistics.get(criteria).appliedModules()).isZero();
  }
}
