package com.seed4j.generator.server.javatool.pbt.domain;

import static com.seed4j.module.domain.Seed4JModule.artifactId;
import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.groupId;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.versionSlug;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class PropertyBasedTestingModuleFactory {

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .gitIgnore()
        .comment("JQwik")
        .pattern(".jqwik-database")
        .and()
      .documentation(documentationTitle("Property Based Testing"), from("server/javatool/pbt/property-based-testing.md"))
      .javaDependencies()
        .addTestDependency(groupId("net.jqwik"), artifactId("jqwik"), versionSlug("jqwik"))
        .and()
      .build();
    // @formatter:on
  }
}
