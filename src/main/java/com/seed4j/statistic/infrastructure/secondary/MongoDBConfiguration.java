package com.seed4j.statistic.infrastructure.secondary;

import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@WithMongoDB
@Configuration
@EnableMongoRepositories
@Import({ MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
class MongoDBConfiguration {}
