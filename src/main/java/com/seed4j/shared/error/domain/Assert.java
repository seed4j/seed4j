package com.seed4j.shared.error.domain;

import com.seed4j.shared.nullness.domain.Contract;
import com.seed4j.shared.nullness.domain.EnsuresNonNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.jspecify.annotations.Nullable;

/**
 * This class provides utilities for input assertions.
 *
 * <p>
 * It is designed to validate domain input, if you want to validate application input it's better to stick to
 * BeanValidation to get all errors at once and internationalized error messages.
 * </p>
 *
 * <p>
 * The main goal of this class is to ensure some basic type validation in your classes. If you have to do business
 * related validation you should create your own exception and code dedicated to that check
 * </p>
 */
public final class Assert {

  private Assert() {}

  /**
   * Ensure that the input is not null
   *
   * @param field
   *          name of the field to check (will be displayed in an exception message)
   * @param input
   *          input to check
   * @throws MissingMandatoryValueException
   *           if the input is null
   */
  @Contract("_, null -> fail")
  public static void notNull(String field, @Nullable Object input) {
    if (input == null) {
      throw MissingMandatoryValueException.forNullValue(field);
    }
  }

  /**
   * Ensure that the value is not blank (null, empty or only whitespace)
   *
   * @param field
   *          name of the field to check (will be displayed in an exception message)
   * @param input
   *          input to check
   * @throws MissingMandatoryValueException
   *           if the input is blank
   */
  public static void notBlank(String field, @Nullable String input) {
    field(field, input).notBlank();
  }

  /**
   * Ensure that the given collection is not empty
   *
   * @param field
   *          name of the field to check (will be displayed in an exception message)
   * @param collection
   *          collection to check
   * @throws MissingMandatoryValueException
   *           if the collection is null or empty
   */
  public static void notEmpty(String field, @Nullable Collection<?> collection) {
    field(field, collection).notEmpty();
  }

  /**
   * Ensure that the given array is not empty
   *
   * @param field
   *          name of the field to check (will be displayed in an exception message)
   * @param array
   *          array to check
   * @throws MissingMandatoryValueException
   *           if the array is null or empty
   */
  public static <T> void notEmpty(String field, @Nullable T[] array) {
    field(field, array).notEmpty();
  }

  /**
   * Ensure that the value contains no whitespace
   *
   * @param field
   *          name of the field to check (will be displayed in an exception message)
   * @param input
   *          input to check
   * @throws MissingMandatoryValueException
   *           if the input contains whitespace
   */
  public static void noWhitespace(String field, @Nullable String input) {
    field(field, input).noWhitespace();
  }

  /**
   * Create a fluent asserter for {@link String}
   *
   * <p>
   * Usage:
   * </p>
   *
   * <pre>
   * <code>
   * Assert.field("name", name)
   *   .notBlank()
   *   .maxLength(150);
   * </code>
   * </pre>
   *
   * @param field
   *          name of the field to check (will be displayed in an exception message)
   * @param input
   *          string to check
   * @return A {@link StringAsserter} for this field and value
   */
  public static StringAsserter field(String field, @Nullable String input) {
    return new StringAsserter(field, input);
  }

  /**
   * Create a fluent asserter for Integer values (int and {@link Integer})
   *
   * <p>
   * Usage:
   * </p>
   *
   * <pre>
   * <code>
   * Assert.field("age", age)
   *   .min(0)
   *   .max(150);
   * </code>
   * </pre>
   *
   * @param field
   *          name of the field to check (will be displayed in an exception message)
   * @param input
   *          value to check
   * @return An {@link IntegerAsserter} for this field and value
   */
  public static IntegerAsserter field(String field, @Nullable Integer input) {
    return new IntegerAsserter(field, input);
  }

  /**
   * Create a fluent asserter for Long values (long and {@link Long})
   *
   * <p>
   * Usage:
   * </p>
   *
   * <pre>
   * <code>
   * Assert.field("duration", duration)
   *   .min(100)
   *   .max(500_000);
   * </code>
   * </pre>
   *
   * @param field
   *          name of the field to check (will be displayed in an exception message)
   * @param input
   *          value to check
   * @return An {@link LongAsserter} for this field and value
   */
  public static LongAsserter field(String field, @Nullable Long input) {
    return new LongAsserter(field, input);
  }

