package com.seed4j.project.infrastructure.primary;

import static com.seed4j.project.domain.history.ProjectHistoryFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.JsonHelper;
import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class RestProjectHistoryTest {

  @Test
  void shouldSerializeToJson() {
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
