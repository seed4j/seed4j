package tech.jhipster.lite.project.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.project.domain.history.ProjectHistoryFixture.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.JsonHelper;
import tech.jhipster.lite.UnitTest;

@UnitTest
class RestProjectHistoryTest {

  @Test
  void shouldSerializetoJson() {
    assertThat(JsonHelper.writeAsString(RestProjectHistory.from(projectHistory()))).isEqualTo(json());
  }

  private String json() {
    return """
    {"modules":\
    [\
    {"slug":"test-module"}\
    ],\
    "properties":{"key":"value"}\
    }\
    """;
  }
}
