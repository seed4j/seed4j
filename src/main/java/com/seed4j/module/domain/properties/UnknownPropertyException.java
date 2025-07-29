package com.seed4j.module.domain.properties;

import com.seed4j.shared.error.domain.GeneratorException;

class UnknownPropertyException extends GeneratorException {

  public UnknownPropertyException(String key) {
    super(badRequest(PropertiesErrorKey.UNKNOWN_PROPERTY).message("Unknown property " + key).addParameter("key", key));
  }
}
