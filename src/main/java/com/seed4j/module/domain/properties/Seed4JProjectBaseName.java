package com.seed4j.module.domain.properties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public record Seed4JProjectBaseName(String name) {
  private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");
  private static final String DEFAULT_NAME = "seed4j";

  public static final Seed4JProjectBaseName DEFAULT = new Seed4JProjectBaseName(DEFAULT_NAME);

  public Seed4JProjectBaseName(String name) {
    this.name = buildName(name);
  }

  private static String buildName(String name) {
    if (StringUtils.isBlank(name)) {
      return DEFAULT_NAME;
    }

    Assert.field("baseName", name).matchesPatternOrThrow(NAME_PATTERN, InvalidProjectBaseNameException::new);

    return name;
  }

  public String get() {
    return name();
  }

  public String uncapitalized() {
    return StringUtils.uncapitalize(name());
  }

  public String capitalized() {
    return StringUtils.capitalize(name());
  }

  public String upperCased() {
    return StringUtils.uncapitalize(name()).replaceAll("([A-Z])", "_$1").toUpperCase(Locale.ROOT);
  }

  public String kebabCase() {
    return StringUtils.uncapitalize(name()).replaceAll("([A-Z])", "-$1").toLowerCase(Locale.ROOT);
  }
}
