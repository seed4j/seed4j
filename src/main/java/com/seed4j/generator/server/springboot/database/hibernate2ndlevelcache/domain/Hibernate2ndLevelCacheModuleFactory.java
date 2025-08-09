package com.seed4j.generator.server.springboot.database.hibernate2ndlevelcache.domain;

import static com.seed4j.module.domain.SeedModule.artifactId;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.groupId;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.propertyKey;
import static com.seed4j.module.domain.SeedModule.propertyValue;
import static com.seed4j.module.domain.SeedModule.toSrcTestJava;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class Hibernate2ndLevelCacheModuleFactory {

  private static final String DEST_SECONDARY = "wire/cache/infrastructure/secondary";

  private static final SeedSource SOURCE = from("server/springboot/database/hibernate2ndlevelcache");

  private static final SeedSource TEST_SOURCE = SOURCE.append("test");

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    SeedDestination testDestination = toSrcTestJava().append(packagePath).append(DEST_SECONDARY);

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
