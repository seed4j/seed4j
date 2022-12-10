package tech.jhipster.lite.dsl.parser.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.jhipster.lite.error.domain.GeneratorException;

class InvalidProjectFolderException extends GeneratorException {

  public InvalidProjectFolderException() {
    super("Project folder is not valid");
  }
}
