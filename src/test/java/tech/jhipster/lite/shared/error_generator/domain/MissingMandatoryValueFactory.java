package tech.jhipster.lite.shared.error_generator.domain;

import tech.jhipster.lite.shared.error.domain.MissingMandatoryValueException;

public final class MissingMandatoryValueFactory {

  public static void throwMissingMandatoryValue() {
    throw missingMandatoryValue();
  }

  public static MissingMandatoryValueException missingMandatoryValue() {
    return MissingMandatoryValueException.forNullValue("myField");
  }
}
