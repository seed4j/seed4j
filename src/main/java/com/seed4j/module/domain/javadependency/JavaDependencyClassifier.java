package com.seed4j.module.domain.javadependency;

import com.seed4j.shared.error.domain.Assert;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public record JavaDependencyClassifier(String classifier) {
  public JavaDependencyClassifier {
    Assert.notBlank("classifier", classifier);
  }

  public static Optional<JavaDependencyClassifier> of(String classifier) {
    return Optional.ofNullable(classifier).filter(StringUtils::isNotBlank).map(JavaDependencyClassifier::new);
  }

  public String get() {
    return classifier();
  }
}
