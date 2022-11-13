package tech.jhipster.lite.error.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class BadRequestAlertExceptionTest {

  @Test
  void shouldBuild() {
    BadRequestAlertException exception = new BadRequestAlertException("chips", "beer", "validation");

    assertThat(exception.getBody().getTitle()).isEqualTo("chips");
    assertThat(exception.getEntityName()).isEqualTo("beer");
    assertThat(exception.getErrorKey()).isEqualTo("validation");
    assertThat(exception.getBody().getProperties())
      .containsExactlyInAnyOrderEntriesOf(Map.of("message", ErrorConstants.ERR_VALIDATION, "params", "beer"));
  }
}