  /**
   * Create a fluent asserter for Float values (float and {@link Float})
   *
   * <p>
   * Usage:
   * </p>
   *
   * <pre>
   * <code>
   * Assert.field("rate", rate)
   *   .min(0)
   *   .max(1);
   * </code>
   * </pre>
   *
   * @param field
   *          name of the field to check (will be displayed in an exception message)
   * @param input
   *          value to check
   * @return An {@link DoubleAsserter} for this field and value
   */
  public static FloatAsserter field(String field, @Nullable Float input) {
    return new FloatAsserter(field, input);
  }

  /**
   * Create a fluent asserter for Double values (double and {@link Double})
   *
   * <p>
   * Usage:
   * </p>
   *
   * <pre>
   * <code>
   * Assert.field("rate", rate)
   *   .min(0)
   *   .max(1);
   * </code>
   * </pre>
   *
   * @param field
   *          name of the field to check (will be displayed in an exception message)
   * @param input
   *          value to check
   * @return An {@link DoubleAsserter} for this field and value
   */
  public static DoubleAsserter field(String field, @Nullable Double input) {
    return new DoubleAsserter(field, input);
  }

  /**
   * Create a fluent asserter for {@link BigDecimal} values
   *
   * <p>
   * Usage:
   * </p>
   *
   * <pre>
   * <code>
   * Assert.field("rate", rate)
   *   .min(0)
   *   .max(1);
   * </code>
   * </pre>
   *
   * @param field
   *          name of the field to check (will be displayed in an exception message)
   * @param input
   *          value to check
   * @return An {@link BigDecimalAsserter} for this field and value
   */
  public static BigDecimalAsserter field(String field, @Nullable BigDecimal input) {
    return new BigDecimalAsserter(field, input);
  }

  /**
   * Create a fluent asserter for {@link Collection}
   *
   * <p>
   * Usage:
   * </p>
   *
   * <pre>
   * <code>
   * Assert.field("name", name)
   *  .notEmpty()
   *  .maxSize(150);
   * </code>
   * </pre>
   *
   * @param field
   *          name of the field to check (will be displayed in an exception message)
   * @param input
   *          collection to check
   * @return A {@link CollectionAsserter} for this field and value
   */
  public static <T> CollectionAsserter<T> field(String field, @Nullable Collection<T> input) {
    return new CollectionAsserter<>(field, input);
  }

  /**
   * Create a fluent asserter for an array
   *
   * <p>
   * Usage:
   * </p>
   *
   * <pre>
   * <code>
   * Assert.field("name", name)
   *  .notEmpty()
   *  .maxSize(150);
   * </code>
   * </pre>
   *
   * @param field
   *          name of the field to check (will be displayed in an exception message)
   * @param input
   *          array to check
   * @return A {@link ArrayAsserter} for this field and value
   */
  public static <T> ArrayAsserter<T> field(String field, @Nullable T[] input) {
    return new ArrayAsserter<>(field, input);
  }

  /**
   * Create a fluent asserter for an Instant
   *
   * <p>
   * Usage:
   * </p>
   *
   * <pre>
   * <code>
   * Assert.field("date", date)
   *   .inPast()
   *   .after(otherDate);
   * </code>
   * </pre>
   *
   * @param field
   *          name of the field to check (will be displayed in an exception message)
   * @param input
   *          value to check
   * @return An {@link InstantAsserter} for this field and value
   */
  public static InstantAsserter field(String field, @Nullable Instant input) {
    return new InstantAsserter(field, input);
  }

  /**
   * Asserter dedicated to {@link String} assertions
   */
  public static final class StringAsserter {

    public static final Pattern PATTERN_SPACE = Pattern.compile("\\s");
    private final String field;
    private final @Nullable String value;

    private StringAsserter(String field, @Nullable String value) {
      this.field = field;
      this.value = value;
    }

    /**
     * Ensure that the value is not null
     *
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the value is null
     */
    @EnsuresNonNull("value")
    public StringAsserter notNull() {
      Assert.notNull(field, value);

      return this;
    }

    /**
     * Ensure that the value is not blank (null, empty or only whitespace)
     *
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the value is blank
     */
    public StringAsserter notBlank() {
      notNull();

      if (value.isBlank()) {
        throw MissingMandatoryValueException.forBlankValue(field);
      }

      return this;
    }

