package tech.jhipster.lite.module.infrastructure.secondary;

import tech.jhipster.lite.shared.error.domain.GeneratorException;

class UnknownFileToMoveException extends GeneratorException {

  public UnknownFileToMoveException(String filename) {
    super(badRequest(ModuleSecondaryErrorKey.UNKNOWN_FILE_TO_MOVE).message(buildMessage(filename)).addParameter("file", filename));
  }

  private static String buildMessage(String filename) {
    return new StringBuilder().append("Can't move ").append(filename).append(", can't find it in project").toString();
  }
}
