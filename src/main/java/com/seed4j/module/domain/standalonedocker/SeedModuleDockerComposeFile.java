package com.seed4j.module.domain.standalonedocker;

import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.startupcommand.DockerComposeFile;
import com.seed4j.shared.error.domain.Assert;
import java.util.ArrayList;
import java.util.Collection;

public final class SeedModuleDockerComposeFile {

  private final DockerComposeFiles dockerComposeFiles;

  private SeedModuleDockerComposeFile(SeedModuleDockerComposeFileBuilder builder) {
    dockerComposeFiles = new DockerComposeFiles(builder.dockerComposeFiles);
  }

  public DockerComposeFiles dockerComposeFiles() {
    return dockerComposeFiles;
  }

  @Override
  public String toString() {
    return dockerComposeFiles.toString();
  }

  public static SeedModuleDockerComposeFileBuilder builder(SeedModule.SeedModuleBuilder parentModuleBuilder) {
    return new SeedModuleDockerComposeFileBuilder(parentModuleBuilder);
  }

  public static final class SeedModuleDockerComposeFileBuilder {

    private final Collection<DockerComposeFile> dockerComposeFiles = new ArrayList<>();
    private final SeedModule.SeedModuleBuilder module;

    private SeedModuleDockerComposeFileBuilder(SeedModule.SeedModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public SeedModuleDockerComposeFileBuilder append(DockerComposeFile dockerComposeFile) {
      Assert.notNull("dockerComposeFile", dockerComposeFile);

      dockerComposeFiles.add(dockerComposeFile);

      return this;
    }

    public SeedModule.SeedModuleBuilder and() {
      return module;
    }

    public SeedModuleDockerComposeFile build() {
      return new SeedModuleDockerComposeFile(this);
    }
  }
}
