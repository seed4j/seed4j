package tech.jhipster.lite.technical.infrastructure.primary.exception;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.violations.ConstraintViolationProblem;
import tech.jhipster.lite.error.domain.GeneratorException;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 */
@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling {

  private static final String FIELD_ERRORS_KEY = "fieldErrors";
  private static final String MESSAGE_KEY = "message";
  private static final String PATH_KEY = "path";
  private static final String VIOLATIONS_KEY = "violations";

  @Value("${spring.application.name:jhlite}")
  private String applicationName;

  @Value("${application.exception.details:false}")
  private boolean exceptionWithDetails;

  @Value("${application.exception.package}")
  private final List<String> packages = List.of();

  /**
   * Post-process the Problem payload to add the message key for the front-end if needed.
   */
  @Override
  public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
    if (entity == null) {
      return null;
    }
    Problem problem = entity.getBody();
    if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
      return entity;
    }

    HttpServletRequest nativeRequest = request.getNativeRequest(HttpServletRequest.class);
    String requestUri = nativeRequest != null ? nativeRequest.getRequestURI() : StringUtils.EMPTY;
    ProblemBuilder builder = Problem
      .builder()
      .withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? ErrorConstants.DEFAULT_TYPE : problem.getType())
      .withStatus(problem.getStatus())
      .withTitle(problem.getTitle())
      .with(PATH_KEY, requestUri);

    if (problem instanceof ConstraintViolationProblem constraintViolationProblem) {
      builder.with(VIOLATIONS_KEY, constraintViolationProblem.getViolations()).with(MESSAGE_KEY, ErrorConstants.ERR_VALIDATION);
    } else {
      builder.withCause(((DefaultProblem) problem).getCause()).withDetail(problem.getDetail()).withInstance(problem.getInstance());
      problem.getParameters().forEach(builder::with);
      if (!problem.getParameters().containsKey(MESSAGE_KEY)) {
        builder.with(MESSAGE_KEY, "error.http." + getStatusCode(problem));
      }
    }
    return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
  }

  private Integer getStatusCode(Problem problem) {
    return Optional.ofNullable(problem.getStatus()).map(StatusType::getStatusCode).orElse(500);
  }

  @Override
  public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @Nonnull NativeWebRequest request) {
    BindingResult result = ex.getBindingResult();
    List<FieldErrorDTO> fieldErrors = result
      .getFieldErrors()
      .stream()
      .map(f -> new FieldErrorDTO(f.getObjectName().replaceFirst("DTO$", ""), f.getField(), getMessage(f)))
      .toList();

    Problem problem = Problem
      .builder()
      .withType(ErrorConstants.CONSTRAINT_VIOLATION_TYPE)
      .withTitle("Method argument not valid")
      .withStatus(defaultConstraintViolationStatus())
      .with(MESSAGE_KEY, ErrorConstants.ERR_VALIDATION)
      .with(FIELD_ERRORS_KEY, fieldErrors)
      .build();
    return create(ex, problem, request);
  }

  private String getMessage(FieldError f) {
    return StringUtils.isNotBlank(f.getDefaultMessage()) ? f.getDefaultMessage() : f.getCode();
  }

  @ExceptionHandler
  public ResponseEntity<Problem> handleBadRequestAlertException(BadRequestAlertException ex, NativeWebRequest request) {
    return create(ex, request, HeaderUtil.createFailureAlert(applicationName, true, ex.getEntityName(), ex.getErrorKey(), ex.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<Problem> handleGenerationException(GeneratorException ex, NativeWebRequest request) {
    Problem problem = Problem.builder().withStatus(Status.BAD_REQUEST).withTitle(ex.getMessage()).build();
    return create(ex, problem, request);
  }

  // jhipster-needle-exception-translator

  @Override
  public ProblemBuilder prepare(final Throwable throwable, final StatusType status, final URI type) {
    if (!exceptionWithDetails) {
      if (throwable instanceof HttpMessageConversionException) {
        return Problem
          .builder()
          .withType(type)
          .withTitle(status.getReasonPhrase())
          .withStatus(status)
          .withDetail("Unable to convert http message");
      }
      if (containsPackageName(throwable.getMessage())) {
        return Problem
          .builder()
          .withType(type)
          .withTitle(status.getReasonPhrase())
          .withStatus(status)
          .withDetail("Unexpected runtime exception");
      }
    }

    return Problem.builder().withType(type).withTitle(status.getReasonPhrase()).withStatus(status).withDetail(throwable.getMessage());
  }

  private boolean containsPackageName(String message) {
    return packages != null && StringUtils.containsAny(message, packages.toArray(new CharSequence[0]));
  }
}
