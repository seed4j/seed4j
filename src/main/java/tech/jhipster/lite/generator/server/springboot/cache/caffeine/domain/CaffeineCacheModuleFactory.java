package tech.jhipster.lite.generator.server.springboot.cache.caffeine.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class CaffeineCacheModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/cache/caffeine");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Caffeine"), SOURCE.template("caffeine.md"))
      .javaDependencies()
        .addDependency(groupId("com.github.ben-manes.caffeine"), artifactId("jcache"))
        .addDependency(groupId("javax.cache"), artifactId("cache-api"))
        .and()
      .build();
    //@formatter:on
  }
}
