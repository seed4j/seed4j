package com.seed4j.generator.server.springboot.cache.caffeine.domain;

import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class CaffeineCacheModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/cache/caffeine");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
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
