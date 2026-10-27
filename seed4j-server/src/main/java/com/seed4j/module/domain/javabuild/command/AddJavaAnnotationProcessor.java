package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.javadependency.JavaAnnotationProcessor;
import com.seed4j.shared.error.domain.Assert;

public record AddJavaAnnotationProcessor(JavaAnnotationProcessor annotationProcessor) implements JavaBuildCommand {
  public AddJavaAnnotationProcessor {
    Assert.notNull("annotationProcessor", annotationProcessor);
  }
}
