package tech.jhipster.lite.error.infrastructure.primary;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.jhipster.lite.error.domain.GeneratorException;

@Order(100)
@ControllerAdvice
class ExceptionTranslator extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BadRequestAlertException.class)
  ProblemDetail handleBadRequestAlertException(BadRequestAlertException ex) {
    return buildProblemDetail(HttpStatus.BAD_REQUEST, ex.getMessage(), "Bad request error");
  }

  @ExceptionHandler(GeneratorException.class)
  ProblemDetail handleGenerationException(GeneratorException ex) {
    return buildProblemDetail(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  ProblemDetail handleAnyException(Throwable ex) {
    return buildProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
  }

  private static ProblemDetail buildProblemDetail(HttpStatus httpStatus, String message, String title) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, message);
    problemDetail.setTitle(title);
    problemDetail.setProperty("message", "error.http." + httpStatus.value());
    return problemDetail;
  }
}
