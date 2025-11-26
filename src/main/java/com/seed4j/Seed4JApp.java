package com.seed4j;

import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.mongodb.autoconfigure.MongoAutoConfiguration;
import org.springframework.core.env.Environment;

@ExcludeFromGeneratedCodeCoverage(reason = "Not testing logs")
@SpringBootApplication(exclude = { MongoAutoConfiguration.class, DataSourceAutoConfiguration.class })
public class Seed4JApp {

  private static final Logger log = LoggerFactory.getLogger(Seed4JApp.class);

  public static void main(String[] args) {
    Environment env = SpringApplication.run(Seed4JApp.class, args).getEnvironment();

    if (log.isInfoEnabled()) {
      log.info(ApplicationStartupTraces.of(env));
    }
  }
}
