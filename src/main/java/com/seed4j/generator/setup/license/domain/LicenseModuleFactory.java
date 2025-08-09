package com.seed4j.generator.setup.license.domain;

import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.to;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import java.time.Year;
import java.time.ZoneId;

public class LicenseModuleFactory {

  private static final SeedSource SOURCE = from("setup").append("license");

  public SeedModule buildMitModule(SeedModuleProperties properties) {
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

  public SeedModule buildApacheModule(SeedModuleProperties properties) {
    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.append("Apache.txt"), to("LICENSE.txt"))
        .and()
      .build();
    // @formatter:on
  }
}
