package com.seed4j.module.domain.properties;

import com.seed4j.shared.error.domain.Assert;

public record JHipsterServerPort(int serverPort) {
  private static final int DEFAULT_PORT = 8080;

  public JHipsterServerPort {
    Assert.field("serverPort", serverPort).min(1).max(65_535);
  }

  public JHipsterServerPort(Integer port) {
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
