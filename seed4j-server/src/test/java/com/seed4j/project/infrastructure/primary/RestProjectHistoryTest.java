package com.seed4j.project.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.JsonHelper;
import com.seed4j.UnitTest;
import com.seed4j.project.domain.ModuleSlug;
import com.seed4j.project.domain.history.ModuleParameters;
import com.seed4j.project.domain.history.ProjectHistory;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class RestProjectHistoryTest {

  @Test
  void shouldSerializeToJson() {
    assertThat(JsonHelper.writeAsString(RestProjectHistory.from(projectHistory()))).isEqualTo(json());
  }

  private static ProjectHistory projectHistory() {
    return new ProjectHistory(List.of(new ModuleSlug("test-module")), new ModuleParameters(Map.of("key", "value")));
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
