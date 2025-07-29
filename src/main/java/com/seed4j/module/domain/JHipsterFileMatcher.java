package com.seed4j.module.domain;

@FunctionalInterface
public interface JHipsterFileMatcher {
  boolean match(JHipsterProjectFilePath path);
}
