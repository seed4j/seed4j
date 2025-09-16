package com.seed4j.module.domain.standalonedocker;

import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.startupcommand.DockerComposeFile;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;

public final class Seed4JModuleDockerComposeFile {

  private final DockerComposeFiles dockerComposeFiles;

  private Seed4JModuleDockerComposeFile(Seed4JModuleDockerComposeFileBuilder builder) {
    dockerComposeFiles = new DockerComposeFiles(builder.dockerComposeFiles);
  }

  public DockerComposeFiles dockerComposeFiles() {
    return dockerComposeFiles;
  }

  @Override
  public String toString() {
    return dockerComposeFiles.toString();
  }

  public static Seed4JModuleDockerComposeFileBuilder builder(Seed4JModule.Seed4JModuleBuilder parentModuleBuilder) {
    return new Seed4JModuleDockerComposeFileBuilder(parentModuleBuilder);
  }

  public static final class Seed4JModuleDockerComposeFileBuilder {

    private final Collection<DockerComposeFile> dockerComposeFiles = new ArrayList<>();
    private final Seed4JModule.Seed4JModuleBuilder module;

    private Seed4JModuleDockerComposeFileBuilder(Seed4JModule.Seed4JModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public Seed4JModuleDockerComposeFileBuilder append(DockerComposeFile dockerComposeFile) {
      Assert.notNull("dockerComposeFile", dockerComposeFile);

      dockerComposeFiles.add(dockerComposeFile);

      return this;
    }

    public Seed4JModule.Seed4JModuleBuilder and() {
      return module;
    }

    public Seed4JModuleDockerComposeFile build() {
      return new Seed4JModuleDockerComposeFile(this);
    }
  }
}
