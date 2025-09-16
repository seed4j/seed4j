package com.seed4j.generator.setup.license.domain;

import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.to;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import java.time.Year;
import java.time.ZoneId;

public class LicenseModuleFactory {

  private static final Seed4JSource SOURCE = from("setup").append("license");

  public Seed4JModule buildMitModule(Seed4JModuleProperties properties) {
    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("currentYear", Year.now(ZoneId.systemDefault()).getValue())
        .and()
      .files()
        .add(SOURCE.template("MIT.txt"), to("LICENSE.txt"))
        .and()
      .build();
    // @formatter:on
  }

  public Seed4JModule buildApacheModule(Seed4JModuleProperties properties) {
    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.append("Apache.txt"), to("LICENSE.txt"))
        .and()
      .build();
    // @formatter:on
  }
}
