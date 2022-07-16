package tech.jhipster.lite.error.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class Assert {

  private Assert() {}

  /**
   * Ensure that the input is not null
   *
   * @param field
   *          name of the field to check (will be displayed in exception message)
   * @param input
   *          input to check
   * @throws MissingMandatoryValueException
   *           if the input is null
   */
  public static void notNull(String field, Object input) {
    if (input == null) {
      throw MissingMandatoryValueException.forNullValue(field);
    }
  }

  /**
   * Ensure that the value is not blank (null, empty or only whitespace)
   *
   * @param field
   *          name of the field to check (will be displayed in exception message)
   * @param input
   *          input to check
   * @throws MissingMandatoryValueException
   *           if the input is blank
   */
  public static void notBlank(String field, String input) {
    Assert.field(field, input).notBlank();
  }

  /**
   * Ensure that the given collection is not empty
   *
   * @param field
   *          name of the field to check (will be displayed in exception message)
   * @param collection
   *          collection to check
   * @throws MissingMandatoryValueException
   *           if the collection is null or empty
   */
  public static void notEmpty(String field, Collection<?> collection) {
    field(field, collection).notEmpty();
  }

  /**
   * Ensure that the value contains no whitespace
   *
   * @param field
   *          name of the field to check (will be displayed in exception message)
   * @param input
   *          input to check
   * @throws MissingMandatoryValueException
   *           if the input contains whitespace
   */
  public static void noWhitespace(String field, String input) {
    field(field, input).noWhitespace();
  }

  /**
   * Create a fluent asserter for {@link String}
   *
   * <p>
   * Usage:
   *
   * <code>
   * <pre>
   * Assert.field("name", name)
   *   .notBlank()
   *   .maxLength(150);
   * </pre>
   * </code>
   * </p>
   *
   * @param field
   *          name of the field to check (will be displayed in exception message)
   * @param input
   *          string to check
   * @return A {@link StringAsserter} for this field and value
   */
  public static StringAsserter field(String field, String input) {
    return new StringAsserter(field, input);
  }

  /**
   * Create a fluent asserter for Integer values (int and {@link Integer})
   *
   * <p>
   * Usage:
   *
   * <code>
   * <pre>
   * Assert.field("age", age)
   *   .min(0)
   *   .max(150);
   * </pre>
   * </code>
   * </p>
   *
   * @param field
   *          name of the field to check (will be displayed in exception message)
   * @param input
   *          value to check
   * @return An {@link IntegerAsserter} for this field and value
   */
  public static IntegerAsserter field(String field, Integer input) {
    return new IntegerAsserter(field, input);
  }

  /**
   * Create a fluent asserter for Long values (long and {@link Long})
   *
   * <p>
   * Usage:
   *
   * <code>
   * <pre>
   * Assert.field("duration", duration)
   *   .min(100)
   *   .max(500_000);
   * </pre>
   * </code>
   * </p>
   *
   * @param field
   *          name of the field to check (will be displayed in exception message)
   * @param input
   *          value to check
   * @return An {@link LongAsserter} for this field and value
   */
  public static LongAsserter field(String field, Long input) {
    return new LongAsserter(field, input);
  }

  /**
   * Create a fluent asserter for Float values (float and {@link Float})
   *
   * <p>
   * Usage:
   *
   * <code>
   * <pre>
   * Assert.field("rate", rate)
   *   .min(0)
   *   .max(1);
   * </pre>
   * </code>
   * </p>
   *
   * @param field
   *          name of the field to check (will be displayed in exception message)
   * @param input
   *          value to check
   * @return An {@link DoubleAsserter} for this field and value
   */
  public static FloatAsserter field(String field, Float input) {
    return new FloatAsserter(field, input);
  }

  /**
   * Create a fluent asserter for Double values (double and {@link Double})
   *
   * <p>
   * Usage:
   *
   * <code>
   * <pre>
   * Assert.field("rate", rate)
   *   .min(0)
   *   .max(1);
   * </pre>
   * </code>
   * </p>
   *
   * @param field
   *          name of the field to check (will be displayed in exception message)
   * @param input
   *          value to check
   * @return An {@link DoubleAsserter} for this field and value
   */
  public static DoubleAsserter field(String field, Double input) {
    return new DoubleAsserter(field, input);
  }

  /**
   * Create a fluent asserter for {@link BigDecimal} values
   *
   * <p>
   * Usage:
   *
   * <code>
   * <pre>
   * Assert.field("rate", rate)
   *   .min(0)
   *   .max(1);
   * </pre>
   * </code>
   * </p>
   *
   * @param field
   *          name of the field to check (will be displayed in exception message)
   * @param input
   *          value to check
   * @return An {@link BigDecimalAsserter} for this field and value
   */
  public static BigDecimalAsserter field(String field, BigDecimal input) {
    return new BigDecimalAsserter(field, input);
  }

  /**
   * Create a fluent asserter for {@link Collection}
   *
   * <p>
   * Usage:
   *
   * <code>
   * <pre>
   * Assert.field("name", name)
   *  .notEmpty()
   *  .maxSize(150);
   * </pre>
   * </code>
   * </p>
   *
   * @param field
   *          name of the field to check (will be displayed in exception message)
   * @param input
   *          collection to check
   * @return A {@link CollectionAsserter} for this field and value
   */
  public static <T> CollectionAsserter<T> field(String field, Collection<T> input) {
    return new CollectionAsserter<>(field, input);
  }

  /**
   * Create a fluent asserter for an Instant
   *
   * <p>
   * Usage:
   *
   * <code>
   * <pre>
   * Assert.field("date", date)
   *   .inPast()
   *   .after(otherDate);
   * </pre>
   * </code>
   * </p>
   *
   * @param field
   *          name of the field to check (will be displayed in exception message)
   * @param input
   *          value to check
   * @return An {@link InstantAsserter} for this field and value
   */
  public static InstantAsserter field(String field, Instant input) {
    return new InstantAsserter(field, input);
  }

  /**
   * Asserter dedicated to {@link String} assertions
   */
  public static class StringAsserter {

    public static final Pattern PATTERN_SPACE = Pattern.compile("\\s");
    private final String field;
    private final String value;

    private StringAsserter(String field, String value) {
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
     * Ensure  that the value does not contain whitespace
     *
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the value contain whitespace
     */
    public StringAsserter noWhitespace() {
      notNull();

      Matcher matcher = PATTERN_SPACE.matcher(value);
      if (matcher.find()) {
        throw new StringWithWitespacesException(field);
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
  }

  /**
   * Asserter dedicated to Integer values (int and {@link Integer})
   */
  public static class IntegerAsserter {

    private final String field;
    private final Integer value;

    private IntegerAsserter(String field, Integer value) {
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

      if (value.intValue() < minValue) {
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

      if (value.intValue() > maxValue) {
        throw NumberValueTooHighException.builder().field(field).maxValue(String.valueOf(maxValue)).value(String.valueOf(value)).build();
      }

      return this;
    }
  }

  /**
   * Asserter dedicated to long values (long and {@link Long})
   */
  public static class LongAsserter {

    private final String field;
    private final Long value;

    private LongAsserter(String field, Long value) {
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

      if (value.longValue() < minValue) {
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

      if (value.longValue() > maxValue) {
        throw NumberValueTooHighException.builder().field(field).maxValue(String.valueOf(maxValue)).value(String.valueOf(value)).build();
      }

      return this;
    }
  }

  /**
   * Asserter dedicated to float values (float and {@link Float})
   */
  public static class FloatAsserter {

    private final String field;
    private final Float value;

    private FloatAsserter(String field, Float value) {
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

      if (value.floatValue() < minValue) {
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

      if (value.floatValue() <= floor) {
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

      if (value.floatValue() > maxValue) {
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

      if (value.floatValue() >= ceil) {
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
  public static class DoubleAsserter {

    private final String field;
    private final Double value;

    private DoubleAsserter(String field, Double value) {
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

      if (value.doubleValue() < minValue) {
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

      if (value.doubleValue() <= floor) {
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

      if (value.doubleValue() > maxValue) {
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

      if (value.doubleValue() >= ceil) {
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
  public static class BigDecimalAsserter {

    private final String field;
    private final BigDecimal value;

    private BigDecimalAsserter(String field, BigDecimal value) {
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
      return NumberValueTooHighException.builder().field(field).maxValue(String.valueOf(ceil)).value(value.toPlainString()).build();
    }

    /**
     * Ensure that the input value is not null
     *
     * @return The current asserter
     * @throws MissingMandatoryValueException
     *           if the input value is null
     */
    public BigDecimalAsserter notNull() {
      Assert.notNull(field, value);

      return this;
    }
  }

  /**
   * Asserter dedicated to {@link Collection} assertions
   */
  public static class CollectionAsserter<T> {

    private final String field;
    private final Collection<T> value;

    private CollectionAsserter(String field, Collection<T> value) {
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
   * Asserter dedicated to instant value
   */
  public static class InstantAsserter {

    private static final String OTHER_FIELD_NAME = "other";

    private final String field;
    private final Instant value;

    private InstantAsserter(String field, Instant value) {
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
     *           if the input instant is in past
     */
    public InstantAsserter inFuture() {
      return afterOrAt(Instant.now());
    }

    /**
     * Ensure that the input instant is after the given instant
     *
     * @param other
     *          exclusive after instant
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
        throw NotAfterTimeException.notStrictlyAfter(field, other);
      }

      return this;
    }

    /**
     * Ensure that the input instant is after the given instant
     *
     * @param other
     *          inclusive after instant
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
        throw NotAfterTimeException.notAfter(field, other);
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
     *          exclusive before instant
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
        throw NotBeforeTimeException.notStrictlyBefore(field, other);
      }

      return this;
    }

    /**
     * Ensure that the input instant is before the given instant
     *
     * @param other
     *          inclusive before instant
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
        throw NotBeforeTimeException.notBefore(field, other);
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
    public InstantAsserter notNull() {
      Assert.notNull(field, value);

      return this;
    }
  }
}
