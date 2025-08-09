package com.seed4j.module.domain.file;

import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.module.domain.SeedModuleContext;
import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import java.nio.file.Path;

public final class SeedTemplatedFile {

  private final SeedModuleFile file;
  private final SeedModuleContext context;

  private SeedTemplatedFile(TemplatedFileBuilder builder) {
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

    private SeedModuleFile file;
    private SeedModuleContext context;

    public TemplatedFileBuilder file(SeedModuleFile file) {
      this.file = file;

      return this;
    }

    public TemplatedFileBuilder context(SeedModuleContext context) {
      this.context = context;

      return this;
    }

    public SeedTemplatedFile build() {
      return new SeedTemplatedFile(this);
    }
  }
}
