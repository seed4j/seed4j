package com.seed4j.generator.server.springboot.mvc.sample.langchain4j.domain;

import static com.seed4j.module.domain.Seed4JModule.*;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class SampleLangChain4jModuleFactory {

  private static final String SAMPLE = "sample";

  private static final Seed4JSource SOURCE = from("server/springboot/mvc/sample/langchain4j");

  private static final String PRIMARY = "infrastructure/primary";

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    Seed4JDestination mainDestination = toSrcMainJava().append(packagePath).append(SAMPLE);
    Seed4JDestination testDestination = toSrcTestJava().append(packagePath).append(SAMPLE);

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
