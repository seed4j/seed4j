package com.seed4j.generator.server.webjars.domain;

import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.javaDependency;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class WebjarsModuleFactory {

  private static final String PROPERTIES_FIELD = "properties";
  private static final GroupId WEBJARS_GROUP = groupId("org.webjars");
  private static final GroupId WEBJARS_NPM_GROUP = groupId("org.webjars.npm");

  public Seed4JModule buildWebjarsLocatorModule(Seed4JModuleProperties properties) {
    Assert.notNull(PROPERTIES_FIELD, properties);

    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(javaDependency().groupId(WEBJARS_GROUP).artifactId("webjars-locator-lite").build())
        .and()
      .build();
    // @formatter:on
  }

  public Seed4JModule buildWebjarsHtmxModule(Seed4JModuleProperties properties) {
    Assert.notNull(PROPERTIES_FIELD, properties);
    // @formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(javaDependency().groupId(WEBJARS_NPM_GROUP).artifactId("htmx.org").versionSlug("htmx-webjars.version").build())
        .and()
      .build();
    // @formatter:on
  }

  public Seed4JModule buildWebjarsAlpineJSModule(Seed4JModuleProperties properties) {
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
