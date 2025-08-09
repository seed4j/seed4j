package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.SeedProjectFilePath;

public interface ContentReplacer {
  SeedProjectFilePath file();

  String apply(String content);

  void handleError(Throwable e);
}
