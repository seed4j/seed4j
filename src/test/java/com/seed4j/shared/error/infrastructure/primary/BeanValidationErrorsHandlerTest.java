package com.seed4j.shared.error.infrastructure.primary;

import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Level;
import com.seed4j.Logs;
import com.seed4j.LogsSpy;
import com.seed4j.LogsSpyExtension;
import com.seed4j.UnitTest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

@UnitTest
@ExtendWith(LogsSpyExtension.class)
class BeanValidationErrorsHandlerTest {

  private static final BeanValidationErrorsHandler handler = new BeanValidationErrorsHandler();

  @Logs
  private LogsSpy logs;

  @Test
  void shouldLogMethodArgumentNotValidInInfo() throws NoSuchMethodException {
    handler.handleMethodArgumentNotValid(
      new MethodArgumentNotValidException(
        new MethodParameter(BeanValidationErrorsHandlerTest.class.getMethod("failingMethod"), -1),
        mock(BindingResult.class)
      )
    );

    logs.shouldHave(Level.INFO, "failingMethod");
  }

  public void failingMethod() {
    // empty method
  }

  @Test
  void shouldLogConstraintViolationInInfo() {
    try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      handler.handleConstraintViolationException(
        new ConstraintViolationException(validatorFactory.getValidator().validate(new ValidatedBean()))
      );

      logs.shouldHave(Level.INFO, "parameter");
    }
  }

  static class ValidatedBean {

    @NotNull
    @SuppressWarnings("unused")
    private String parameter;
  }
}
