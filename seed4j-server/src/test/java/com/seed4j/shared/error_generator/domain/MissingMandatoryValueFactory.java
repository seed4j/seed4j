package com.seed4j.shared.error_generator.domain;

import com.seed4j.shared.error.domain.MissingMandatoryValueException;

public final class MissingMandatoryValueFactory {

  public static void throwMissingMandatoryValue() {
    throw missingMandatoryValue();
  }

  public static MissingMandatoryValueException missingMandatoryValue() {
    return MissingMandatoryValueException.forNullValue("myField");
  }
}
