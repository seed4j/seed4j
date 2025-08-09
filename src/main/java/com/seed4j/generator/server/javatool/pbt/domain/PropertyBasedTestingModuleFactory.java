package com.seed4j.generator.server.javatool.pbt.domain;

import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.versionSlug;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class PropertyBasedTestingModuleFactory {

  public JHipsterModule buildModule(SeedModuleProperties properties) {
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
