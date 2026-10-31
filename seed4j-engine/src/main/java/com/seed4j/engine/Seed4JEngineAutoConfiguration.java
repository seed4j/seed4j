package com.seed4j.engine;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Starts the Seed4J engine in any Spring Boot application depending on seed4j-engine, with the modules found on the classpath.
 */
@AutoConfiguration
@ComponentScan({
  "com.seed4j.module.application",
  "com.seed4j.module.infrastructure.secondary",
  "com.seed4j.history",
  "com.seed4j.shared.npmdetector",
})
public class Seed4JEngineAutoConfiguration {}
