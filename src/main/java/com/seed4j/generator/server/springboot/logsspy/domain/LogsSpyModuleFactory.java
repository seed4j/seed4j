package com.seed4j.generator.server.springboot.logsspy.domain;

import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class LogsSpyModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/logsspy");
  private static final SeedSource TEST_SOURCE = SOURCE.append("test");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Logs Spy"), SOURCE.file("logs-spy.md"))
      .files()
        .add(TEST_SOURCE.template("Logs.java"), toSrcTestJava().append(properties.packagePath()).append("Logs.java"))
        .add(TEST_SOURCE.template("LogsSpy.java"), toSrcTestJava().append(properties.packagePath()).append("LogsSpy.java"))
        .add(TEST_SOURCE.template("LogsSpyExtension.java"), toSrcTestJava().append(properties.packagePath()).append("LogsSpyExtension.java"))
        .and()
      .build();
    // @formatter:on
  }
}
