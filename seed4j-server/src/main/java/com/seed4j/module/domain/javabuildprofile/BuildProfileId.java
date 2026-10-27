package com.seed4j.module.domain.javabuildprofile;

public record BuildProfileId(String value) {
  @Override
  public String toString() {
    return value;
  }
}
