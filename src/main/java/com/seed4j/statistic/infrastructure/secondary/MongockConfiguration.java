package com.seed4j.statistic.infrastructure.secondary;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

@WithMongoDB
@Configuration
@EnableMongock
@AutoConfigureAfter(MongoDBConfiguration.class)
class MongockConfiguration {}
