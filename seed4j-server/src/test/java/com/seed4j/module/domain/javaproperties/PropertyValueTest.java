package com.seed4j.module.domain.javaproperties;

import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

@UnitTest
class PropertyValueTest {

  @Test
  void shouldRespectEqualsContract() {
    EqualsVerifier.forClass(PropertyValue.class).verify();
  }

  @Test
  void shouldGetValuesAsString() {
    assertThat(propertyValue("alpha", "beta")).hasToString("PropertyValue[values=[alpha, beta]]");
  }
}
