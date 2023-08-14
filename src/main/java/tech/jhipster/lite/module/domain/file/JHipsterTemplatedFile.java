package tech.jhipster.lite.module.domain.file;

import java.nio.file.Path;
import tech.jhipster.lite.module.domain.JHipsterModuleContext;
import tech.jhipster.lite.module.domain.ProjectFiles;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.shared.error.domain.Assert;

public class JHipsterTemplatedFile {

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

  public byte[] content(ProjectFiles files) {
    return file.content().read(files, context);
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
