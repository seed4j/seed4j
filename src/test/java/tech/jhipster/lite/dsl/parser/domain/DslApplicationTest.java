package tech.jhipster.lite.dsl.parser.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.parser.domain.DslApplication;

@UnitTest
class DslApplicationTest {

  @Test
  void shouldBuildDefaultApplication() {
    DslApplication.DslApplicationBuilder builder = DslApplication.dslApplilcationBuilder();
    DslApplication dslApp = builder.build();
    assertNotNull(dslApp);
    assertNotNull(dslApp.getLstDslContext());
    assertTrue(dslApp.getLstDslContext().isEmpty());
  }
}
