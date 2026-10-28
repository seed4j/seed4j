package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.shared.error.domain.ErrorStatus;
import org.junit.jupiter.api.Test;

@UnitTest
class InvalidPropertyTypeExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    InvalidPropertyTypeException exception = InvalidPropertyTypeException.builder()
      .key("propertyKey")
      .expectedType(String.class)
      .actualType(Integer.class);

    assertThat(exception.getMessage()).isEqualTo(
      "Can't get property propertyKey, expecting class java.lang.String but is a class java.lang.Integer"
    );
    assertThat(exception.key()).isEqualTo(PropertiesErrorKey.INVALID_PROPERTY_TYPE);
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
    assertThat(exception.parameters()).containsOnly(
      entry("propertyKey", "propertyKey"),
      entry("expectedType", "java.lang.String"),
      entry("actualType", "java.lang.Integer")
    );
  }
}
