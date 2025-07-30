package com.seed4j.module.domain.file;

import static java.nio.charset.StandardCharsets.*;

import com.seed4j.module.domain.JHipsterModuleContext;
import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.shared.error.domain.Assert;

public class JHipsterFileContent {

  private final JHipsterSource source;

  public JHipsterFileContent(JHipsterSource source) {
    Assert.notNull("source", source);

    this.source = source;
  }

  public byte[] read(ProjectFiles files, JHipsterModuleContext context, TemplateRenderer templateRenderer) {
    Assert.notNull("files", files);
    Assert.notNull("context", context);
    Assert.notNull("templateRenderer", templateRenderer);

    if (source.isNotTemplate()) {
      return files.readBytes(source.get().toString());
    }

    String rawContent = files.readString(source.get().toString());
    return templateRenderer.render(rawContent, context).getBytes(UTF_8);
  }

  @Override
  public String toString() {
    return source.toString();
  }
}
