package com.seed4j.generator.server.springboot.mvc.sample.langchain4j.domain;

import static com.seed4j.module.domain.JHipsterModule.*;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.JHipsterDestination;
import com.seed4j.module.domain.file.JHipsterSource;
import com.seed4j.module.domain.properties.JHipsterModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SampleLangChain4jModuleFactory {

  private static final String SAMPLE = "sample";

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/sample/langchain4j");

  private static final String PRIMARY = "infrastructure/primary";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    JHipsterDestination mainDestination = toSrcMainJava().append(packagePath).append(SAMPLE);
    JHipsterDestination testDestination = toSrcTestJava().append(packagePath).append(SAMPLE);

    // @formatter:off
    return moduleBuilder(properties)
      .files()
        .batch(SOURCE.append("main").append(PRIMARY), mainDestination.append(PRIMARY))
          .addTemplate("ChatResource.java")
          .and()
        .batch(SOURCE.append("test").append(SAMPLE).append(PRIMARY), testDestination.append(PRIMARY))
          .addTemplate("ChatResourceTest.java")
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
