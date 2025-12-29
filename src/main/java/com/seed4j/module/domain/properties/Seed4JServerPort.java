package com.seed4j.module.domain.properties;

import com.seed4j.shared.error.domain.Assert;

public record Seed4JServerPort(int serverPort) {
  private static final int DEFAULT_PORT = 8080;
  public static final Seed4JServerPort DEFAULT = new Seed4JServerPort(DEFAULT_PORT);

  public Seed4JServerPort {
    Assert.field("serverPort", serverPort).min(1).max(65_535);
  }

  public int get() {
    return serverPort;
  }

  public String stringValue() {
    return String.valueOf(serverPort);
  }

  @Override
  public String toString() {
    return stringValue();
  }
}
