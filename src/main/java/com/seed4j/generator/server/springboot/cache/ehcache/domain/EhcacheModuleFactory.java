package com.seed4j.generator.server.springboot.cache.ehcache.domain;

import static com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.javaDependency;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.to;
import static com.seed4j.module.domain.Seed4JModule.toSrcMainJava;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class EhcacheModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/cache/ehcache");

  private static final Seed4JSource MAIN_SOURCE = SOURCE.append("main");
  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");

  private static final String EHCACHE_GROUP = "org.ehcache";

  private static final String CACHE_SECONDARY = "wire/cache/infrastructure/secondary";

  public Seed4JModule buildJavaConfigurationModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    Seed4JDestination mainDestination = toSrcMainJava().append(packagePath).append(CACHE_SECONDARY);
    Seed4JDestination testDestination = toSrcTestJava().append(packagePath).append(CACHE_SECONDARY);

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

  public Seed4JModule buildXmlConfigurationModule(Seed4JModuleProperties properties) {
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

  private Seed4JModuleBuilder commonEHCacheModuleBuilder(Seed4JModuleProperties properties) {
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
