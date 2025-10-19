package com.seed4j.module.domain.javabuild.command;

import com.seed4j.module.domain.javadependency.JavaDependency;

public sealed interface AddJavaDependency permits AddDirectJavaDependency, AddJavaAnnotationProcessor, AddJavaDependencyManagement {
  JavaDependency dependency();
}
