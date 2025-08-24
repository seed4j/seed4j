package com.seed4j.statistic.infrastructure.secondary;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@WithPostgreSQL
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.seed4j" }, enableDefaultTransactions = false)
class DatabaseConfiguration {}
