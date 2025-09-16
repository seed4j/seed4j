package com.seed4j.generator.server.springboot.cache.simple.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SimpleCacheModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/cache/simple/src/");

  private static final String CACHE_SECONDARY = "wire/cache/infrastructure/secondary";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
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
