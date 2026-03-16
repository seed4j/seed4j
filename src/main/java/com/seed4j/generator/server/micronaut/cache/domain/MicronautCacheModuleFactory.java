package com.seed4j.generator.server.micronaut.cache.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class MicronautCacheModuleFactory {

  public Seed4JModule buildCaffeineModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .javaDependencies()
      .addDependency(groupId("io.micronaut.cache"), artifactId("micronaut-cache-caffeine"))
      .and()
      .springMainProperties()
      .set(propertyKey("micronaut.caches.default.maximum-size"), propertyValue(100))
      .and()
      .build();
  }

  public Seed4JModule buildRedisModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .javaDependencies()
      .addDependency(groupId("io.micronaut.cache"), artifactId("micronaut-cache-redis"))
      .addDependency(groupId("io.micronaut.redis"), artifactId("micronaut-redis-lettuce"))
      .and()
      .springMainProperties()
      .set(propertyKey("redis.uri"), propertyValue("redis://localhost:6379"))
      .and()
      .build();
  }
}
