package com.seed4j.module.domain.standalonedocker;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.startupcommand.DockerComposeFile;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;

public final class JHipsterModuleDockerComposeFile {

  private final DockerComposeFiles dockerComposeFiles;

  private JHipsterModuleDockerComposeFile(JHipsterModuleDockerComposeFileBuilder builder) {
    dockerComposeFiles = new DockerComposeFiles(builder.dockerComposeFiles);
  }

  public DockerComposeFiles dockerComposeFiles() {
    return dockerComposeFiles;
  }

  @Override
  public String toString() {
    return dockerComposeFiles.toString();
  }

  public static JHipsterModuleDockerComposeFileBuilder builder(JHipsterModule.SeedModuleBuilder parentModuleBuilder) {
    return new JHipsterModuleDockerComposeFileBuilder(parentModuleBuilder);
  }

  public static final class JHipsterModuleDockerComposeFileBuilder {

    private final Collection<DockerComposeFile> dockerComposeFiles = new ArrayList<>();
    private final JHipsterModule.SeedModuleBuilder module;

    private JHipsterModuleDockerComposeFileBuilder(JHipsterModule.SeedModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleDockerComposeFileBuilder append(DockerComposeFile dockerComposeFile) {
      Assert.notNull("dockerComposeFile", dockerComposeFile);

      dockerComposeFiles.add(dockerComposeFile);

      return this;
    }

    public JHipsterModule.SeedModuleBuilder and() {
      return module;
    }

    public JHipsterModuleDockerComposeFile build() {
      return new JHipsterModuleDockerComposeFile(this);
    }
  }
}
