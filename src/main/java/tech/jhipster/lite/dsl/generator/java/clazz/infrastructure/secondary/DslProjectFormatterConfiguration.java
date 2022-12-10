package tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.common.domain.Generated;

@Configuration
//@ImportRuntimeHints(NativeHints.class)
public class DslProjectFormatterConfiguration {

  private static final Logger log = LoggerFactory.getLogger(DslProjectFormatterConfiguration.class);

  @Bean
  @Generated(reason = "Jacoco think there is a missing case")
  public DslProjectFormatter dslProjectFormatter(DslNpmInstallationReader npmInstallation) {
    return switch (npmInstallation.get()) {
      case UNIX -> unixFormatter();
      case WINDOWS -> windowsFormatter();
      case NONE -> traceFormatter();
    };
  }

  private NpmProjectFormatter unixFormatter() {
    log.info("Using unix formatter");

    return new NpmProjectFormatter("npm");
  }

  private NpmProjectFormatter windowsFormatter() {
    log.info("Using windows formatter");

    return new NpmProjectFormatter("npm.cmd");
  }

  private TraceProjectFormatter traceFormatter() {
    log.info("Using trace formatter");

    return new TraceProjectFormatter();
  }
}
