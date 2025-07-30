package com.seed4j.module.domain.packagejson;

import com.seed4j.shared.error.domain.Assert;

public record Script(ScriptKey key, ScriptCommand command) {
  public Script {
    Assert.notNull("key", key);
    Assert.notNull("command", command);
  }
}
