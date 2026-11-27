package com.seed4j.generator.server.springboot.banner;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.to;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

class BannerModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/banner");

  Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("banner", AsciiArt.from(properties.projectBaseName()).text())
        .and()
      .files()
        .add(SOURCE.template("banner.txt"), to("src/main/resources/banner.txt"))
        .and()
      .build();
    // @formatter:on
  }
}
