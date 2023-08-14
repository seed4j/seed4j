package tech.jhipster.lite.module.domain.packagejson;

import tech.jhipster.lite.shared.error.domain.Assert;

public record Script(ScriptKey key, ScriptCommand command) {
  public Script {
    Assert.notNull("key", key);
    Assert.notNull("command", command);
  }
}
