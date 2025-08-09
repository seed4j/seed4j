package com.seed4j.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Indentation;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class SeedModulePropertiesTest {

  private final Instant today = Instant.now();
  private final Instant yesterday = Instant.now().minus(1, ChronoUnit.DAYS);

  @Nested
  @DisplayName("Mandatory String")
  class SeedModulePropertiesMandatoryStringTest {

    @Test
    void shouldNotGetUnknownProperty() {
      assertThatThrownBy(() -> properties().getString("unknown")).isExactlyInstanceOf(UnknownPropertyException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = { "boolean", "integer" })
    void shouldNotGetInvalidType(String key) {
      assertThatThrownBy(() -> properties().getString(key)).isExactlyInstanceOf(InvalidPropertyTypeException.class);
    }

    @Test
    void shouldGetProperty() {
      assertThat(properties().getString("string")).isEqualTo("value");
    }
  }

  @Nested
  @DisplayName("Optional String")
  class SeedModulePropertiesOptionalStringTest {

    @Test
    void shouldNotGetDefaultValueForUnknownProperty() {
      assertThat(properties().getOrDefaultString("unknown", "default")).isEqualTo("default");
    }

    @ParameterizedTest
    @ValueSource(strings = { "boolean", "integer" })
    void shouldNotGetInvalidType(String key) {
      assertThatThrownBy(() -> properties().getOrDefaultString(key, "default")).isExactlyInstanceOf(InvalidPropertyTypeException.class);
    }

    @Test
    void shouldGetDefaultForBlankValue() {
      assertThat(properties().getOrDefaultString("blank", "default")).isEqualTo("default");
    }

    @Test
    void shouldGetProperty() {
      assertThat(properties().getOrDefaultString("string", "default")).isEqualTo("value");
    }
  }

  @Nested
  @DisplayName("Mandatory Boolean")
  class SeedModulePropertiesMandatoryBooleanTest {

    @Test
    void shouldNotGetUnknownProperty() {
      assertThatThrownBy(() -> properties().getBoolean("unknown")).isExactlyInstanceOf(UnknownPropertyException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = { "string", "integer" })
    void shouldNotGetInvalidType(String key) {
      assertThatThrownBy(() -> properties().getBoolean(key)).isExactlyInstanceOf(InvalidPropertyTypeException.class);
    }

    @Test
    void shouldGetProperty() {
      assertThat(properties().getBoolean("boolean")).isTrue();
    }
  }

  @Nested
  @DisplayName("Optional Boolean")
  class SeedModulePropertiesOptionalBooleanTest {

    @Test
    void shouldNotGetUnknownProperty() {
      assertThat(properties().getOrDefaultBoolean("unknown", true)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = { "string", "integer" })
    void shouldNotGetInvalidType(String key) {
      assertThatThrownBy(() -> properties().getOrDefaultBoolean(key, true)).isExactlyInstanceOf(InvalidPropertyTypeException.class);
    }

    @Test
    void shouldGetProperty() {
      assertThat(properties().getOrDefaultBoolean("boolean", false)).isTrue();
    }
  }

  @Nested
  @DisplayName("Mandatory Integer")
  class SeedModulePropertiesMandatoryIntegerTest {

    @Test
    void shouldNotGetUnknownProperty() {
      assertThatThrownBy(() -> properties().getInteger("unknown")).isExactlyInstanceOf(UnknownPropertyException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = { "string", "boolean" })
    void shouldNotGetInvalidType(String key) {
      assertThatThrownBy(() -> properties().getInteger(key)).isExactlyInstanceOf(InvalidPropertyTypeException.class);
    }

    @Test
    void shouldGetProperty() {
      assertThat(properties().getInteger("integer")).isEqualTo(42);
    }
  }

  @Nested
  @DisplayName("Optional Integer")
  class SeedModulePropertiesOptionalIntegerTest {

    @Test
    void shouldNotGetUnknownProperty() {
      assertThat(properties().getOrDefaultInteger("unknown", 42)).isEqualTo(42);
    }

    @ParameterizedTest
    @ValueSource(strings = { "string", "boolean" })
    void shouldNotGetInvalidType(String key) {
      assertThatThrownBy(() -> properties().getOrDefaultInteger(key, 42)).isExactlyInstanceOf(InvalidPropertyTypeException.class);
    }

    @Test
    void shouldGetProperty() {
      assertThat(properties().getOrDefaultInteger("integer", 42)).isEqualTo(42);
    }
  }

  @Nested
  @DisplayName("Instant")
  class SeedModulePropertiesInstantTest {

    @ParameterizedTest
    @ValueSource(strings = { "string", "boolean", "integer" })
    void shouldNotGetInvalidType(String key) {
      assertThatThrownBy(() -> properties().getInstantOrDefault(key, today)).isExactlyInstanceOf(InvalidPropertyTypeException.class);
    }

    @Test
    void shouldGetProperty() {
      assertThat(properties().getInstantOrDefault("instant", yesterday)).isEqualTo(today);
    }

    @Test
    void shouldGetUnknownProperty() {
      assertThat(properties().getInstantOrDefault("unknown", yesterday)).isEqualTo(yesterday);
    }
  }

  @Test
  void shouldGetDefaultProjectProperties() {
    SeedModuleProperties properties = properties();

    assertThat(properties.indentation()).isEqualTo(Indentation.DEFAULT);
    assertThat(properties.basePackage()).isEqualTo(SeedBasePackage.DEFAULT);
    assertThat(properties.projectName()).isEqualTo(SeedProjectName.DEFAULT);
    assertThat(properties.projectBaseName()).isEqualTo(SeedProjectBaseName.DEFAULT);
  }

  @Test
  void testToStringShowsProjectName() {
    SeedModuleProperties properties = properties();
    assertThat(properties).hasToString(properties.projectName().toString());
  }

  private SeedModuleProperties properties() {
    return new SeedModuleProperties(
      "/tmp/folder",
      false,
      Map.of("string", "value", "boolean", true, "integer", 42, "blank", " ", "instant", today.toString())
    );
  }
}
