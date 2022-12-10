package tech.jhipster.lite.dsl.common.infrastructure.primary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.jhipster.lite.error.domain.GeneratorException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class InvalidProjectFolderException extends GeneratorException {

  public InvalidProjectFolderException() {
    super("Project folder is not valid");
  }
}
