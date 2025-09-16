package com.seed4j.module.domain.file;

import static java.nio.charset.StandardCharsets.*;

import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.module.domain.Seed4JModuleContext;
import com.seed4j.shared.error.domain.Assert;

public class Seed4JFileContent {

  private final Seed4JSource source;

  public Seed4JFileContent(Seed4JSource source) {
    Assert.notNull("source", source);

    this.source = source;
  }

  public byte[] read(ProjectFiles files, Seed4JModuleContext context, TemplateRenderer templateRenderer) {
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
