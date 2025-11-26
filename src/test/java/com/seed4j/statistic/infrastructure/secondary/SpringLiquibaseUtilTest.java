package com.seed4j.statistic.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.InstanceOfAssertFactories.*;

import com.seed4j.UnitTest;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.liquibase.autoconfigure.DataSourceClosingSpringLiquibase;
import org.springframework.boot.liquibase.autoconfigure.LiquibaseProperties;
import org.springframework.core.io.ClassPathResource;

@UnitTest
class SpringLiquibaseUtilTest {

  private static String datasourceUrl;

  @BeforeAll
  static void setup() {
    datasourceUrl = testProperties().getProperty("spring.datasource.url");
  }

  @Test
  void createSpringLiquibaseFromLiquibaseDataSource() {
    DataSource liquibaseDatasource = DataSourceBuilder.create().url(datasourceUrl).username("sa").build();
    LiquibaseProperties liquibaseProperties = null;
    DataSource normalDataSource = null;
    DataSourceProperties dataSourceProperties = null;

    SpringLiquibase liquibase = SpringLiquibaseUtil.createSpringLiquibase(
      liquibaseDatasource,
      liquibaseProperties,
      normalDataSource,
      dataSourceProperties
    );
    assertThat(liquibase)
      .isNotInstanceOf(DataSourceClosingSpringLiquibase.class)
      .extracting(SpringLiquibase::getDataSource)
      .isEqualTo(liquibaseDatasource)
      .asInstanceOf(type(HikariDataSource.class))
      .hasFieldOrPropertyWithValue("jdbcUrl", datasourceUrl)
      .hasFieldOrPropertyWithValue("username", "sa")
      .hasFieldOrPropertyWithValue("password", null);
  }

  @Test
  void createSpringLiquibaseFromNormalDataSource() {
    DataSource liquibaseDatasource = null;
    var liquibaseProperties = new LiquibaseProperties();
    DataSource normalDataSource = DataSourceBuilder.create().url(datasourceUrl).username("sa").build();
    DataSourceProperties dataSourceProperties = null;

    SpringLiquibase liquibase = SpringLiquibaseUtil.createSpringLiquibase(
      liquibaseDatasource,
      liquibaseProperties,
      normalDataSource,
      dataSourceProperties
    );
    assertThat(liquibase)
      .isNotInstanceOf(DataSourceClosingSpringLiquibase.class)
      .extracting(SpringLiquibase::getDataSource)
      .isEqualTo(normalDataSource)
      .asInstanceOf(type(HikariDataSource.class))
      .hasFieldOrPropertyWithValue("jdbcUrl", datasourceUrl)
      .hasFieldOrPropertyWithValue("username", "sa")
      .hasFieldOrPropertyWithValue("password", null);
  }

  @Test
  void createSpringLiquibaseFromLiquibaseProperties() {
    DataSource liquibaseDatasource = null;
    var liquibaseProperties = new LiquibaseProperties();
    liquibaseProperties.setUrl(datasourceUrl);
    liquibaseProperties.setUser("sa");
    DataSource normalDataSource = null;
    var dataSourceProperties = new DataSourceProperties();
    dataSourceProperties.setPassword("password");

    SpringLiquibase liquibase = SpringLiquibaseUtil.createSpringLiquibase(
      liquibaseDatasource,
      liquibaseProperties,
      normalDataSource,
      dataSourceProperties
    );
    assertThat(liquibase)
      .asInstanceOf(type(DataSourceClosingSpringLiquibase.class))
      .extracting(SpringLiquibase::getDataSource)
      .asInstanceOf(type(HikariDataSource.class))
      .hasFieldOrPropertyWithValue("jdbcUrl", datasourceUrl)
      .hasFieldOrPropertyWithValue("username", "sa")
      .hasFieldOrPropertyWithValue("password", "password");
  }

  private static Properties testProperties() {
    var yaml = new YamlPropertiesFactoryBean();
    yaml.setResources(new ClassPathResource("config/application-postgresql.yml"));
    return yaml.getObject();
  }
}
