package tech.jhipster.lite.dsl.common.infrastructure.primary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.jhipster.lite.common.domain.Generated;

@Generated
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class InvalidDslFileException extends RuntimeException {

  public InvalidDslFileException(String message) {
    super(message);
  }

  public InvalidDslFileException(String message, Throwable cause) {
    super(message, cause);
  }
}
