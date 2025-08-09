package com.seed4j.generator.client.hexagonaldocumentation.domain;

import static com.seed4j.module.domain.SeedModule.documentationTitle;
import static com.seed4j.module.domain.SeedModule.from;
import static com.seed4j.module.domain.SeedModule.moduleBuilder;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class FrontHexagonalDocumentationModuleFactory {

  public static final SeedSource SOURCE = from("client/common/hexagonal-documentation");

  public SeedModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .documentation(documentationTitle("Front hexagonal architecture"), SOURCE.template("front-hexagonal-architecture.md"))
      .build();
  }
}
