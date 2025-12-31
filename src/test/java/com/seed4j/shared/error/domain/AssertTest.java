package com.seed4j.shared.error.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.seed4j.UnitTest;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
@SuppressWarnings("NullAway")
class AssertTest {

  private static final String FIELD_NAME = "fieldName";

  private static final String NOT_NULL_OR_EMPTY = "NotNullOrEmpty";

  @Test
  void shouldNotValidateNullInputs() {
    assertThatThrownBy(() -> Assert.notNull("field", null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("\"field\"");
  }

  @Test
  void shouldValidateNonNull() {
    assertThatCode(() -> Assert.notNull("field", NOT_NULL_OR_EMPTY)).doesNotThrowAnyException();
  }

  @Test
  void shouldNotValidateNullString() {
    assertThatThrownBy(() -> Assert.notBlank("field", null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("\"field\"")
      .hasMessageContaining("(null)");
  }

  @Test
  void shouldNotValidateEmptyString() {
    assertNotBlankString("");
  }

  @Test
  void shouldNotValidateSpaceString() {
    assertNotBlankString(" ");
  }

  @Test
  void shouldNotValidateTabString() {
    assertNotBlankString("\t");
  }

  @Test
  void shouldNotValidateStringWithWhitespace() {
    assertThatThrownBy(() -> Assert.noWhitespace("field", "my tag"))
      .isExactlyInstanceOf(StringWithWhitespacesException.class)
      .hasMessageContaining("\"field\"")
      .hasMessageContaining("contains at least one space");
  }

  @Test
  void shouldValidateStringWithoutWhitespace() {
    assertThatCode(() -> Assert.noWhitespace("field", NOT_NULL_OR_EMPTY)).doesNotThrowAnyException();
  }

  @Test
  void shouldValidateNonBlank() {
    assertThatCode(() -> Assert.notBlank("field", NOT_NULL_OR_EMPTY)).doesNotThrowAnyException();
  }

  private void assertNotBlankString(String input) {
    assertThatThrownBy(() -> Assert.notBlank("field", input))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("\"field\"")
      .hasMessageContaining("(blank)");
  }

  @Test
  void shouldNotValidateEmptyCollection() {
    List<String> list = List.of();
    assertThatThrownBy(() -> Assert.notEmpty("field", list))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("empty");
  }

  @Test
  void shouldValidateNotEmptyCollection() {
    assertThatCode(() -> Assert.notEmpty("field", List.of("Hello"))).doesNotThrowAnyException();
  }

  @Nested
  @DisplayName("String")
  class AssertStringTest {

    @Test
    void shouldNotValidateNullStringAsNotNull() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (String) null).notNull())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("(null)");
    }

    @Test
    void shouldValidateActualStringAsNotNull() {
      assertThatCode(() -> Assert.field(FIELD_NAME, "").notNull()).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullStringAsNotBlank() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (String) null).notBlank())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("(null)");
    }

