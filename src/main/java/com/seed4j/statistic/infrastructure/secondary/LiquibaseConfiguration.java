package com.seed4j.statistic.infrastructure.secondary;

import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Configuration
@WithPostgreSQL
@EnableConfigurationProperties({ LiquibaseProperties.class })
@ConditionalOnProperty(prefix = "spring.liquibase", name = { "enabled" }, matchIfMissing = true)
class LiquibaseConfiguration {

  @Bean
  @ExcludeFromGeneratedCodeCoverage(reason = "Not testing all liquibase properties configuration")
  public SpringLiquibase liquibase(
    @LiquibaseDataSource ObjectProvider<DataSource> liquibaseDataSource,
    LiquibaseProperties liquibaseProperties,
    ObjectProvider<DataSource> dataSource,
    DataSourceProperties dataSourceProperties
  ) {
    SpringLiquibase liquibase;
    liquibase = SpringLiquibaseUtil.createSpringLiquibase(
      liquibaseDataSource.getIfAvailable(),
      liquibaseProperties,
      dataSource.getIfUnique(),
      dataSourceProperties
    );

    liquibase.setChangeLog(liquibaseProperties.getChangeLog());
    if (!CollectionUtils.isEmpty(liquibaseProperties.getContexts())) {
      liquibase.setContexts(StringUtils.collectionToCommaDelimitedString(liquibaseProperties.getContexts()));
    }
    liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
    liquibase.setLiquibaseSchema(liquibaseProperties.getLiquibaseSchema());
    liquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
    liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
    liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
    liquibase.setDropFirst(liquibaseProperties.isDropFirst());
    if (!CollectionUtils.isEmpty(liquibaseProperties.getLabelFilter())) {
      liquibase.setContexts(StringUtils.collectionToCommaDelimitedString(liquibaseProperties.getLabelFilter()));
    }
    liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
    liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
    liquibase.setTestRollbackOnUpdate(liquibaseProperties.isTestRollbackOnUpdate());
    liquibase.setShouldRun(liquibaseProperties.isEnabled());

    liquibase.setAnalyticsEnabled(liquibaseProperties.getAnalyticsEnabled());
    liquibase.setLicenseKey(liquibaseProperties.getLicenseKey());

    return liquibase;
  }
}
