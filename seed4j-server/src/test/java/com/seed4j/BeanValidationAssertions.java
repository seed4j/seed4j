package com.seed4j;

import static org.assertj.core.api.Assertions.*;

import com.seed4j.shared.error.domain.Assert;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.function.Predicate;

public final class BeanValidationAssertions {

  private BeanValidationAssertions() {}

  public static <T> BeanAsserter<T> assertThatBean(T bean) {
    return new BeanAsserter<>(bean);
  }

  public static final class BeanAsserter<T> {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final Set<ConstraintViolation<T>> violations;

    private BeanAsserter(T bean) {
      Assert.notNull("bean", bean);
      violations = validator.validate(bean);
    }

    public BeanAsserter<T> isValid() {
      assertThat(violations).isEmpty();
      return this;
    }

    public InvalidPropertyAsserter<T> hasInvalidProperty(String property) {
      Assert.notBlank("property", property);
      return violations
        .stream()
        .filter(withProperty(property))
        .findFirst()
        .map(validation -> new InvalidPropertyAsserter<>(this, validation))
        .orElseThrow(() -> new AssertionError("Property " + property + " must be invalid and wasn't"));
    }

    private Predicate<ConstraintViolation<T>> withProperty(String property) {
      return validation -> property.equals(validation.getPropertyPath().toString());
    }
  }

  public static final class InvalidPropertyAsserter<T> {

    private final BeanAsserter<T> beanAsserter;
    private final ConstraintViolation<T> violation;

    private InvalidPropertyAsserter(BeanAsserter<T> beanAsserter, ConstraintViolation<T> violation) {
      this.beanAsserter = beanAsserter;
      this.violation = violation;
    }

    public InvalidPropertyAsserter<T> withMessage(String message) {
      Assert.notBlank("message", message);
      assertThat(violation.getMessage()).isEqualTo(message);
      return this;
    }

    public InvalidPropertyAsserter<T> withParameter(String parameter, Object value) {
      Assert.notBlank("parameter", parameter);
      assertThat(violation.getConstraintDescriptor().getAttributes()).contains(entry(parameter, value));
      return this;
    }

    public BeanAsserter<T> and() {
      return beanAsserter;
    }
  }
}
