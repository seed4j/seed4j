package com.seed4j.generator.setup.license.domain;

import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.to;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import java.time.Year;
import java.time.ZoneId;

public class LicenseModuleFactory {

  private static final JHipsterSource SOURCE = from("setup").append("license");

  public JHipsterModule buildMitModule(JHipsterModuleProperties properties) {
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

  public JHipsterModule buildApacheModule(JHipsterModuleProperties properties) {
    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .add(SOURCE.append("Apache.txt"), to("LICENSE.txt"))
        .and()
      .build();
    // @formatter:on
  }
}
