package com.seed4j.module.infrastructure.primary;

import com.seed4j.shared.error.domain.GeneratorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class InvalidProjectFolderException extends GeneratorException {

  public InvalidProjectFolderException(String folder) {
    super(badRequest(ProjectFolderErrorKey.INVALID_FOLDER).message("Project folder is not valid: " + folder));
  }
}
