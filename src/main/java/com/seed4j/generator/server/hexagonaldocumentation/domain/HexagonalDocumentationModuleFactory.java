package com.seed4j.generator.server.hexagonaldocumentation.domain;

import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;
import static com.seed4j.module.domain.Seed4JModule.to;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class HexagonalDocumentationModuleFactory {

  private static final Seed4JSource SOURCE = from("server/documentation/hexagonal-application-service");

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    // @formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Hexagonal architecture"), SOURCE.file("hexagonal-application-service-architecture.md"))
      .files()
        .batch(SOURCE, to("documentation"))
          .addFile("hexagonal-flow.png")
          .addFile("hexagonal-global-schema.png")
          .and()
        .and()
      .build();
    // @formatter:on
  }
}
