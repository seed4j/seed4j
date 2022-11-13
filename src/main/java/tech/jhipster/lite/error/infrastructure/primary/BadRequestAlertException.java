package tech.jhipster.lite.error.infrastructure.primary;

import java.io.Serial;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

@SuppressWarnings("squid:MaximumInheritanceDepth")
class BadRequestAlertException extends ErrorResponseException {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String entityName;

  private final String errorKey;

  public BadRequestAlertException(String defaultMessage, String entityName, String errorKey) {
    this(ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, errorKey);
  }

  public BadRequestAlertException(URI type, String defaultMessage, String entityName, String errorKey) {
    super(HttpStatus.BAD_REQUEST, buildProblemDetail(type, defaultMessage, entityName, errorKey), null);
    this.entityName = entityName;
    this.errorKey = errorKey;
  }

  private static ProblemDetail buildProblemDetail(URI type, String defaultMessage, String entityName, String errorKey) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problemDetail.setType(type);
    problemDetail.setTitle(defaultMessage);
    problemDetail.setProperty("message", "error." + errorKey);
    problemDetail.setProperty("params", entityName);
    return problemDetail;
  }

  public String getEntityName() {
    return entityName;
  }

  public String getErrorKey() {
    return errorKey;
  }
}
