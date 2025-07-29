package com.seed4j.shared.error_generator.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

class RestMandatoryParameter {

  private final String parameter;

  public RestMandatoryParameter(@JsonProperty("parameter") String parameter) {
    this.parameter = parameter;
  }

  @NotBlank
  public String getParameter() {
    return parameter;
  }
}
