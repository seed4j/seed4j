package com.seed4j.statistic.infrastructure.primary;

import static com.seed4j.statistic.domain.StatisticsFixture.*;
import static org.assertj.core.api.Assertions.*;

import com.seed4j.JsonHelper;
import com.seed4j.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class RestStatisticsTest {

  @Test
  void shouldSerializeToJson() {
    assertThat(JsonHelper.writeAsString(RestStatistics.from(statistics()))).isEqualTo(json());
  }

  private String json() {
    return "{\"appliedModules\":42}";
  }
}
