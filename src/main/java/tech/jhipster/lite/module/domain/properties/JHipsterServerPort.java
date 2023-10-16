package tech.jhipster.lite.module.domain.properties;

import tech.jhipster.lite.shared.error.domain.Assert;

public class JHipsterServerPort {

  private static final int DEFAULT_PORT = 8080;

  private final int serverPort;

  public JHipsterServerPort(Integer port) {
    serverPort = buildPort(port);

    Assert.field("serverPort", serverPort).min(1).max(65_535);
  }

  private int buildPort(Integer port) {
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
