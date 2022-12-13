package tech.jhipster.lite.dsl.parser.domain;

import java.util.Arrays;
import tech.jhipster.lite.error.domain.Assert;

public class JhipsterDslFileToSave {

  private String name;
  private byte[] bytes;

  public String getName() {
    return name;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public JhipsterDslFileToSave(JhipsterDslFileToImport from) {
    Assert.notNull("from", from);
    this.name = from.getOriginalFilename();
    this.bytes = from.getBytes();
  }
}
