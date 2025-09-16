package com.seed4j.generator.server.springboot.devtools.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class DevToolsModuleFactory {

  private static final GroupId SPRING_GROUP = groupId("org.springframework.boot");

  private static final Seed4JSource SOURCE = from("server/springboot/devtools");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Dev tools"), SOURCE.template("devtools.md"))
      .javaDependencies()
        .addDependency(springBootDevtoolsDependency())
        .and()
      .springMainProperties()
        .set(propertyKey("spring.devtools.livereload.enabled"), propertyValue(false))
        .set(propertyKey("spring.devtools.restart.enabled"), propertyValue(false))
        .and()
      .springLocalProperties()
        .set(propertyKey("spring.devtools.livereload.enabled"), propertyValue(true))
        .set(propertyKey("spring.devtools.restart.enabled"), propertyValue(true))
        .and()
      .build();
    // @formatter:on
  }

  private JavaDependency springBootDevtoolsDependency() {
    return JavaDependency.builder()
      .groupId(SPRING_GROUP)
      .artifactId(artifactId("spring-boot-devtools"))
      .scope(JavaDependencyScope.RUNTIME)
      .optional()
      .build();
  }
}
