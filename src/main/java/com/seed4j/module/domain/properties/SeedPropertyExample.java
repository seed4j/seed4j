package com.seed4j.module.domain.properties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public record SeedPropertyExample(String example) {
  public SeedPropertyExample {
    Assert.notBlank("example", example);
  }

  public static Optional<SeedPropertyExample> of(String example) {
    return Optional.ofNullable(example).filter(StringUtils::isNotBlank).map(SeedPropertyExample::new);
  }

  public String get() {
    return example();
  }
}
