package com.seed4j.generator.server.springboot.cache.caffeine.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class CaffeineCacheModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/cache/caffeine");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Caffeine"), SOURCE.template("caffeine.md"))
      .javaDependencies()
        .addDependency(groupId("com.github.ben-manes.caffeine"), artifactId("caffeine"))
        .and()
      .build();
    // @formatter:on
  }
}