    /**
     * Ensure that the value does not contain whitespace
     *
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the value contains whitespace
     */
    public StringAsserter noWhitespace() {
      notNull();

      Matcher matcher = PATTERN_SPACE.matcher(value);
      if (matcher.find()) {
        throw new StringWithWhitespacesException(field);
      }

      return this;
    }

    /**
     * Ensure that the input value is at least of the given length
     *
     * @param length
     *          inclusive min length of the {@link String}
     *
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the expected length is strictly positive and the value is null
     * @throws StringTooShortException
     *           if the value is shorter than min length
     */
    public StringAsserter minLength(int length) {
      if (length <= 0 && value == null) {
        return this;
      }

      notNull();

      if (value.length() < length) {
        throw StringTooShortException.builder().field(field).value(value).minLength(length).build();
      }

      return this;
    }

    /**
     * Ensure that the given input value is not over the given length
     *
     * @param length
     *          inclusive max length of the {@link String}
     * @return The current asserter
     * @throws StringTooLongException
     *           if the value is longer than the max length
     */
    public StringAsserter maxLength(int length) {
      if (value == null) {
        return this;
      }

      if (value.length() > length) {
        throw StringTooLongException.builder().field(field).value(value).maxLength(length).build();
      }

      return this;
    }

    /**
     * Ensure that the given input value matches the given pattern
     *
     * @param pattern pattern of the {@link String}
     * @return The current asserter
     * @throws NotMatchingExpectedPatternException
     *           if the value does not match the provided pattern
     */
    public StringAsserter matchesPattern(Pattern pattern) {
      return matchesPatternOrThrow(pattern, () ->
        NotMatchingExpectedPatternException.builder().field(field).value(value).pattern(pattern).build()
      );
    }

    /**
     * Ensure that the given input value matches the given pattern
     *
     * @param pattern pattern of the {@link String}
     * @param exceptionSupplier supplier of the exception to throw if the value does not match the pattern
     * @return The current asserter
     * @throws X provided exception if the value does not match the provided pattern
     */
    public <X extends RuntimeException> StringAsserter matchesPatternOrThrow(Pattern pattern, Supplier<X> exceptionSupplier) {
      if (value == null) {
        return this;
      }

      if (!pattern.matcher(value).matches()) {
        throw exceptionSupplier.get();
      }

      return this;
    }
  }

  /**
   * Asserter dedicated to Integer values (int and {@link Integer})
   */
  public static final class IntegerAsserter {

    private final String field;
    private final @Nullable Integer value;

    private IntegerAsserter(String field, @Nullable Integer value) {
      this.field = field;
      this.value = value;
    }

    /**
     * Ensure that the input value is positive (0 is positive)
     *
     * @return The current asserters
     * @throws MissingMandatoryValueException
     *           if the value is null
     * @throws NumberValueTooLowException
     *           if the value is negative
     */
    public IntegerAsserter positive() {
      return min(0);
    }

    /**
     * Ensure that the input value is over the given value
     *
     * @param minValue
     *          inclusive min value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if value is null
     * @throws NumberValueTooLowException
     *           if the value is under min
     */
    public IntegerAsserter min(int minValue) {
      notNull(field, value);

      if (value < minValue) {
        throw NumberValueTooLowException.builder().field(field).minValue(String.valueOf(minValue)).value(String.valueOf(value)).build();
      }

      return this;
    }

    /**
     * Ensure that the input value is under the given value
     *
     * @param maxValue
     *          inclusive max value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if value is null
     * @throws NumberValueTooHighException
     *           if the value is over max
     */
    public IntegerAsserter max(int maxValue) {
      notNull(field, value);

      if (value > maxValue) {
        throw NumberValueTooHighException.builder().field(field).maxValue(String.valueOf(maxValue)).value(String.valueOf(value)).build();
      }

      return this;
    }
  }

  /**
   * Asserter dedicated to long values (long and {@link Long})
   */
  public static final class LongAsserter {

    private final String field;
    private final @Nullable Long value;

    private LongAsserter(String field, @Nullable Long value) {
      this.field = field;
      this.value = value;
    }

    /**
     * Ensure that the input value is positive (0 is positive)
     *
     * @return The current asserters
     * @throws MissingMandatoryValueException
     *           if the value is null
     * @throws NumberValueTooLowException
     *           if the value is negative
     */
    public LongAsserter positive() {
      return min(0);
    }

