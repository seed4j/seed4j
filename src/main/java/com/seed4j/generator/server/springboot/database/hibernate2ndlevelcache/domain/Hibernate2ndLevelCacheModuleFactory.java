package com.seed4j.generator.server.springboot.database.hibernate2ndlevelcache.domain;

import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.propertyKey;
import static com.seed4j.module.domain.JHipsterModule.propertyValue;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.JHipsterDestination;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class Hibernate2ndLevelCacheModuleFactory {

  private static final String DEST_SECONDARY = "wire/cache/infrastructure/secondary";

  private static final JHipsterSource SOURCE = from("server/springboot/database/hibernate2ndlevelcache");

  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    JHipsterDestination testDestination = toSrcTestJava().append(packagePath).append(DEST_SECONDARY);

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(
          JavaDependency
            .builder()
            .groupId(groupId("org.hibernate.orm"))
            .artifactId(artifactId("hibernate-jcache"))
            .build()
        )
        .and()
      .springMainProperties()
        .set(propertyKey("spring.jpa.properties.hibernate.cache.use_second_level_cache"), propertyValue(true))
        .and()
      .files()
        .add(TEST_SOURCE.template("Hibernate2ndLevelCacheConfigurationIT.java"), testDestination.append("Hibernate2ndLevelCacheConfigurationIT.java"))
        .and()
      .build();
    // @formatter:on
  }
}
