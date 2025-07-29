package com.seed4j.module.domain.packagejson;

import com.seed4j.shared.error.domain.Assert;

public record ScriptKey(String key) {
  public ScriptKey {
    Assert.notBlank("key", key);
  }

  public String get() {
    return key();
  }
}
