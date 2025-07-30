package com.seed4j.module.domain;

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
