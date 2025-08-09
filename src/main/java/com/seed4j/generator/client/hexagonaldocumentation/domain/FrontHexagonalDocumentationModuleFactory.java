package com.seed4j.generator.client.hexagonaldocumentation.domain;

import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import com.seed4j.shared.error.domain.Assert;

public class FrontHexagonalDocumentationModuleFactory {

  public static final SeedSource SOURCE = from("client/common/hexagonal-documentation");

  public JHipsterModule buildModule(SeedModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .documentation(documentationTitle("Front hexagonal architecture"), SOURCE.template("front-hexagonal-architecture.md"))
      .build();
  }
}
