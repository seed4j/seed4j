package tech.jhipster.lite.generator.server.springboot.mvc.sample.langchain4j.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class SampleLangChain4jModuleFactory {

  private static final String SAMPLE = "sample";

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/sample/langchain4j");

  private static final String PRIMARY = "infrastructure/primary";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();
    JHipsterDestination mainDestination = toSrcMainJava().append(packagePath).append(SAMPLE);
    JHipsterDestination testDestination = toSrcTestJava().append(packagePath).append(SAMPLE);

    //@formatter:off
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
    //@formatter:on
  }
}
