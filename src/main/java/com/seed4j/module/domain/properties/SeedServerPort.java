package com.seed4j.module.domain.properties;

import com.seed4j.shared.error.domain.Assert;

public record SeedServerPort(int serverPort) {
  private static final int DEFAULT_PORT = 8080;

  public SeedServerPort {
    Assert.field("serverPort", serverPort).min(1).max(65_535);
  }

  public SeedServerPort(Integer port) {
    this(buildPort(port));
  }

  private static int buildPort(Integer port) {
    if (port == null) {
      return DEFAULT_PORT;
    }

    return port;
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