    @Test
    void shouldNotValidateBlankStringAsNotBlank() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, " ").notBlank())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("(blank)");
    }

    @Test
    void shouldValidateStringWithValueAsNotBlank() {
      assertThatCode(() -> Assert.field(FIELD_NAME, "value").notBlank()).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateTooShortStringValue() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, "value").minLength(6))
        .isExactlyInstanceOf(StringTooShortException.class)
        .hasMessageContaining(String.valueOf(6))
        .hasMessageContaining(String.valueOf("value".length()))
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateStringNotMatchingPattern() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, "mytag?").matchesPattern(Pattern.compile("^[a-z0-9-]+$")))
        .isExactlyInstanceOf(NotMatchingExpectedPatternException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("The value \"mytag?\" in field \"fieldName\" must match the pattern ^[a-z0-9-]+$");
    }

    @Test
    void shouldValidateStringNotMatchingPattern() {
      assertThatCode(() -> Assert.field(FIELD_NAME, "my-tag").matchesPattern(Pattern.compile("^[a-z0-9-]+$"))).doesNotThrowAnyException();
    }

    @Test
    void shouldValidateNullStringWhenVerifyingMatchingPattern() {
      assertThatCode(() ->
        Assert.field(FIELD_NAME, (String) null).matchesPattern(Pattern.compile("^[a-z0-9-]+$"))
      ).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullStringWithLength() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (String) null).minLength(1))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 0 })
    void shouldValidateZeroOrNegativeMinLengthWithStringValue(int minLength) {
      assertThatCode(() -> Assert.field(FIELD_NAME, "value").minLength(minLength)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 0 })
    void shouldValidateZeroOrNegativeMinLengthForNullInput(int minLength) {
      assertThatCode(() -> Assert.field(FIELD_NAME, (String) null).minLength(minLength)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = { 4, 5 })
    void shouldValidateLongEnoughString(int minLength) {
      assertThatCode(() -> Assert.field(FIELD_NAME, "value").minLength(minLength)).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateTooLongStringValue() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, "value").maxLength(4))
        .isExactlyInstanceOf(StringTooLongException.class)
        .hasMessageContaining(String.valueOf(4))
        .hasMessageContaining(String.valueOf("value".length()))
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldValidateNullInputUnderMaxLength() {
      assertThatCode(() -> Assert.field(FIELD_NAME, (String) null).maxLength(5)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = { 5, 6 })
    void shouldValidateShortEnoughString(int maxLength) {
      assertThatCode(() -> Assert.field(FIELD_NAME, "value").maxLength(maxLength)).doesNotThrowAnyException();
    }
  }

  @Nested
  @DisplayName("Integer")
  class AssertIntegerTest {

    @Test
    void shouldNotValidateNullAsPositive() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Integer) null).positive())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateNegativeValueAsPositive() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, -4).positive())
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("0")
        .hasMessageContaining("-4");
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 42 })
    void shouldValidatePositiveValuesAsPositive(int value) {
      assertThatCode(() -> Assert.field(FIELD_NAME, value).positive()).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullAsOverMin() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Integer) null).min(0))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateValueUnderMin() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, 42).min(1337))
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("42")
        .hasMessageContaining("1337");
    }

    @ParameterizedTest
    @ValueSource(ints = { 41, 42 })
    void shouldValidateValueOverMin(int min) {
      assertThatCode(() -> Assert.field(FIELD_NAME, 42).min(min)).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullAsUnderMax() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Integer) null).max(42))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateValueOverMax() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, 42).max(12))
        .isExactlyInstanceOf(NumberValueTooHighException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("12")
        .hasMessageContaining("42");
    }

    @ParameterizedTest
    @ValueSource(ints = { 42, 43 })
    void shouldValidateValueUnderMax(int max) {
      assertThatCode(() -> Assert.field(FIELD_NAME, 42).max(max)).doesNotThrowAnyException();
    }
  }

  @Nested
  @DisplayName("Long")
  class AssertLongTest {

    @Test
    void shouldNotValidateNullAsPositive() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Long) null).positive())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateNegativeValueAsPositive() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, -4L).positive())
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("0")
        .hasMessageContaining("-4");
    }

    @ParameterizedTest
    @ValueSource(longs = { 0, 42 })
    void shouldValidatePositiveValuesAsPositive(long value) {
      assertThatCode(() -> Assert.field(FIELD_NAME, value).positive()).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullAsOverMin() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Long) null).min(0))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateValueUnderMin() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, 42L).min(1337))
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("42")
        .hasMessageContaining("1337");
    }

    @ParameterizedTest
    @ValueSource(longs = { 41, 42 })
    void shouldValidateValueOverMin(long min) {
      assertThatCode(() -> Assert.field(FIELD_NAME, 42L).min(min)).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullAsUnderMax() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Long) null).max(42L))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateValueOverMax() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, 42L).max(12))
        .isExactlyInstanceOf(NumberValueTooHighException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("12")
        .hasMessageContaining("42");
    }

    @ParameterizedTest
    @ValueSource(longs = { 42, 43 })
    void shouldValidateValueUnderMax(long max) {
      assertThatCode(() -> Assert.field(FIELD_NAME, 42L).max(max)).doesNotThrowAnyException();
    }
  }

  @Nested
  @DisplayName("Float")
  class AssertFloatTest {

    @Test
    void shouldNotValidateNullAsPositive() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Float) null).positive())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateNegativeValueAsPositive() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, -4F).positive())
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("0")
        .hasMessageContaining("-4");
    }

    @ParameterizedTest
    @ValueSource(floats = { 0, 42 })
    void shouldValidatePositiveValuesAsPositive(float value) {
      assertThatCode(() -> Assert.field(FIELD_NAME, value).positive()).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(floats = { -0.1F, 0 })
    void shouldNotValidateNegativeAndZeroValueAsStrictlyPositive(float value) {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, value).strictlyPositive())
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("0")
        .hasMessageContaining(String.valueOf(value));
    }

    @ParameterizedTest
    @ValueSource(floats = { 0.1F, 1 })
    void shouldValidatePositiveValueAsStrictlyPositive(float value) {
      assertThatCode(() -> Assert.field(FIELD_NAME, value).strictlyPositive()).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullAsOverMin() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Float) null).min(0))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateValueUnderMin() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, 42F).min(1337))
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("42")
        .hasMessageContaining("1337");
    }

    @ParameterizedTest
    @ValueSource(floats = { 41, 42 })
    void shouldValidateValueOverMin(float min) {
      assertThatCode(() -> Assert.field(FIELD_NAME, 42F).min(min)).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullAsOverFloor() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Float) null).over(42))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @ParameterizedTest
    @ValueSource(floats = { 41.9F, 42 })
    void shouldNotValidateValueUnderFloor(float value) {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, value).over(42))
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining(String.valueOf(value))
        .hasMessageContaining("42");
    }

    @ParameterizedTest
    @ValueSource(floats = { 42.1F, 43 })
    void shouldValidateValueOverFloor(float value) {
      assertThatCode(() -> Assert.field(FIELD_NAME, value).over(42)).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullAsUnderMax() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Float) null).max(42))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateValueOverMax() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, 42F).max(12))
        .isExactlyInstanceOf(NumberValueTooHighException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("12")
        .hasMessageContaining("42");
    }

    @ParameterizedTest
    @ValueSource(floats = { 42, 43 })
    void shouldValidateValueUnderMax(float max) {
      assertThatCode(() -> Assert.field(FIELD_NAME, 42F).max(max)).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullAsUnderCeil() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Float) null).under(42))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @ParameterizedTest
    @ValueSource(floats = { 42, 42.5F })
    void shouldNotValidateValueOverCeil(float value) {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, value).under(42))
        .isExactlyInstanceOf(NumberValueTooHighException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining(String.valueOf(value))
        .hasMessageContaining("42");
    }

    @ParameterizedTest
    @ValueSource(floats = { 41, 41.9F })
    void shouldValidateValueUnderCeil(float value) {
      assertThatCode(() -> Assert.field(FIELD_NAME, value).under(42)).doesNotThrowAnyException();
    }
  }

  @Nested
  @DisplayName("Double")
  class AssertDoubleTest {

    @Test
    void shouldNotValidateNullAsPositive() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Double) null).positive())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateNegativeValueAsPositive() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, -4D).positive())
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("0")
        .hasMessageContaining("-4");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 0, 42 })
    void shouldValidatePositiveValuesAsPositive(double value) {
      assertThatCode(() -> Assert.field(FIELD_NAME, value).positive()).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(doubles = { -0.1F, 0 })
    void shouldNotValidateNegativeAndZeroValueAsStrictlyPositive(double value) {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, value).strictlyPositive())
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("0")
        .hasMessageContaining(String.valueOf(value));
    }

    @ParameterizedTest
    @ValueSource(doubles = { 0.1F, 1 })
    void shouldValidatePositiveValueAsStrictlyPositive(double value) {
      assertThatCode(() -> Assert.field(FIELD_NAME, value).strictlyPositive()).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullAsOverMin() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Double) null).min(0))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateValueUnderMin() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, 42D).min(1337))
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("42")
        .hasMessageContaining("1337");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 41, 42 })
    void shouldValidateValueOverMin(double min) {
      assertThatCode(() -> Assert.field(FIELD_NAME, 42D).min(min)).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullAsOverFloor() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Double) null).over(42))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @ParameterizedTest
    @ValueSource(doubles = { 41.9D, 42 })
    void shouldNotValidateValueUnderFloor(double value) {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, value).over(42))
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining(String.valueOf(value))
        .hasMessageContaining("42");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 42.1D, 43 })
    void shouldValidateValueOverFloor(double value) {
      assertThatCode(() -> Assert.field(FIELD_NAME, value).over(42)).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullAsUnderMax() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Double) null).max(42))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateValueOverMax() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, 42D).max(12))
        .isExactlyInstanceOf(NumberValueTooHighException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("12")
        .hasMessageContaining("42");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 42, 43 })
    void shouldValidateValueUnderMax(double max) {
      assertThatCode(() -> Assert.field(FIELD_NAME, 42D).max(max)).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullAsUnderCeil() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Double) null).under(42))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @ParameterizedTest
    @ValueSource(doubles = { 42, 42.5F })
    void shouldNotValidateValueOverCeil(double value) {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, value).under(42))
        .isExactlyInstanceOf(NumberValueTooHighException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining(String.valueOf(value))
        .hasMessageContaining("42");
    }

    @ParameterizedTest
    @ValueSource(doubles = { 41, 41.9F })
    void shouldValidateValueUnderCeil(double value) {
      assertThatCode(() -> Assert.field(FIELD_NAME, value).under(42)).doesNotThrowAnyException();
    }
  }

  @Nested
  @DisplayName("BigDecimal")
  class AssertBigDecimalTest {

    @Test
    void shouldNotBePositiveForNullInput() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (BigDecimal) null).positive())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotBePositiveForNegativeValue() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new BigDecimal(-1)).positive())
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("-1")
        .hasMessageContaining("0");
    }

    @Test
    void shouldBePositiveForZero() {
      assertThatCode(() -> Assert.field(FIELD_NAME, BigDecimal.ZERO).positive()).doesNotThrowAnyException();
    }

    @Test
    void shouldBePositiveForOne() {
      assertThatCode(() -> Assert.field(FIELD_NAME, BigDecimal.ONE).positive()).doesNotThrowAnyException();
    }

    @Test
    void shouldNotBeStrictlyPositiveForNullValue() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (BigDecimal) null).strictlyPositive())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotBeStrictlyPositiveForNegativeValue() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new BigDecimal(-1)).strictlyPositive())
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("-1")
        .hasMessageContaining("0");
    }

    @Test
    void shouldNotBeStrictlyPositiveForZero() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, BigDecimal.ZERO).strictlyPositive())
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("0");
    }

    @Test
    void shouldBeStrictlyPositiveForOne() {
      assertThatCode(() -> Assert.field(FIELD_NAME, BigDecimal.ONE).strictlyPositive()).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullBigDecimalAsNotNull() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (BigDecimal) null).notNull())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateNullBigDecimalAsUnderLongMin() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (BigDecimal) null).min(1))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateBigDecimalUnderMinLongValue() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new BigDecimal(1)).min(42))
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("42")
        .hasMessageContaining("1");
    }

    @Test
    void shouldValidateBigDecimalOverMinLongValue() {
      assertThatCode(() -> Assert.field(FIELD_NAME, new BigDecimal(42)).min(1)).doesNotThrowAnyException();
    }

    @Test
    void shouldValidateBigDecimalAtMinLongValue() {
      assertThatCode(() -> Assert.field(FIELD_NAME, new BigDecimal(42)).min(42)).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullBigDecimalAsUnderBigDecimalMin() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (BigDecimal) null).min(BigDecimal.ZERO))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateBigDecimalAsUnderNullMin() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, BigDecimal.ONE).min(null))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining("minValue");
    }

    @Test
    void shouldNotValidateBigDecimalUnderMinBigDecimalValue() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new BigDecimal(1)).min(new BigDecimal(42)))
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("42")
        .hasMessageContaining("1");
    }

    @Test
    void shouldValidateBigDecimalOverMinBigDecimalValue() {
      assertThatCode(() -> Assert.field(FIELD_NAME, new BigDecimal(42)).min(new BigDecimal(1))).doesNotThrowAnyException();
    }

    @Test
    void shouldValidateBigDecimalAtMinBigDecimalValue() {
      assertThatCode(() -> Assert.field(FIELD_NAME, new BigDecimal(42)).min(new BigDecimal(42))).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullValueAsOverLongFloor() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (BigDecimal) null).over(1))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateValueOverLongFloor() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new BigDecimal(1)).over(42))
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("42")
        .hasMessageContaining("1");
    }

    @Test
    void shouldNotValidateValueAtLongFloor() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new BigDecimal(42)).over(42))
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("42");
    }

    @Test
    void shouldValidateValueOverLongFloor() {
      assertThatCode(() -> Assert.field(FIELD_NAME, new BigDecimal(42)).over(1)).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullValueAsOverBigDecimalFloor() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (BigDecimal) null).over(BigDecimal.ONE))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateNullValueAsOverNullFloor() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, BigDecimal.ONE).over(null))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining("floor");
    }

    @Test
    void shouldNotValidateValueUnderBigDecimalFloor() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new BigDecimal(1)).over(new BigDecimal(42)))
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("42")
        .hasMessageContaining("1");
    }

    @Test
    void shouldNotValidateValueAtBigDecimalFloor() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new BigDecimal(42)).over(new BigDecimal(42)))
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("42");
    }

    @Test
    void shouldValidateValueOverBigDecimalFloor() {
      assertThatCode(() -> Assert.field(FIELD_NAME, new BigDecimal(42)).over(BigDecimal.ONE)).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullBigDecimalAsOverLongMax() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (BigDecimal) null).max(1))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateBigDecimalOverMaxLongValue() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new BigDecimal(42)).max(1))
        .isExactlyInstanceOf(NumberValueTooHighException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("42")
        .hasMessageContaining("1");
    }

    @Test
    void shouldValidateBigDecimalUnderMaxLongValue() {
      assertThatCode(() -> Assert.field(FIELD_NAME, new BigDecimal(1)).max(42)).doesNotThrowAnyException();
    }

    @Test
    void shouldValidateBigDecimalAtMaxLongValue() {
      assertThatCode(() -> Assert.field(FIELD_NAME, new BigDecimal(42)).max(42)).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullBigDecimalAsOverBigDecimalMax() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (BigDecimal) null).max(BigDecimal.ZERO))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateBigDecimalAsOverNullMax() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, BigDecimal.ONE).max(null))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining("maxValue");
    }

    @Test
    void shouldNotValidateBigDecimalOverMaxBigDecimalValue() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new BigDecimal(42)).max(new BigDecimal(1)))
        .isExactlyInstanceOf(NumberValueTooHighException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("42")
        .hasMessageContaining("1");
    }

    @Test
    void shouldValidateBigDecimalUnderMaxBigDecimalValue() {
      assertThatCode(() -> Assert.field(FIELD_NAME, new BigDecimal(1)).max(new BigDecimal(1))).doesNotThrowAnyException();
    }

    @Test
    void shouldValidateBigDecimalAtMaxBigDecimalValue() {
      assertThatCode(() -> Assert.field(FIELD_NAME, new BigDecimal(42)).max(new BigDecimal(42))).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullValueAsUnderLongCeil() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (BigDecimal) null).under(1))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateValueUnderLongCeil() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new BigDecimal(42)).under(1))
        .isExactlyInstanceOf(NumberValueTooHighException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("42")
        .hasMessageContaining("1");
    }

    @Test
    void shouldNotValidateValueAtLongCeil() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new BigDecimal(42)).under(42))
        .isExactlyInstanceOf(NumberValueTooHighException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("42");
    }

    @Test
    void shouldValidateValueUnderLongCeil() {
      assertThatCode(() -> Assert.field(FIELD_NAME, new BigDecimal(1)).under(42)).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullValueAsUnderBigDecimalCeil() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (BigDecimal) null).under(BigDecimal.ONE))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateNullValueAsUnderNullCeil() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, BigDecimal.ONE).under(null))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining("ceil");
    }

    @Test
    void shouldNotValidateValueOverBigDecimalCeil() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new BigDecimal(42)).under(new BigDecimal(1)))
        .isExactlyInstanceOf(NumberValueTooHighException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("42")
        .hasMessageContaining("1");
    }

    @Test
    void shouldNotValidateValueAtBigDecimalCeil() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new BigDecimal(42)).under(new BigDecimal(42)))
        .isExactlyInstanceOf(NumberValueTooHighException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("42");
    }

    @Test
    void shouldValidateValueOverBigDecimalCeil() {
      assertThatCode(() -> Assert.field(FIELD_NAME, BigDecimal.ONE).under(new BigDecimal(42))).doesNotThrowAnyException();
    }
  }

  @Nested
  @DisplayName("Collection")
  class CollectionAssertTest {

    @Test
    void shouldNotValidateNullAsNotNull() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Collection<Object>) null).notNull())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("(null)");
    }

    @Test
    void shouldValidateActualCollectionAsNotNull() {
      assertThatCode(() -> Assert.field(FIELD_NAME, List.of()).notNull()).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullAsNotEmpty() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Collection<Object>) null).notEmpty())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("(null)");
    }

    @Test
    void shouldNotValidateEmptyCollectionAsNotEmpty() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, List.of()).notEmpty())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("(empty)");
    }

    @Test
    void shouldValidateCollectionWithElementAsNotEmpty() {
      assertThatCode(() -> Assert.field(FIELD_NAME, List.of("value")).notEmpty()).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullCollectionAsMaxSizeOverZero() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Collection<Object>) null).maxSize(1))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("(null)");
    }

    @Test
    void shouldNotValidateNotNullCollectionAsNegativeMaxSize() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, List.of("value1", "value2", "value3")).maxSize(-1))
        .isExactlyInstanceOf(TooManyElementsException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("-1")
        .hasMessageContaining("3");
    }

    @Test
    void shouldNotValidateCollectionWithTooManyElements() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, List.of("value1", "value2", "value3")).maxSize(2))
        .isExactlyInstanceOf(TooManyElementsException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("2")
        .hasMessageContaining("3");
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 0 })
    void shouldValidateNullAsMaxSizeZeroOrNegative(int maxSize) {
      assertThatCode(() -> Assert.field(FIELD_NAME, (Collection<Object>) null).maxSize(maxSize)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = { 3, 4 })
    void shouldValidateCollectionWithSizeUnderMaxSize(int maxSize) {
      assertThatCode(() -> Assert.field(FIELD_NAME, List.of("value1", "value2", "value3")).maxSize(maxSize)).doesNotThrowAnyException();
    }

    @Test
    void shouldValidateNullAsNegativeMaxSize() {
      assertThatCode(() -> Assert.field(FIELD_NAME, (Collection<Object>) null).maxSize(-1)).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateCollectionWithNullElementAsNoNullElement() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, Arrays.asList("value1", null)).noNullElement())
        .isExactlyInstanceOf(NullElementInCollectionException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("null element");
    }

    @Test
    void shouldValidateNullCollectionAsNoNullElement() {
      assertThatCode(() -> Assert.field(FIELD_NAME, (Collection<Object>) null).noNullElement()).doesNotThrowAnyException();
    }

    @Test
    void shouldValidateCollectionWithNoNullElementAsNoNullElement() {
      assertThatCode(() -> Assert.field(FIELD_NAME, List.of("value1", "value2")).noNullElement()).doesNotThrowAnyException();
    }
  }

  @Nested
  @DisplayName("Array")
  class ArrayAssertTest {

    @Test
    void shouldNotValidateNullAsNotNull() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Object[]) null).notNull())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("(null)");
    }

    @Test
    void shouldValidateActualCollectionAsNotNull() {
      assertThatCode(() -> Assert.field(FIELD_NAME, List.of()).notNull()).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullAsNotEmpty() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Object[]) null).notEmpty())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("(null)");
    }

    @Test
    void shouldNotValidateEmptyCollectionAsNotEmpty() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new String[] {}).notEmpty())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("(empty)");
    }

    @Test
    void shouldValidateCollectionWithElementAsNotEmpty() {
      assertThatCode(() -> Assert.field(FIELD_NAME, new String[] { "value" }).notEmpty()).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullCollectionAsMaxSizeOverZero() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Object[]) null).maxSize(1))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("(null)");
    }

    @Test
    void shouldNotValidateNotNullCollectionAsNegativeMaxSize() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new String[] { "value1", "value2", "value3" }).maxSize(-1))
        .isExactlyInstanceOf(TooManyElementsException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("-1")
        .hasMessageContaining("3");
    }

    @Test
    void shouldNotValidateCollectionWithTooManyElements() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new String[] { "value1", "value2", "value3" }).maxSize(2))
        .isExactlyInstanceOf(TooManyElementsException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("2")
        .hasMessageContaining("3");
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 0 })
    void shouldValidateNullAsMaxSizeZeroOrNegative(int maxSize) {
      assertThatCode(() -> Assert.field(FIELD_NAME, (Object[]) null).maxSize(maxSize)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = { 3, 4 })
    void shouldValidateCollectionWithSizeUnderMaxSize(int maxSize) {
      assertThatCode(() ->
        Assert.field(FIELD_NAME, new String[] { "value1", "value2", "value3" }).maxSize(maxSize)
      ).doesNotThrowAnyException();
    }

    @Test
    void shouldValidateNullAsNegativeMaxSize() {
      assertThatCode(() -> Assert.field(FIELD_NAME, (Object[]) null).maxSize(-1)).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateCollectionWithNullElementAsNoNullElement() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, new String[] { "value1", null }).noNullElement())
        .isExactlyInstanceOf(NullElementInCollectionException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("null element");
    }

    @Test
    void shouldValidateNullCollectionAsNoNullElement() {
      assertThatCode(() -> Assert.field(FIELD_NAME, (Object[]) null).noNullElement()).doesNotThrowAnyException();
    }

    @Test
    void shouldValidateCollectionWithNoNullElementAsNoNullElement() {
      assertThatCode(() -> Assert.field(FIELD_NAME, new String[] { "value1", "value2" }).noNullElement()).doesNotThrowAnyException();
    }
  }

  @Nested
  @DisplayName("Instant")
  class AssertInstantTest {

    @Test
    void shouldNotValidateNullAsNotNull() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Instant) null).notNull())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldValidateActualInstantAsNotNull() {
      assertThatCode(() -> Assert.field(FIELD_NAME, Instant.now()).notNull()).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullInstantAsPast() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Instant) null).inPast())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateFutureInstantAsPast() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, future()).inPast())
        .isExactlyInstanceOf(NotBeforeTimeException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldValidatePastDateAsPast() {
      assertThatCode(() -> Assert.field(FIELD_NAME, past()).inPast()).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateNullInstantAsFuture() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Instant) null).inFuture())
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidatePastInstantAsFuture() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, past()).inFuture())
        .isExactlyInstanceOf(NotAfterTimeException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldValidateFutureDateAsFuture() {
      assertThatCode(() -> Assert.field(FIELD_NAME, future()).inFuture()).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateInstantAfterNullInstant() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, past()).after(null))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining("other");
    }

    @Test
    void shouldNotValidateNullInstantAfterInstant() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Instant) null).after(past()))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateSameInstantAsAfterInstant() {
      Instant date = past();

      assertThatThrownBy(() -> Assert.field(FIELD_NAME, date).after(date))
        .isExactlyInstanceOf(NotAfterTimeException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("strictly");
    }

    @Test
    void shouldNotValidatePastInstantAsAfterFutureInstant() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, past()).after(future()))
        .isExactlyInstanceOf(NotAfterTimeException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("strictly");
    }

    @Test
    void shouldValidateFutureInstantAsAfterPastInstant() {
      assertThatCode(() -> Assert.field(FIELD_NAME, future()).after(past())).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateInstantAfterOrAtNullInstant() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, past()).afterOrAt(null))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining("other");
    }

    @Test
    void shouldNotValidateNullInstantAfterOrAtInstant() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Instant) null).afterOrAt(past()))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidatePastInstantAsAfterOrAtFutureInstant() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, past()).afterOrAt(future()))
        .isExactlyInstanceOf(NotAfterTimeException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageNotContaining("strictly");
    }

    @Test
    void shouldValidateFutureInstantAsAfterOrAtPastInstant() {
      assertThatCode(() -> Assert.field(FIELD_NAME, future()).afterOrAt(past())).doesNotThrowAnyException();
    }

    @Test
    void shouldValidateSameInstantAsAfterOrAtInstant() {
      Instant date = past();

      assertThatCode(() -> Assert.field(FIELD_NAME, date).afterOrAt(date)).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateInstantBeforeNullInstant() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, past()).before(null))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining("other");
    }

    @Test
    void shouldNotValidateNullInstantBeforeInstant() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Instant) null).before(past()))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateSameInstantAsBeforeInstant() {
      Instant date = past();

      assertThatThrownBy(() -> Assert.field(FIELD_NAME, date).before(date))
        .isExactlyInstanceOf(NotBeforeTimeException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("strictly");
    }

    @Test
    void shouldNotValidateFutureInstantAsBeforePastInstant() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, future()).before(past()))
        .isExactlyInstanceOf(NotBeforeTimeException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageContaining("strictly");
    }

    @Test
    void shouldValidatePastInstantAsBeforeFutureInstant() {
      assertThatCode(() -> Assert.field(FIELD_NAME, past()).before(future())).doesNotThrowAnyException();
    }

    @Test
    void shouldNotValidateInstantBeforeOrAtNullInstant() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, past()).beforeOrAt(null))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining("other");
    }

    @Test
    void shouldNotValidateNullInstantBeforeOrAtInstant() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, (Instant) null).beforeOrAt(past()))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining(FIELD_NAME);
    }

    @Test
    void shouldNotValidateFutureInstantAsBeforeOrAtPastInstant() {
      assertThatThrownBy(() -> Assert.field(FIELD_NAME, future()).beforeOrAt(past()))
        .isExactlyInstanceOf(NotBeforeTimeException.class)
        .hasMessageContaining(FIELD_NAME)
        .hasMessageNotContaining("strictly");
    }

    @Test
    void shouldValidatePastInstantAsAfterOrAtFutureInstant() {
      assertThatCode(() -> Assert.field(FIELD_NAME, past()).beforeOrAt(future())).doesNotThrowAnyException();
    }

    @Test
    void shouldValidateSameInstantAsBeforeOrAtInstant() {
      Instant date = past();

      assertThatCode(() -> Assert.field(FIELD_NAME, date).beforeOrAt(date)).doesNotThrowAnyException();
    }

    private Instant past() {
      return Instant.now().minusSeconds(10);
    }

    private Instant future() {
      return Instant.now().plusSeconds(10);
    }
  }
}
