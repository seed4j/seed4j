package tech.jhipster.lite.dsl.domain.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class ConfigAppTest {

  @Test
  void testToString() {
    ConfigApp config = ConfigApp.configBuilder().build();

    assertTrue(config.toString().contains("jhlite"));
    assertTrue(config.toString().contains("com.mycompany.myapp"));
    assertTrue(config.toString().contains("true"));
    assertTrue(config.toString().contains("domain"));
    assertTrue(config.toString().contains("infrastructure"));
    assertTrue(config.toString().contains("primary"));
    assertTrue(config.toString().contains("secondary"));
    assertTrue(config.toString().contains("/tmp"));
  }
}
