package tech.jhipster.lite.module.infrastructure.primary;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.jhipster.lite.shared.error.domain.GeneratorException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class InvalidProjectFolderException extends GeneratorException {

  public InvalidProjectFolderException() {
    super(badRequest(ProjectFolderErrorKey.INVALID_FOLDER).message("Project folder is not valid"));
  }
}