    /**
     * Ensure that the input value is over the given value
     *
     * @param minValue
     *          inclusive min value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if value is null
     * @throws NumberValueTooLowException
     *           if the value is under min
     */
    public LongAsserter min(long minValue) {
      notNull(field, value);

      if (value < minValue) {
        throw NumberValueTooLowException.builder().field(field).minValue(String.valueOf(minValue)).value(String.valueOf(value)).build();
      }

      return this;
    }

    /**
     * Ensure that the input value is under the given value
     *
     * @param maxValue
     *          inclusive max value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if value is null
     * @throws NumberValueTooHighException
     *           if the value is over max
     */
    public LongAsserter max(long maxValue) {
      notNull(field, value);

      if (value > maxValue) {
        throw NumberValueTooHighException.builder().field(field).maxValue(String.valueOf(maxValue)).value(String.valueOf(value)).build();
      }

      return this;
    }
  }

  /**
   * Asserter dedicated to float values (float and {@link Float})
   */
  public static final class FloatAsserter {

    private final String field;
    private final @Nullable Float value;

    private FloatAsserter(String field, @Nullable Float value) {
      this.field = field;
      this.value = value;
    }

    /**
     * Ensure that the input value is positive (0 is positive)
     *
     * @return The current asserters
     * @throws MissingMandatoryValueException
     *           if the value is null
     * @throws NumberValueTooLowException
     *           if the value is negative
     */
    public FloatAsserter positive() {
      return min(0);
    }

    /**
     * Ensure that the input value is strictly positive (0 is not strictly positive)
     *
     * @return The current asserters
     * @throws MissingMandatoryValueException
     *           if the value is null
     * @throws NumberValueTooLowException
     *           if the value is negative
     */
    public FloatAsserter strictlyPositive() {
      return over(0);
    }

    /**
     * Ensure that the input value is over the given value
     *
     * @param minValue
     *          inclusive min value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if value is null
     * @throws NumberValueTooLowException
     *           if the value is under min
     */
    public FloatAsserter min(float minValue) {
      notNull(field, value);

      if (value < minValue) {
        throw tooLow(minValue);
      }

      return this;
    }

    /**
     * Ensure that the input value is over the given floor
     *
     * @param floor
     *          exclusive floor value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if value is null
     * @throws NumberValueTooHighException
     *           if the value is under floor
     */
    public FloatAsserter over(float floor) {
      notNull(field, value);

      if (value <= floor) {
        throw tooLow(floor);
      }

      return this;
    }

    private NumberValueTooLowException tooLow(float floor) {
      return NumberValueTooLowException.builder().field(field).minValue(String.valueOf(floor)).value(String.valueOf(value)).build();
    }

    /**
     * Ensure that the input value is under the given value
     *
     * @param maxValue
     *          inclusive max value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if value is null
     * @throws NumberValueTooHighException
     *           if the value is over max
     */
    public FloatAsserter max(float maxValue) {
      notNull(field, value);

      if (value > maxValue) {
        throw tooHigh(maxValue);
      }

      return this;
    }

    /**
     * Ensure that the input value is under the given ceil
     *
     * @param ceil
     *          exclusive ceil value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if value is null
     * @throws NumberValueTooHighException
     *           if the value is over ceil
     */
    public FloatAsserter under(float ceil) {
      notNull(field, value);

      if (value >= ceil) {
        throw tooHigh(ceil);
      }

      return this;
    }

    private NumberValueTooHighException tooHigh(float ceil) {
      return NumberValueTooHighException.builder().field(field).maxValue(String.valueOf(ceil)).value(String.valueOf(value)).build();
    }
  }

  /**
   * Asserter dedicated to double values (double and {@link Double})
   */
  public static final class DoubleAsserter {

    private final String field;
    private final @Nullable Double value;

    private DoubleAsserter(String field, @Nullable Double value) {
      this.field = field;
      this.value = value;
    }

    /**
     * Ensure that the input value is positive (0 is positive)
     *
     * @return The current asserters
     * @throws MissingMandatoryValueException
     *           if the value is null
     * @throws NumberValueTooLowException
     *           if the value is negative
     */
    public DoubleAsserter positive() {
      return min(0);
    }

    /**
     * Ensure that the input value is strictly positive (0 is not strictly positive)
     *
     * @return The current asserters
     * @throws MissingMandatoryValueException
     *           if the value is null
     * @throws NumberValueTooLowException
     *           if the value is negative
     */
    public DoubleAsserter strictlyPositive() {
      return over(0);
    }

