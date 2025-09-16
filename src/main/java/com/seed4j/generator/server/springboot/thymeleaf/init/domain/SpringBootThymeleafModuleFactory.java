package com.seed4j.generator.server.springboot.thymeleaf.init.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.javaDependency;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SpringBootThymeleafModuleFactory {

  private static final GroupId SPRING_GROUP = groupId("org.springframework.boot");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(SPRING_GROUP, artifactId("spring-boot-starter-thymeleaf"))
        .addDependency(javaDependency().groupId("nz.net.ultraq.thymeleaf").artifactId("thymeleaf-layout-dialect").versionSlug("thymeleaf-layout-dialect.version").build())
        .and()
      .build();
    // @formatter:on
  }
}
