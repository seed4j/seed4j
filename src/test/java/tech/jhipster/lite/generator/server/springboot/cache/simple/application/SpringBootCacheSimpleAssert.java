package tech.jhipster.lite.generator.server.springboot.cache.simple.application;

import tech.jhipster.lite.generator.server.springboot.cache.common.application.SpringBootCacheAssert;
import tech.jhipster.lite.generator.tools.domain.Project;

public class SpringBootCacheSimpleAssert {

  public static void assertInit(Project project) {
    SpringBootCacheAssert.assertDependencies(project);
    SpringBootCacheAssert.assertEnableCaching(project);
  }
}
