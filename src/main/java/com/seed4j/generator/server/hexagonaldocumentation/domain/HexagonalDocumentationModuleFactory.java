package com.seed4j.generator.server.hexagonaldocumentation.domain;

import static com.seed4j.module.domain.SeedModule.documentationTitle;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;
import static com.seed4j.module.domain.SeedModule.to;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class HexagonalDocumentationModuleFactory {

  private static final SeedSource SOURCE = from("server/documentation/hexagonal-application-service");

  public SeedModule buildModule(SeedModuleProperties properties) {
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
