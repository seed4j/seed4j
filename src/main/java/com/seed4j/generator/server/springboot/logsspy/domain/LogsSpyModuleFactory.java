package com.seed4j.generator.server.springboot.logsspy.domain;

import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.toSrcTestJava;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class LogsSpyModuleFactory {

  private static final Seed4JSource SOURCE = from("server/springboot/logsspy");
  private static final Seed4JSource TEST_SOURCE = SOURCE.append("test");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
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
