package tech.jhipster.lite.module.domain;

public enum LogLevel {
  OFF,
  ERROR,
  WARN,
  INFO,
  DEBUG,
  TRACE,
  ALL;

  String value() {
    return name();
  }
}
