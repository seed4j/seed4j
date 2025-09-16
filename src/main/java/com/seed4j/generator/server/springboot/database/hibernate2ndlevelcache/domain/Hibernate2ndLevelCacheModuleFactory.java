package com.seed4j.generator.server.springboot.database.hibernate2ndlevelcache.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.propertyKey;
import static com.seed4j.module.domain.Seed4JModule.propertyValue;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javadependency.JavaDependency;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class Hibernate2ndLevelCacheModuleFactory {

  private static final String DEST_SECONDARY = "wire/cache/infrastructure/secondary";

  private static final Seed4JSource SOURCE = from("server/springboot/database/hibernate2ndlevelcache");

  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    Seed4JDestination testDestination = toSrcTestJava().append(packagePath).append(DEST_SECONDARY);

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
