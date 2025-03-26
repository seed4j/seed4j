package tech.jhipster.lite.statistic.infrastructure.primary;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.jhipster.lite.statistic.domain.StatisticsFixture.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.JsonHelper;
import tech.jhipster.lite.UnitTest;

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
