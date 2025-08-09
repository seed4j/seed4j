package com.seed4j.generator.server.springboot.cache.ehcache.domain;

import static com.seed4j.module.domain.SeedModule.SeedModuleBuilder;
import static com.seed4j.module.domain.SeedModule.artifactId;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.groupId;
import static com.seed4j.module.domain.SeedModule.javaDependency;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.propertyKey;
import static com.seed4j.module.domain.SeedModule.propertyValue;
import static com.seed4j.module.domain.SeedModule.to;
import static com.seed4j.module.domain.SeedModule.toSrcMainJava;
import static com.seed4j.module.domain.SeedModule.toSrcTestJava;

import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class EhcacheModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/cache/ehcache");

  private static final SeedSource MAIN_SOURCE = SOURCE.append("main");
  private static final SeedSource TEST_SOURCE = SOURCE.append("test");

  private static final String EHCACHE_GROUP = "org.ehcache";

  private static final String CACHE_SECONDARY = "wire/cache/infrastructure/secondary";

  public SeedModule buildJavaConfigurationModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    SeedDestination mainDestination = toSrcMainJava().append(packagePath).append(CACHE_SECONDARY);
    SeedDestination testDestination = toSrcTestJava().append(packagePath).append(CACHE_SECONDARY);

    // @formatter:off
    return commonEHCacheModuleBuilder(properties)
      .files()
        .add(MAIN_SOURCE.template("JavaCacheConfiguration.java"), mainDestination.append("CacheConfiguration.java"))
        .add(MAIN_SOURCE.template("EhcacheProperties.java"), mainDestination.append("EhcacheProperties.java"))
        .add(TEST_SOURCE.template("JavaCacheConfigurationIT.java"), testDestination.append("CacheConfigurationIT.java"))
        .add(TEST_SOURCE.template("CacheConfigurationTest.java"), testDestination.append("CacheConfigurationTest.java"))
        .and()
      .springMainProperties()
        .set(propertyKey("application.cache.ehcache.max-entries"), propertyValue(100))
        .set(propertyKey("application.cache.ehcache.time-to-live-seconds"), propertyValue(3600))
        .and()
      .build();
    // @formatter:on
  }

  public SeedModule buildXmlConfigurationModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return commonEHCacheModuleBuilder(properties)
      .files()
        .add(SOURCE.file("resources/ehcache.xml"), to("src/main/resources/config/ehcache/ehcache.xml"))
        .and()
      .springMainProperties()
        .set(propertyKey("spring.cache.jcache.config"), propertyValue("classpath:config/ehcache/ehcache.xml"))
        .and()
      .build();
    // @formatter:on
  }

  private SeedModuleBuilder commonEHCacheModuleBuilder(SeedModuleProperties properties) {
    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(groupId("javax.cache"), artifactId("cache-api"))
        .addDependency(javaDependency().groupId(EHCACHE_GROUP).artifactId("ehcache").classifier("jakarta").build())
        .and()
      .springMainLogger(EHCACHE_GROUP, LogLevel.WARN)
      .springTestLogger(EHCACHE_GROUP, LogLevel.WARN);
    // @formatter:on
  }
}
