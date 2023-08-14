package tech.jhipster.lite.generator.server.springboot.database.hibernate2ndlevelcache.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class Hibernate2ndLevelCacheModuleFactory {

  private static final String DEST_SECONDARY = "wire/cache/infrastructure/secondary";

  private static final JHipsterSource SOURCE = from("server/springboot/database/hibernate2ndlevelcache");

  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    JHipsterDestination testDestination = toSrcTestJava().append(packagePath).append(DEST_SECONDARY);

    //@formatter:off
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
        .set(propertyKey("spring.jpa.properties.hibernate.cache.use_second_level_cache"), propertyValue("true"))
        .and()
      .springTestProperties()
        .set(propertyKey("spring.jpa.properties.hibernate.cache.use_second_level_cache"), propertyValue("true"))
        .and()
      .files()
        .add(TEST_SOURCE.template("Hibernate2ndLevelCacheConfigurationIT.java"), testDestination.append("Hibernate2ndLevelCacheConfigurationIT.java"))
        .and()
      .build();
    //@formatter:on
  }
}
