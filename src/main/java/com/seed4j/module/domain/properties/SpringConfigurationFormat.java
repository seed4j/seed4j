package com.seed4j.module.domain.properties;

import com.seed4j.shared.error.domain.Assert;
import java.util.stream.Stream;

public enum SpringConfigurationFormat {
  YAML("yaml"),
  PROPERTIES("properties");

  private final String format;

  SpringConfigurationFormat(String format) {
    Assert.notNull(format, "format");

    this.format = format;
  }

  public static SpringConfigurationFormat from(String input) {
    return Stream.of(values())
      .filter(configFormat -> configFormat.format.equals(input))
      .findFirst()
      .orElse(null);
  }

  public String get() {
    return format;
  }
}
