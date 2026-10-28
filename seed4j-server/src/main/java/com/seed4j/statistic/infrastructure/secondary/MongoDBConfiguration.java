package com.seed4j.statistic.infrastructure.secondary;

import org.springframework.boot.mongodb.autoconfigure.MongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@WithMongoDB
@Configuration
@EnableMongoRepositories
@Import({ MongoAutoConfiguration.class })
class MongoDBConfiguration {}
