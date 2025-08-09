package com.seed4j.generator.server.springboot.cache.simple.domain;

import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SimpleCacheModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/cache/simple/src/");

  private static final String CACHE_SECONDARY = "wire/cache/infrastructure/secondary";

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter-cache"))
        .and()
      .files()
        .add(
          SOURCE.template("CacheConfiguration.java"),
          toSrcMainJava().append(properties.packagePath()).append(CACHE_SECONDARY).append("CacheConfiguration.java")
        )
        .and()
      .build();
    // @formatter:on
  }
}
