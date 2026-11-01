package com.seed4j.module.infrastructure.secondary;

import com.seed4j.shared.error.domain.GeneratorException;

class UnknownFileToMoveException extends GeneratorException {

  public UnknownFileToMoveException(String filename) {
    super(badRequest(ModuleSecondaryErrorKey.UNKNOWN_FILE_TO_MOVE).message(buildMessage(filename)).addParameter("file", filename));
  }

  private static String buildMessage(String filename) {
    return "Can't move %s, can't find it in project".formatted(filename);
  }
}
