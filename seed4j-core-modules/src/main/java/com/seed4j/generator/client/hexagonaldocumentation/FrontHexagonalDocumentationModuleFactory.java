package com.seed4j.generator.client.hexagonaldocumentation;

import static com.seed4j.module.domain.Seed4JModule.documentationTitle;
import static com.seed4j.module.domain.Seed4JModule.from;
import static com.seed4j.module.domain.Seed4JModule.moduleBuilder;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.file.Seed4JSource;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.shared.error.domain.Assert;

class FrontHexagonalDocumentationModuleFactory {

  public static final Seed4JSource SOURCE = from("client/common/hexagonal-documentation");

  Seed4JModule buildModule(Seed4JModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .documentation(documentationTitle("Front hexagonal architecture"), SOURCE.template("front-hexagonal-architecture.md"))
      .build();
  }
}
