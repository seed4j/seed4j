package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.Seed4JProjectFilePath;

public interface ContentReplacer {
  Seed4JProjectFilePath file();

  String apply(String content);

  void handleError(Throwable e);
}
