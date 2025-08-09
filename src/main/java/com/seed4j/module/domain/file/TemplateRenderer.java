package com.seed4j.module.domain.file;

import com.seed4j.module.domain.SeedModuleContext;
import com.seed4j.shared.error.domain.Assert;
import java.util.Map;

public interface TemplateRenderer {
  TemplateRenderer NOOP = (templateContent, context) -> templateContent;

  String render(String templateContent, Map<String, ?> context);

  default String render(String templateContent, SeedModuleContext context) {
    Assert.notNull("context", context);

    return render(templateContent, context.get());
  }
}
