package com.seed4j.module.domain.file;

import com.seed4j.module.domain.JHipsterModuleContext;
import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import java.nio.file.Path;

public final class JHipsterTemplatedFile {

  private final JHipsterModuleFile file;
  private final JHipsterModuleContext context;

  private JHipsterTemplatedFile(TemplatedFileBuilder builder) {
    Assert.notNull("file", builder.file);
    Assert.notNull("context", builder.context);

    file = builder.file;
    context = builder.context;
  }

  public static TemplatedFileBuilder builder() {
    return new TemplatedFileBuilder();
  }

  public Path folder(JHipsterProjectFolder projectFolder) {
    Assert.notNull("projectFolder", projectFolder);

    return file.destination().folder(projectFolder);
  }

  public Path path(JHipsterProjectFolder projectFolder) {
    Assert.notNull("projectFolder", projectFolder);

    return file.destination().pathInProject(projectFolder);
  }

  public byte[] content(ProjectFiles files, TemplateRenderer templateRenderer) {
    return file.content().read(files, context, templateRenderer);
  }

  public boolean isNotExecutable() {
    return !file.executable();
  }

  public static class TemplatedFileBuilder {

    private JHipsterModuleFile file;
    private JHipsterModuleContext context;

    public TemplatedFileBuilder file(JHipsterModuleFile file) {
      this.file = file;

      return this;
    }

    public TemplatedFileBuilder context(JHipsterModuleContext context) {
      this.context = context;

      return this;
    }

    public JHipsterTemplatedFile build() {
      return new JHipsterTemplatedFile(this);
    }
  }
}
