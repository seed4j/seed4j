package com.seed4j.generator.server.springboot.springcloud.common.domain;

import static com.seed4j.module.domain.SeedModule.groupId;

import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.javadependency.JavaDependencyType;

public final class SpringCloudModuleDependencies {

  public static final GroupId SPRING_CLOUD_GROUP = groupId("org.springframework.cloud");

  private SpringCloudModuleDependencies() {}

  public static JavaDependency springCloudDependenciesManagement() {
    return JavaDependency.builder()
      .groupId(SPRING_CLOUD_GROUP)
      .artifactId("spring-cloud-dependencies")
      .versionSlug("spring-cloud.version")
      .scope(JavaDependencyScope.IMPORT)
      .type(JavaDependencyType.POM)
      .build();
  }
}
