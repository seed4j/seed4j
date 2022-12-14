package tech.jhipster.lite.dsl.common.infrastructure.primary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.error.domain.GeneratorException;

@Generated
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class InvalidProjectFolderException extends RuntimeException {

  public InvalidProjectFolderException() {
    super("Project folder is not valid");
  }
}
