package tech.jhipster.lite.generator.server.springboot.cache.jcache.domain;

import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

public class SpringBootJCache {

  private SpringBootJCache() {}

  public static Dependency cacheApiDependency() {
    return Dependency.builder().groupId("javax.cache").artifactId("cache-api").build();
  }
}
