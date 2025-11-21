package com.seed4j.statistic.infrastructure.secondary;

import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.util.Optional;
import java.util.function.Supplier;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.liquibase.autoconfigure.DataSourceClosingSpringLiquibase;
import org.springframework.boot.liquibase.autoconfigure.LiquibaseProperties;

/**
 * Utility class for handling SpringLiquibase.
 *
 * <p>
 * It follows implementation of
 * <a href="https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/liquibase/LiquibaseAutoConfiguration.java">LiquibaseAutoConfiguration</a>.
 */
final class SpringLiquibaseUtil {

  private SpringLiquibaseUtil() {}

  /**
   * <p>createSpringLiquibase.</p>
   *
   * @param liquibaseDatasource a {@link DataSource} object.
   * @param liquibaseProperties a {@link LiquibaseProperties} object.
   * @param dataSource a {@link DataSource} object.
   * @param dataSourceProperties a {@link DataSourceProperties} object.
   * @return a {@link liquibase.integration.spring.SpringLiquibase} object.
   */
  public static SpringLiquibase createSpringLiquibase(
    DataSource liquibaseDatasource,
    LiquibaseProperties liquibaseProperties,
    DataSource dataSource,
    DataSourceProperties dataSourceProperties
  ) {
    SpringLiquibase liquibase;
    var liquibaseDataSource = getDataSource(liquibaseDatasource, liquibaseProperties, dataSource);
    if (liquibaseDataSource != null) {
      liquibase = new SpringLiquibase();
      liquibase.setDataSource(liquibaseDataSource);
      return liquibase;
    }
    liquibase = new DataSourceClosingSpringLiquibase();
    liquibase.setDataSource(createNewDataSource(liquibaseProperties, dataSourceProperties));
    return liquibase;
  }

  @ExcludeFromGeneratedCodeCoverage
  private static DataSource getDataSource(DataSource liquibaseDataSource, LiquibaseProperties liquibaseProperties, DataSource dataSource) {
    if (liquibaseDataSource != null) {
      return liquibaseDataSource;
    }
    if (liquibaseProperties.getUrl() == null && liquibaseProperties.getUser() == null) {
      return dataSource;
    }
    return null;
  }

  private static DataSource createNewDataSource(LiquibaseProperties liquibaseProperties, DataSourceProperties dataSourceProperties) {
    String url = getProperty(liquibaseProperties::getUrl, dataSourceProperties::determineUrl);
    String user = getProperty(liquibaseProperties::getUser, dataSourceProperties::determineUsername);
    String password = getProperty(liquibaseProperties::getPassword, dataSourceProperties::determinePassword);
    return DataSourceBuilder.create().url(url).username(user).password(password).build();
  }

  private static String getProperty(Supplier<String> property, Supplier<String> defaultValue) {
    return Optional.of(property).map(Supplier::get).orElseGet(defaultValue);
  }
}
