package tech.jhipster.lite.generator.module.domain;

import java.nio.file.Paths;
import java.util.Collection;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.JHipsterModuleContext.JHipsterModuleContextBuilder;
import tech.jhipster.lite.generator.module.domain.JHipsterModuleFiles.JHipsterModuleFilesBuilder;

public class JHipsterModule {

  private final JHipsterProjectFolder projectFolder;
  private final Collection<JHipsterModuleFile> files;
  private final JHipsterModuleContext context;

  private JHipsterModule(JHipsterModuleBuilder builder) {
    projectFolder = builder.projectFolder;

    files = builder.files.build().get();
    context = builder.context.build();
  }

  public static JHipsterModuleBuilder moduleForProject(JHipsterProjectFolder project) {
    return new JHipsterModuleBuilder(project);
  }

  public static JHipsterSource from(String source) {
    Assert.notBlank("source", source);

    return new JHipsterSource(Paths.get("/generator", source));
  }

  public static JHipsterDestination to(String destination) {
    return new JHipsterDestination(destination);
  }

  public static JHipsterDestination toSrcMainJava() {
    return JHipsterDestination.SRC_MAIN_JAVA;
  }

  public static JHipsterDestination toSrcTestJava() {
    return JHipsterDestination.SRC_TEST_JAVA;
  }

  public Collection<JHipsterModuleFile> files() {
    return files;
  }

  public JHipsterProjectFolder projectFolder() {
    return projectFolder;
  }

  public JHipsterModuleContext context() {
    return context;
  }

  public static class JHipsterModuleBuilder {

    private final JHipsterProjectFolder projectFolder;
    private final JHipsterModuleFilesBuilder files = JHipsterModuleFiles.builder(this);
    private final JHipsterModuleContextBuilder context = JHipsterModuleContext.builder(this);

    private JHipsterModuleBuilder(JHipsterProjectFolder projectFolder) {
      Assert.notNull("projectFolder", projectFolder);

      this.projectFolder = projectFolder;
    }

    public JHipsterModuleFilesBuilder files() {
      return files;
    }

    public JHipsterModuleContextBuilder context() {
      return context;
    }

    public JHipsterModule build() {
      return new JHipsterModule(this);
    }
  }
}
