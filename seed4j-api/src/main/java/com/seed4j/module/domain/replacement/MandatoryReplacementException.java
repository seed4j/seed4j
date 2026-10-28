package com.seed4j.module.domain.replacement;

import com.seed4j.shared.error.domain.GeneratorException;

public class MandatoryReplacementException extends GeneratorException {

  public MandatoryReplacementException(Throwable cause) {
    super(
      internalServerError(ReplacementErrorKey.MANDATORY_REPLACEMENT_ERROR).message("Error applying mandatory replacement").cause(cause)
    );
  }
}
