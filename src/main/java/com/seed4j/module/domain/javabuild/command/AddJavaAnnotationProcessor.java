package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.javadependency.JavaAnnotationProcessorDependency;
import com.seed4j.shared.error.domain.Assert;

public record AddJavaAnnotationProcessor(JavaAnnotationProcessorDependency dependency) implements JavaBuildCommand {
  public AddJavaAnnotationProcessor {
    Assert.notNull("dependency", dependency);
  }
}
