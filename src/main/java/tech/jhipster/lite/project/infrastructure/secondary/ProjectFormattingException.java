package tech.jhipster.lite.project.infrastructure.secondary;

import tech.jhipster.lite.shared.error.domain.GeneratorException;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@ExcludeFromGeneratedCodeCoverage
class ProjectFormattingException extends GeneratorException {

  public ProjectFormattingException(String message) {
    super(internalServerError(ProjectErrorKey.FORMATTING_ERROR).message(message));
  }

  public ProjectFormattingException(String message, Throwable cause) {
    super(internalServerError(ProjectErrorKey.FORMATTING_ERROR).message(message).cause(cause));
  }
}
