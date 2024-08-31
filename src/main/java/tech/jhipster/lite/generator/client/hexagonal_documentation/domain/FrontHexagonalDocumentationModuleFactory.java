package tech.jhipster.lite.generator.client.hexagonal_documentation.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class FrontHexagonalDocumentationModuleFactory {

  public static final JHipsterSource SOURCE = from("client/common/hexagonal-documentation");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return moduleBuilder(properties)
      .documentation(documentationTitle("Front hexagonal architecture"), SOURCE.template("front-hexagonal-architecture.md"))
      .build();
  }
}
