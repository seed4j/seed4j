package tech.jhipster.lite.generator.project.domain;

import java.util.regex.Pattern;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.UnauthorizedValueException;

public class CheckConfig {

  public static final Pattern PATTERN_BASENAME = Pattern.compile("^[a-zA-Z0-9]+$");
  public static final Pattern PATTERN_PACKAGENAME = Pattern.compile("^([a-z_][a-z0-9_]*(\\.[a-z_]{1}[a-z0-9_]*+)*+)$");

  private CheckConfig() {}

  public static void validBaseName(String baseName) {
    Assert.notBlank("baseName", baseName);

    if (!PATTERN_BASENAME.matcher(baseName).find()) {
      throw new UnauthorizedValueException("The baseName cannot contain special characters or a blank space");
    }
  }

  public static void validPackageName(String packageName) {
    Assert.notBlank("packageName", packageName);

    if (!PATTERN_PACKAGENAME.matcher(packageName).find()) {
      throw new UnauthorizedValueException("The packageName is not a valid Java package name");
    }
  }
}
