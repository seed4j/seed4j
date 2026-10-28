package com.seed4j.module.domain.packagejson;

import com.seed4j.shared.error.domain.Assert;

public record ScriptCommand(String command) {
  public ScriptCommand {
    Assert.notBlank("command", command);
  }

  public String get() {
    return command();
  }
}
