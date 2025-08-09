package com.seed4j.generator.server.springboot.devtools.domain;

import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.propertyKey;
import static com.seed4j.module.domain.JHipsterModule.propertyValue;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.javadependency.JavaDependencyScope;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class DevToolsModuleFactory {

  private static final GroupId SPRING_GROUP = groupId("org.springframework.boot");

  private static final SeedSource SOURCE = from("server/springboot/devtools");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
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