    /**
     * Ensure that the input value is over the given value
     *
     * @param minValue
     *          inclusive min value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if value is null
     * @throws NumberValueTooLowException
     *           if the value is under min
     */
    public DoubleAsserter min(double minValue) {
      notNull(field, value);

      if (value < minValue) {
        throw tooLow(minValue);
      }

      return this;
    }

    /**
     * Ensure that the input value is over the given floor
     *
     * @param floor
     *          exclusive floor value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if value is null
     * @throws NumberValueTooHighException
     *           if the value is under floor
     */
    public DoubleAsserter over(double floor) {
      notNull(field, value);

      if (value <= floor) {
        throw tooLow(floor);
      }

      return this;
    }

    private NumberValueTooLowException tooLow(double floor) {
      return NumberValueTooLowException.builder().field(field).minValue(String.valueOf(floor)).value(String.valueOf(value)).build();
    }

    /**
     * Ensure that the input value is under the given value
     *
     * @param maxValue
     *          inclusive max value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if value is null
     * @throws NumberValueTooHighException
     *           if the value is over max
     */
    public DoubleAsserter max(double maxValue) {
      notNull(field, value);

      if (value > maxValue) {
        throw tooHigh(maxValue);
      }

      return this;
    }

    /**
     * Ensure that the input value is under the given ceil
     *
     * @param ceil
     *          exclusive ceil value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if value is null
     * @throws NumberValueTooHighException
     *           if the value is over ceil
     */
    public DoubleAsserter under(double ceil) {
      notNull(field, value);

      if (value >= ceil) {
        throw tooHigh(ceil);
      }

      return this;
    }

    private NumberValueTooHighException tooHigh(double ceil) {
      return NumberValueTooHighException.builder().field(field).maxValue(String.valueOf(ceil)).value(String.valueOf(value)).build();
    }
  }

  /**
   * Asserter dedicated to {@link BigDecimal} assertions
   */
  public static final class BigDecimalAsserter {

    private final String field;
    private final @Nullable BigDecimal value;

    private BigDecimalAsserter(String field, @Nullable BigDecimal value) {
      this.field = field;
      this.value = value;
    }

    /**
     * Ensure that the input value is positive (0 is positive)
     *
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the input value is null
     * @throws NumberValueTooLowException
     *           if the input value is negative
     */
    public BigDecimalAsserter positive() {
      return min(0);
    }

    /**
     * Ensure that the input value is strictly positive (0 is not strictly positive)
     *
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the input value is null
     * @throws NumberValueTooLowException
     *           if the input value is negative
     */
    public BigDecimalAsserter strictlyPositive() {
      return over(0);
    }

    /**
     * Ensure that the input value is at least at min value
     *
     * @param minValue
     *          inclusive min value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the input value is null
     * @throws NumberValueTooLowException
     *           if the input value is under the min value
     */
    public BigDecimalAsserter min(long minValue) {
      return min(new BigDecimal(minValue));
    }

    /**
     * Ensure that the input value is at least at min value
     *
     * @param minValue
     *          inclusive min value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the input or min value is null
     * @throws NumberValueTooLowException
     *           if the input value is under the min value
     */
    public BigDecimalAsserter min(BigDecimal minValue) {
      notNull();
      Assert.notNull("minValue", minValue);

      if (value.compareTo(minValue) < 0) {
        throw tooLow(minValue);
      }

      return this;
    }

    /**
     * Ensure that the input value is over the given floor
     *
     * @param floor
     *          exclusive floor value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if value is null
     * @throws NumberValueTooLowException
     *           if the value is under floor
     */
    public BigDecimalAsserter over(long floor) {
      return over(new BigDecimal(floor));
    }

    /**
     * Ensure that the input value is over the given floor
     *
     * @param floor
     *          exclusive floor value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if value or floor is null
     * @throws NumberValueTooLowException
     *           if the value is under floor
     */
    public BigDecimalAsserter over(BigDecimal floor) {
      notNull();
      Assert.notNull("floor", floor);

      if (value.compareTo(floor) <= 0) {
        throw tooLow(floor);
      }

      return this;
    }

