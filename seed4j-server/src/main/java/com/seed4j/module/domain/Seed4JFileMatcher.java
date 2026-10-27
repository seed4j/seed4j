package com.seed4j.module.domain;

@FunctionalInterface
public interface Seed4JFileMatcher {
  boolean match(Seed4JProjectFilePath path);
}
