package tech.jhipster.lite.wire.security.infrastructure.primary;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.CorsFilter;
import tech.jhipster.lite.IntegrationTest;

@IntegrationTest
class CorsFilterConfigurationIT {

  @Nested
  @IntegrationTest(
    properties = {
      "application.cors.allowed-origins=http://localhost:8100,http://localhost:9000", "application.cors.allowed-origin-patterns=",
    }
  )
  class CorsFilterDefault {

    @Autowired
    CorsFilter corsFilter;

    @Test
    void shouldCorsFilter() {
      assertThat(corsFilter).isNotNull();
    }
  }

  @Nested
  @IntegrationTest(properties = { "application.cors.allowed-origins=", "application.cors.allowed-origin-patterns=" })
  class CorsFilterEmpty {

    @Autowired
    CorsFilter corsFilter;

    @Test
    void shouldCorsFilter() {
      assertThat(corsFilter).isNotNull();
    }
  }

  @Nested
  @IntegrationTest(
    properties = {
      "application.cors.allowed-origins=", "application.cors.allowed-origin-patterns=http://localhost:8100,http://localhost:9000",
    }
  )
  class CorsFilterWithAllowedOriginPatterns {

    @Autowired
    CorsFilter corsFilter;

    @Test
    void shouldCorsFilter() {
      assertThat(corsFilter).isNotNull();
    }
  }
}
