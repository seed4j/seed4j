package com.seed4j.statistic.infrastructure.secondary;

import static com.seed4j.statistic.domain.AppliedModuleFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.IntegrationTest;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;
import com.seed4j.statistic.domain.AppliedModule;
import com.seed4j.statistic.domain.StatisticsRepository;
import com.seed4j.statistic.domain.criteria.StatisticsCriteria;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@IntegrationTest
@EnabledOnOs(OS.LINUX)
@ActiveProfiles("mongodb")
@Import(MongoDBStatisticsRepositoryIT.TestMongoConfig.class)
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

  @TestConfiguration
  static class TestMongoConfig {

    @Bean
    public MongoDatabaseFactory mongoDbFactory(@Value("${spring.data.mongodb.uri}") String connectionString) {
      return new SimpleMongoClientDatabaseFactory(connectionString + "?uuidRepresentation=STANDARD");
    }
  }

  @AfterAll
  static void stopMongo() {
    mongoDbContainer.stop();
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
  @MethodSource("matchingCriteria")
  void shouldGetOneForEachFilteredCriteria(StatisticsCriteria criteria) {
    AppliedModule appliedModule = appliedModule(Seed4JCoreModuleSlug.ANGULAR_CORE.get());
    statistics.save(appliedModule);

    assertThat(statistics.get(criteria).appliedModules()).isEqualTo(1);
  }

  @ParameterizedTest
  @MethodSource("notMatchingCriteria")
  void shouldGetZeroForFalseCriteria(StatisticsCriteria criteria) {
    AppliedModule appliedModule = appliedModule();

    statistics.save(appliedModule);

    assertThat(statistics.get(criteria).appliedModules()).isZero();
  }

  static Stream<StatisticsCriteria> matchingCriteria() {
    return StatisticsCriteriaFixture.matchingCriteria();
  }

  static Stream<StatisticsCriteria> notMatchingCriteria() {
    return StatisticsCriteriaFixture.notMatchingCriteria();
  }
}
