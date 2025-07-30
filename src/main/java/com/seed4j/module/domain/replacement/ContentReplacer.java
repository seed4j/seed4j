package com.seed4j.module.domain.replacement;

import com.seed4j.module.domain.JHipsterProjectFilePath;

public interface ContentReplacer {
  JHipsterProjectFilePath file();

  String apply(String content);

  void handleError(Throwable e);
}
