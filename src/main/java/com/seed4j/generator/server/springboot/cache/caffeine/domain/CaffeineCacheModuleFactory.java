package com.seed4j.generator.server.springboot.cache.caffeine.domain;

import static com.seed4j.module.domain.SeedModule.artifactId;
import static com.seed4j.module.domain.SeedModule.documentationTitle;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.groupId;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class CaffeineCacheModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/cache/caffeine");

  public SeedModule buildModule(SeedModuleProperties properties) {
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
