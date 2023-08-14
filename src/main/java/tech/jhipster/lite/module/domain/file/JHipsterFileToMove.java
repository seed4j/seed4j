package tech.jhipster.lite.module.domain.file;

import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;
import tech.jhipster.lite.shared.error.domain.Assert;

public record JHipsterFileToMove(JHipsterProjectFilePath source, JHipsterDestination destination) {
  public JHipsterFileToMove {
    Assert.notNull("source", source);
    Assert.notNull("destination", destination);
  }
}
