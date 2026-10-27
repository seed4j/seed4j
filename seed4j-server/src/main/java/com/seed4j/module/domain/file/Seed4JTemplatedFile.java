package com.seed4j.module.domain.file;

import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.module.domain.Seed4JModuleContext;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import java.nio.file.Path;
import org.jspecify.annotations.Nullable;

public final class Seed4JTemplatedFile {

  private final Seed4JModuleFile file;
  private final Seed4JModuleContext context;

  private Seed4JTemplatedFile(TemplatedFileBuilder builder) {
    Assert.notNull("file", builder.file);
    Assert.notNull("context", builder.context);

    file = builder.file;
    context = builder.context;
  }

  public static TemplatedFileBuilder builder() {
    return new TemplatedFileBuilder();
  }

  public Path folder(Seed4JProjectFolder projectFolder) {
    Assert.notNull("projectFolder", projectFolder);

    return file.destination().folder(projectFolder);
  }

  public Path path(Seed4JProjectFolder projectFolder) {
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

    private @Nullable Seed4JModuleFile file;
    private @Nullable Seed4JModuleContext context;

    public TemplatedFileBuilder file(Seed4JModuleFile file) {
      this.file = file;

      return this;
    }

    public TemplatedFileBuilder context(Seed4JModuleContext context) {
      this.context = context;

      return this;
    }

    public Seed4JTemplatedFile build() {
      return new Seed4JTemplatedFile(this);
    }
  }
}
