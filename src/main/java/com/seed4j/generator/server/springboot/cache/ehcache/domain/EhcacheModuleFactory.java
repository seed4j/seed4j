package com.seed4j.generator.server.springboot.cache.ehcache.domain;

import static com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.javaDependency;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.propertyKey;
import static com.seed4j.module.domain.JHipsterModule.propertyValue;
import static com.seed4j.module.domain.JHipsterModule.to;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.LogLevel;
import com.seed4j.module.domain.file.JHipsterDestination;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class EhcacheModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/cache/ehcache");

  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");

  private static final String EHCACHE_GROUP = "org.ehcache";

  private static final String CACHE_SECONDARY = "wire/cache/infrastructure/secondary";

  public JHipsterModule buildJavaConfigurationModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    JHipsterDestination mainDestination = toSrcMainJava().append(packagePath).append(CACHE_SECONDARY);
    JHipsterDestination testDestination = toSrcTestJava().append(packagePath).append(CACHE_SECONDARY);

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

  public JHipsterModule buildXmlConfigurationModule(JHipsterModuleProperties properties) {
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

  private JHipsterModuleBuilder commonEHCacheModuleBuilder(JHipsterModuleProperties properties) {
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
