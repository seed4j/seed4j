package tech.jhipster.lite.generator.module.domain;

import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public record JHipsterProjectBaseName(String name) {
  private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");
  public static final String DEFAULT_PROJECT_NAME = "jhipster";

  public JHipsterProjectBaseName(String name) {
    this.name = buildName(name);
  }

  private String buildName(String name) {
    if (StringUtils.isBlank(name)) {
      return DEFAULT_PROJECT_NAME;
    }

    assertValidName(name);

    return name;
  }

  private void assertValidName(String name) {
    if (!NAME_PATTERN.matcher(name).matches()) {
      throw new InvalidProjectBaseNameException();
    }
  }

  public String get() {
    return name();
  }

  public String capitalized() {
    return StringUtils.capitalize(name());
  }
}
