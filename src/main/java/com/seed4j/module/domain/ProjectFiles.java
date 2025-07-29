package com.seed4j.module.domain;

import java.util.Collection;

public interface ProjectFiles {
  String readString(String path);

  byte[] readBytes(String path);

  Collection<String> findRecursivelyInPath(String path);
}
