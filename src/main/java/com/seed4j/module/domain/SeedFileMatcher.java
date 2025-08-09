package com.seed4j.module.domain;

@FunctionalInterface
public interface SeedFileMatcher {
  boolean match(SeedProjectFilePath path);
}