    private NumberValueTooLowException tooLow(BigDecimal floor) {
      notNull();
      return NumberValueTooLowException.builder().field(field).minValue(String.valueOf(floor)).value(value.toPlainString()).build();
    }

    /**
     * Ensure that the input value is at most at max value
     *
     * @param maxValue
     *          inclusive max value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the input value is null
     * @throws NumberValueTooHighException
     *           if the input value is over max
     */
    public BigDecimalAsserter max(long maxValue) {
      return max(new BigDecimal(maxValue));
    }

    /**
     * Ensure that the input value is at most at max value
     *
     * @param maxValue
     *          inclusive max value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the input or max value is null
     * @throws NumberValueTooHighException
     *           if the input value is over max
     */
    public BigDecimalAsserter max(BigDecimal maxValue) {
      notNull();
      Assert.notNull("maxValue", maxValue);

      if (value.compareTo(maxValue) > 0) {
        throw tooHigh(maxValue);
      }

      return this;
    }

    /**
     * Ensure that the input value is under the given ceil
     *
     * @param ceil
     *          exclusive ceil value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if value is null
     * @throws NumberValueTooHighException
     *           if the value is under floor
     */
    public BigDecimalAsserter under(long ceil) {
      return under(new BigDecimal(ceil));
    }

    /**
     * Ensure that the input value is under the given ceil
     *
     * @param ceil
     *          exclusive ceil value
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if value or ceil is null
     * @throws NumberValueTooHighException
     *           if the value is under floor
     */
    public BigDecimalAsserter under(BigDecimal ceil) {
      notNull();
      Assert.notNull("ceil", ceil);

      if (value.compareTo(ceil) >= 0) {
        throw tooHigh(ceil);
      }

      return this;
    }

    private NumberValueTooHighException tooHigh(BigDecimal ceil) {
      notNull();
      return NumberValueTooHighException.builder().field(field).maxValue(String.valueOf(ceil)).value(value.toPlainString()).build();
    }

    /**
     * Ensure that the input value is not null
     *
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the input value is null
     */
    @EnsuresNonNull("value")
    public BigDecimalAsserter notNull() {
      Assert.notNull(field, value);

      return this;
    }
  }

  /**
   * Asserter dedicated to {@link Collection} assertions
   */
  public static final class CollectionAsserter<T> {

    private final String field;
    private final @Nullable Collection<T> value;

    private CollectionAsserter(String field, @Nullable Collection<T> value) {
      this.field = field;
      this.value = value;
    }

    /**
     * Ensure that the value is not null
     *
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the value is null
     */
    @EnsuresNonNull("value")
    public CollectionAsserter<T> notNull() {
      Assert.notNull(field, value);

      return this;
    }

    /**
     * Ensure that the value is not empty (null or empty)
     *
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the value is null or empty
     */
    public CollectionAsserter<T> notEmpty() {
      notNull();

      if (value.isEmpty()) {
        throw MissingMandatoryValueException.forEmptyValue(field);
      }

      return this;
    }

    /**
     * Ensure that the size of the given input value is not over the given size
     *
     * @param maxSize
     *          inclusive max size of the {@link Collection}
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the expected size is strictly positive and the value is null
     * @throws TooManyElementsException
     *           if the size of value is over the max size
     */
    public CollectionAsserter<T> maxSize(int maxSize) {
      if (maxSize <= 0 && value == null) {
        return this;
      }

      notNull();

      if (value.size() > maxSize) {
        throw TooManyElementsException.builder().field(field).maxSize(maxSize).size(value.size()).build();
      }

      return this;
    }

    /**
     * Ensure that no element in this {@link Collection} is null
     *
     * @return The current asserter
     * @throws NullElementInCollectionException
     *           if an element is null
     */
    public CollectionAsserter<T> noNullElement() {
      if (value == null) {
        return this;
      }

      if (value.stream().anyMatch(Objects::isNull)) {
        throw new NullElementInCollectionException(field);
      }

      return this;
    }
  }

  /**
   * Asserter dedicated to arrays assertions
   */
  public static final class ArrayAsserter<T> {

    private final String field;
    private final @Nullable T[] value;

    private ArrayAsserter(String field, @Nullable T[] value) {
      this.field = field;
      this.value = value;
    }

    /**
     * Ensure that the value is not null
     *
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the value is null
     */
    @EnsuresNonNull("value")
    public ArrayAsserter<T> notNull() {
      Assert.notNull(field, value);

      return this;
    }

