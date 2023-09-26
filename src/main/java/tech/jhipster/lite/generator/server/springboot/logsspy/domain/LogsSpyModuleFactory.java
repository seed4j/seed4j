package tech.jhipster.lite.generator.server.springboot.logsspy.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class LogsSpyModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/logsspy");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Logs Spy"), SOURCE.file("logs-spy.md"))
      .files()
        .add(TEST_SOURCE.template("Logs.java"), toSrcTestJava().append(properties.packagePath()).append("Logs.java"))
        .add(TEST_SOURCE.template("LogsSpy.java"), toSrcTestJava().append(properties.packagePath()).append("LogsSpy.java"))
        .add(TEST_SOURCE.template("LogsSpyExtension.java"), toSrcTestJava().append(properties.packagePath()).append("LogsSpyExtension.java"))
        .and()
      .build();
    //@formatter:on
  }
}
