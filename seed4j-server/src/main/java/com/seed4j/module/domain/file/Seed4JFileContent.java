package com.seed4j.module.domain.file;

import static java.nio.charset.StandardCharsets.*;

import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.module.domain.Seed4JModuleContext;
import com.seed4j.shared.error.domain.Assert;
import java.util.HashMap;
import java.util.Map;

public class Seed4JFileContent {

  private final Seed4JSource source;

  private final Map<String, Object> fileContext;

  public Seed4JFileContent(Seed4JSource source) {
    this(source, Map.of());
  }

  public Seed4JFileContent(Seed4JSource source, Map<String, Object> fileContext) {
    Assert.notNull("source", source);
    Assert.notNull("fileContext", fileContext);

    this.source = source;
    this.fileContext = Map.copyOf(fileContext);
  }

  public byte[] read(ProjectFiles files, Seed4JModuleContext context, TemplateRenderer templateRenderer) {
    Assert.notNull("files", files);
    Assert.notNull("context", context);
    Assert.notNull("templateRenderer", templateRenderer);

    if (source.isNotTemplate()) {
      return files.readBytes(source.get().toString());
    }

    String rawContent = files.readString(source.get().toString());
    Map<String, Object> renderContext = new HashMap<>(context.get());
    renderContext.putAll(fileContext);

    return templateRenderer.render(rawContent, renderContext).getBytes(UTF_8);
  }

  @Override
  public String toString() {
    return source.toString();
  }
}
