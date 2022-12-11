package tech.jhipster.lite.dsl.parser.domain;

import tech.jhipster.lite.error.domain.Assert;

public record JhipsterDslFileIdentifiant(String id) {
  public JhipsterDslFileIdentifiant {
    Assert.field("id", id).notBlank();
  }
}
