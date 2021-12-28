package tech.jhipster.lite.generator.server.springboot.cache.ehcache.domain;

import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

public class Ehcache {

  private Ehcache() {}

  public static Dependency ehcacheDependency() {
    return Dependency.builder().groupId("org.ehcache").artifactId("ehcache").build();
  }
}
