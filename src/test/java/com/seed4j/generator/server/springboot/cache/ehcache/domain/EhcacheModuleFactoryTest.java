package com.seed4j.generator.server.springboot.cache.ehcache.domain;

import static com.seed4j.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import com.seed4j.TestFileUtils;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.JHipsterModulesFixture;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.junit.jupiter.api.Test;

@UnitTest
class EhcacheModuleFactoryTest {

  private static final EhcacheModuleFactory factory = new EhcacheModuleFactory();

  @Test
  void shouldBuildJavaConfigurationModule() {
    SeedModule module = factory.buildJavaConfigurationModule(properties());

    commonEHCacheModuleAsserter(module)
      .hasFile("src/main/java/com/seed4j/growth/wire/cache/infrastructure/secondary/CacheConfiguration.java")
      .containing("JCacheManagerCustomizer")
      .and()
      .hasFiles("src/main/java/com/seed4j/growth/wire/cache/infrastructure/secondary/EhcacheProperties.java")
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth/wire/cache/infrastructure/secondary",
        "CacheConfigurationIT.java",
        "CacheConfigurationTest.java"
      )
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        application:
          cache:
            ehcache:
              max-entries: 100
              time-to-live-seconds: 3600
        """
      );
  }

  @Test
  void shouldBuildXmlConfigurationModule() {
    SeedModule module = factory.buildXmlConfigurationModule(properties());

    commonEHCacheModuleAsserter(module)
      .hasFiles("src/main/resources/config/ehcache/ehcache.xml")
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          cache:
            jcache:
              config: classpath:config/ehcache/ehcache.xml
        """
      );
  }

  private JHipsterModuleAsserter commonEHCacheModuleAsserter(SeedModule module) {
    return assertThatModuleWithFiles(module, pomFile(), logbackFile(), testLogbackFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>javax.cache</groupId>
              <artifactId>cache-api</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.ehcache</groupId>
              <artifactId>ehcache</artifactId>
              <classifier>jakarta</classifier>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/resources/logback-spring.xml")
      .containing("<logger name=\"org.ehcache\" level=\"WARN\" />")
      .and()
      .hasFile("src/test/resources/logback.xml")
      .containing("<logger name=\"org.ehcache\" level=\"WARN\" />")
      .and();
  }

  private SeedModuleProperties properties() {
    return JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).basePackage("com.seed4j.growth").build();
  }
}
