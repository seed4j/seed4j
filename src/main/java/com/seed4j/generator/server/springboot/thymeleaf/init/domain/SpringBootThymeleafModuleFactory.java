package com.seed4j.generator.server.springboot.thymeleaf.init.domain;

import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.javaDependency;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SpringBootThymeleafModuleFactory {

  private static final GroupId SPRING_GROUP = groupId("org.springframework.boot");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
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
