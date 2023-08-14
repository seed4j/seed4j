package tech.jhipster.lite.generator.server.springboot.cache.simple.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class SimpleCacheModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/cache/simple/src/");

  private static final String CACHE_SECONDARY = "wire/cache/infrastructure/secondary";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
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
    //@formatter:on
  }
}
