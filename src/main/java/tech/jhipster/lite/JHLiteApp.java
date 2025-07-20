package tech.jhipster.lite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.core.env.Environment;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@ExcludeFromGeneratedCodeCoverage(reason = "Not testing logs")
@SpringBootApplication(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
public class JHLiteApp {

  private static final Logger log = LoggerFactory.getLogger(JHLiteApp.class);

  public static void main(String[] args) {
    Environment env = SpringApplication.run(JHLiteApp.class, args).getEnvironment();

    if (log.isInfoEnabled()) {
      // log.info(ApplicationStartupTraces.of(env)); -> TODO: remove me, just for testing in CI
      log.info(ApplicationStartupTraces.of(env));
    }
  }
}
