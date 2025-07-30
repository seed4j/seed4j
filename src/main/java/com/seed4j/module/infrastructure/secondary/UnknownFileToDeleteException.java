package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.JHipsterProjectFilePath;
import com.seed4j.shared.error.domain.GeneratorException;

class UnknownFileToDeleteException extends GeneratorException {

  public UnknownFileToDeleteException(JHipsterProjectFilePath file) {
    super(badRequest(ModuleSecondaryErrorKey.UNKNOWN_FILE_TO_DELETE).message(buildMessage(file)).addParameter("file", file.get()));
  }

  private static String buildMessage(JHipsterProjectFilePath file) {
    return "File to delete %s, can't be found".formatted(file.get());
  }
}
