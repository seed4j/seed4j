package com.seed4j.module.domain.properties;

import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public record Seed4JPropertyExample(String example) {
  public Seed4JPropertyExample {
    Assert.notBlank("example", example);
  }

  public static Optional<Seed4JPropertyExample> of(String example) {
    return Optional.ofNullable(example).filter(StringUtils::isNotBlank).map(Seed4JPropertyExample::new);
  }

  public String get() {
    return example();
  }
}
