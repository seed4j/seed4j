package com.seed4j.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.assertThat;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.properties.Seed4JPropertyType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class OpenApiFieldTypeTest {

  @ParameterizedTest
  @CsvSource({ "BOOLEAN,BOOLEAN", "INTEGER,INTEGER", "STRING,STRING" })
  void shouldGetFieldTypeFromPropertiesType(Seed4JPropertyType propertyType, OpenApiFieldType expectedOpenApiFieldType) {
    assertThat(OpenApiFieldType.from(propertyType)).isEqualTo(expectedOpenApiFieldType);
  }
}
