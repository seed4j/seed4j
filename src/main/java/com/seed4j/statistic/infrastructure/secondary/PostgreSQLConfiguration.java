package com.seed4j.statistic.infrastructure.secondary;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@WithPostgreSQL
@Import({ DataSourceAutoConfiguration.class })
public class PostgreSQLConfiguration {}