    /**
     * Ensure that the value is not empty (null or empty)
     *
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the value is null or empty
     */
    public ArrayAsserter<T> notEmpty() {
      notNull();

      if (value.length == 0) {
        throw MissingMandatoryValueException.forEmptyValue(field);
      }

      return this;
    }

    /**
     * Ensure that the size of the given input value is not over the given size
     *
     * @param maxSize
     *          inclusive max size of the array
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the expected size is strictly positive and the value is null
     * @throws TooManyElementsException
     *           if the size of value is over the max size
     */
    public ArrayAsserter<T> maxSize(int maxSize) {
      if (maxSize <= 0 && value == null) {
        return this;
      }

      notNull();

      if (value.length > maxSize) {
        throw TooManyElementsException.builder().field(field).maxSize(maxSize).size(value.length).build();
      }

      return this;
    }

    /**
     * Ensure that no element in this array is null
     *
     * @return The current asserter
     * @throws NullElementInCollectionException
     *           if an element is null
     */
    public ArrayAsserter<T> noNullElement() {
      if (value == null) {
        return this;
      }

      if (Stream.of(value).anyMatch(Objects::isNull)) {
        throw new NullElementInCollectionException(field);
      }

      return this;
    }
  }

  /**
   * Asserter dedicated to instant value
   */
  public static final class InstantAsserter {

    private static final String OTHER_FIELD_NAME = "other";

    private final String field;
    private final @Nullable Instant value;

    private InstantAsserter(String field, @Nullable Instant value) {
      this.field = field;
      this.value = value;
    }

    /**
     * Ensure that the given instant is in the future or at current Instant (considering this method invocation time)
     *
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the input value is null
     * @throws NotAfterTimeException
     *           if the input instant is in the past
     */
    public InstantAsserter inFuture() {
      return afterOrAt(Instant.now());
    }

    /**
     * Ensure that the input instant is after the given instant
     *
     * @param other
     *          exclusive after an instant
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if input or other are null
     * @throws NotAfterTimeException
     *           if the input instant is not after the other instant
     */
    public InstantAsserter after(Instant other) {
      notNull();
      Assert.notNull(OTHER_FIELD_NAME, other);

      if (value.compareTo(other) <= 0) {
        throw NotAfterTimeException.field(field, value).strictlyNotAfter(other);
      }

      return this;
    }

    /**
     * Ensure that the input instant is after the given instant
     *
     * @param other
     *          inclusive after an instant
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if input or other are null
     * @throws NotAfterTimeException
     *           if the input instant is not after the other instant
     */
    public InstantAsserter afterOrAt(Instant other) {
      notNull();
      Assert.notNull(OTHER_FIELD_NAME, other);

      if (value.compareTo(other) < 0) {
        throw NotAfterTimeException.field(field, value).notAfter(other);
      }

      return this;
    }

    /**
     * Ensure that the given instant is in the past or at current Instant (considering this method invocation time)
     *
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the input value is null
     * @throws NotBeforeTimeException
     *           if the input instant is in future
     */
    public InstantAsserter inPast() {
      return beforeOrAt(Instant.now());
    }

    /**
     * Ensure that the input instant is before the given instant
     *
     * @param other
     *          exclusive before an instant
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if input or other are null
     * @throws NotBeforeTimeException
     *           if the input instant is not before the other instant
     */
    public InstantAsserter before(Instant other) {
      notNull();
      Assert.notNull(OTHER_FIELD_NAME, other);

      if (value.compareTo(other) >= 0) {
        throw NotBeforeTimeException.field(field, value).strictlyNotBefore(other);
      }

      return this;
    }

    /**
     * Ensure that the input instant is before the given instant
     *
     * @param other
     *          inclusive before an instant
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if input or other are null
     * @throws NotBeforeTimeException
     *           if the input instant is not before the other instant
     */
    public InstantAsserter beforeOrAt(Instant other) {
      notNull();
      Assert.notNull(OTHER_FIELD_NAME, other);

      if (value.compareTo(other) > 0) {
        throw NotBeforeTimeException.field(field, value).notBefore(other);
      }

      return this;
    }

    /**
     * Ensure that the instant is not null
     *
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the instant is null
     */
    @EnsuresNonNull("value")
    public InstantAsserter notNull() {
      Assert.notNull(field, value);

      return this;
    }
  }
}
