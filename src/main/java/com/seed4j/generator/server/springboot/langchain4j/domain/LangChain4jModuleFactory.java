package com.seed4j.generator.server.springboot.langchain4j.domain;

import static com.seed4j.module.domain.Seed4JModule.*;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.javabuild.ArtifactId;
import com.seed4j.module.domain.javabuild.GroupId;
import com.seed4j.module.domain.javabuild.VersionSlug;
import com.seed4j.module.domain.javaproperties.PropertyKey;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class LangChain4jModuleFactory {

  private static final String API_KEY_DEMO_COMMENT =
    "You can temporarily use 'demo' key, which is provided for free for demonstration purposes";

  private static final Seed4JSource SOURCE = from("server/springboot/langchain4j");
  private static final GroupId GROUP_ID = groupId("dev.langchain4j");
  private static final ArtifactId ARTIFACT_ID = artifactId("langchain4j-spring-boot-starter");
  private static final ArtifactId OPEN_AI_ARTIFACT_ID = artifactId("langchain4j-open-ai-spring-boot-starter");
  private static final VersionSlug VERSION_SLUG = versionSlug("langchain4j");

  private static final String PROPERTIES = "properties";
  private static final PropertyKey LANGCHAIN4J_PROPERTY_API_KEY = propertyKey("langchain4j.open-ai.chat-model.api-key");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull(PROPERTIES, properties);

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("LangChain4j"), SOURCE.template("langchain4j.md"))
      .javaDependencies()
        .addDependency(GROUP_ID, ARTIFACT_ID, VERSION_SLUG)
        .addDependency(GROUP_ID, OPEN_AI_ARTIFACT_ID, VERSION_SLUG)
        .and()
      .springMainProperties()
        .set(LANGCHAIN4J_PROPERTY_API_KEY, propertyValue("demo"))
          .comment(LANGCHAIN4J_PROPERTY_API_KEY, comment(API_KEY_DEMO_COMMENT))
        .set(propertyKey("langchain4j.open-ai.chat-model.model-name"), propertyValue("gpt-4o-mini"))
        .set(propertyKey("langchain4j.open-ai.chat-model.log-requests"), propertyValue("true"))
        .set(propertyKey("langchain4j.open-ai.chat-model.log-responses"), propertyValue("true"))
        .and()
      .springTestProperties()
        .set(LANGCHAIN4J_PROPERTY_API_KEY, propertyValue("seed4j"))
        .and()
      .build();
    // @formatter:on
  }
}
