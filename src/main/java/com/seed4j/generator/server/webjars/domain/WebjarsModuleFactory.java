package com.seed4j.generator.server.webjars.domain;

import static com.seed4j.module.domain.SeedModule.groupId;
import static com.seed4j.module.domain.SeedModule.javaDependency;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class WebjarsModuleFactory {

  private static final String PROPERTIES_FIELD = "properties";
  private static final GroupId WEBJARS_GROUP = groupId("org.webjars");
  private static final GroupId WEBJARS_NPM_GROUP = groupId("org.webjars.npm");

  public SeedModule buildWebjarsLocatorModule(SeedModuleProperties properties) {
    Assert.notNull(PROPERTIES_FIELD, properties);

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(javaDependency().groupId(WEBJARS_GROUP).artifactId("webjars-locator-lite").build())
        .and()
      .build();
    // @formatter:on
  }

  public SeedModule buildWebjarsHtmxModule(SeedModuleProperties properties) {
    Assert.notNull(PROPERTIES_FIELD, properties);
    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(javaDependency().groupId(WEBJARS_NPM_GROUP).artifactId("htmx.org").versionSlug("htmx-webjars.version").build())
        .and()
      .build();
    // @formatter:on
  }

  public SeedModule buildWebjarsAlpineJSModule(SeedModuleProperties properties) {
    Assert.notNull(PROPERTIES_FIELD, properties);

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(javaDependency().groupId(WEBJARS_NPM_GROUP).artifactId("alpinejs").versionSlug("alpinejs-webjars.version").build())
        .and()
      .build();
    // @formatter:on
  }
}
