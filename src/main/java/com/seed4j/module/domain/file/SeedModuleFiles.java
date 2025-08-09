package com.seed4j.module.domain.file;

import com.seed4j.module.domain.SeedModule.SeedModuleBuilder;
import com.seed4j.module.domain.SeedModuleUpgrade;
import com.seed4j.module.domain.SeedProjectFilePath;
import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.*;

public final class SeedModuleFiles {

  private final Collection<SeedModuleFile> filesToAdd;
  private final SeedFilesToDelete filesToDelete;
  private final SeedFilesToMove filesToMove;

  private SeedModuleFiles(SeedModuleFilesBuilder builder) {
    filesToAdd = SeedCollections.immutable(builder.filesToAdd);
    filesToMove = new SeedFilesToMove(builder.filesToMove);
    filesToDelete = new SeedFilesToDelete(builder.filesToDelete);
  }

  private SeedModuleFiles(SeedModuleFiles source, SeedModuleUpgrade upgrade) {
    Assert.notNull("ignoredFiles", upgrade);

    filesToAdd = buildFilesToAdd(source, upgrade.skippedFiles());
    filesToDelete = source.filesToDelete.add(upgrade.filesToDelete());
    filesToMove = source.filesToMove;
  }

  private List<SeedModuleFile> buildFilesToAdd(SeedModuleFiles source, SeedDestinations skippedFiles) {
    return source.filesToAdd
      .stream()
      .filter(file -> skippedFiles.doesNotContain(file.destination()))
      .toList();
  }

  public static SeedModuleFilesBuilder builder(SeedModuleBuilder module) {
    return new SeedModuleFilesBuilder(module);
  }

  public SeedModuleFiles forUpgrade(SeedModuleUpgrade upgrade) {
    return new SeedModuleFiles(this, upgrade);
  }

  public Collection<SeedModuleFile> filesToAdd() {
    return filesToAdd;
  }

  public SeedFilesToMove filesToMove() {
    return filesToMove;
  }

  public SeedFilesToDelete filesToDelete() {
    return filesToDelete;
  }

  public static final class SeedModuleFilesBuilder {

    private final SeedModuleBuilder module;
    private final Collection<SeedModuleFile> filesToAdd = new ArrayList<>();
    private final Collection<SeedFileToMove> filesToMove = new ArrayList<>();
    private final Collection<SeedProjectFilePath> filesToDelete = new ArrayList<>();

    private SeedModuleFilesBuilder(SeedModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public SeedModuleFilesBuilder add(SeedSource source, SeedDestination destination) {
      filesToAdd.add(new SeedModuleFile(new SeedFileContent(source), destination, false));

      return this;
    }

    public SeedModuleFilesBuilder addExecutable(SeedSource source, SeedDestination destination) {
      filesToAdd.add(new SeedModuleFile(new SeedFileContent(source), destination, true));

      return this;
    }

    public SeedModuleFileBatchBuilder batch(SeedSource source, SeedDestination destination) {
      return new SeedModuleFileBatchBuilder(source, destination, this);
    }

    public SeedModuleFilesBuilder move(SeedProjectFilePath source, SeedDestination destination) {
      filesToMove.add(new SeedFileToMove(source, destination));

      return this;
    }

    public SeedModuleFilesBuilder delete(SeedProjectFilePath path) {
      Assert.notNull("path", path);

      filesToDelete.add(path);

      return this;
    }

    public SeedModuleBuilder and() {
      return module;
    }

    public SeedModuleFiles build() {
      return new SeedModuleFiles(this);
    }
  }

  public static final class SeedModuleFileBatchBuilder {

    private final SeedSource source;
    private final SeedDestination destination;
    private final SeedModuleFilesBuilder files;

    private SeedModuleFileBatchBuilder(SeedSource source, SeedDestination destination, SeedModuleFilesBuilder files) {
      this.source = source;
      this.destination = destination;
      this.files = files;
    }

    public SeedModuleFileBatchBuilder addTemplate(String file) {
      return add(source.template(file), destination.append(file));
    }

    public SeedModuleFileBatchBuilder addTemplates(Collection<String> files) {
      Assert.notNull("files", files);
      files.forEach(this::addTemplate);

      return this;
    }

    public SeedModuleFileBatchBuilder addFile(String file) {
      return add(source.file(file), destination.append(file));
    }

    public SeedModuleFileBatchBuilder addExecutable(String file) {
      files.addExecutable(source.file(file), destination.append(file));

      return this;
    }

    public SeedModuleFileBatchBuilder addExecutableTemplate(String file) {
      files.addExecutable(source.template(file), destination.append(file));

      return this;
    }

    private SeedModuleFileBatchBuilder add(SeedSource source, SeedDestination destination) {
      files.add(source, destination);

      return this;
    }

    public SeedModuleFilesBuilder and() {
      return files;
    }
  }
}
