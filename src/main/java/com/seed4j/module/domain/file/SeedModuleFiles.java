package com.seed4j.module.domain.file;

import com.seed4j.module.domain.JHipsterModule.SeedModuleBuilder;
import com.seed4j.module.domain.JHipsterModuleUpgrade;
import com.seed4j.module.domain.SeedProjectFilePath;
import com.seed4j.shared.collection.domain.SeedCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.*;

public final class SeedModuleFiles {

  private final Collection<SeedModuleFile> filesToAdd;
  private final SeedFilesToDelete filesToDelete;
  private final SeedFilesToMove filesToMove;

  private SeedModuleFiles(JHipsterModuleFilesBuilder builder) {
    filesToAdd = SeedCollections.immutable(builder.filesToAdd);
    filesToMove = new SeedFilesToMove(builder.filesToMove);
    filesToDelete = new SeedFilesToDelete(builder.filesToDelete);
  }

  private SeedModuleFiles(SeedModuleFiles source, JHipsterModuleUpgrade upgrade) {
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

  public static JHipsterModuleFilesBuilder builder(SeedModuleBuilder module) {
    return new JHipsterModuleFilesBuilder(module);
  }

  public SeedModuleFiles forUpgrade(JHipsterModuleUpgrade upgrade) {
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

  public static final class JHipsterModuleFilesBuilder {

    private final SeedModuleBuilder module;
    private final Collection<SeedModuleFile> filesToAdd = new ArrayList<>();
    private final Collection<SeedFileToMove> filesToMove = new ArrayList<>();
    private final Collection<SeedProjectFilePath> filesToDelete = new ArrayList<>();

    private JHipsterModuleFilesBuilder(SeedModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleFilesBuilder add(SeedSource source, SeedDestination destination) {
      filesToAdd.add(new SeedModuleFile(new SeedFileContent(source), destination, false));

      return this;
    }

    public JHipsterModuleFilesBuilder addExecutable(SeedSource source, SeedDestination destination) {
      filesToAdd.add(new SeedModuleFile(new SeedFileContent(source), destination, true));

      return this;
    }

    public JHipsterModuleFileBatchBuilder batch(SeedSource source, SeedDestination destination) {
      return new JHipsterModuleFileBatchBuilder(source, destination, this);
    }

    public JHipsterModuleFilesBuilder move(SeedProjectFilePath source, SeedDestination destination) {
      filesToMove.add(new SeedFileToMove(source, destination));

      return this;
    }

    public JHipsterModuleFilesBuilder delete(SeedProjectFilePath path) {
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

  public static final class JHipsterModuleFileBatchBuilder {

    private final SeedSource source;
    private final SeedDestination destination;
    private final JHipsterModuleFilesBuilder files;

    private JHipsterModuleFileBatchBuilder(SeedSource source, SeedDestination destination, JHipsterModuleFilesBuilder files) {
      this.source = source;
      this.destination = destination;
      this.files = files;
    }

    public JHipsterModuleFileBatchBuilder addTemplate(String file) {
      return add(source.template(file), destination.append(file));
    }

    public JHipsterModuleFileBatchBuilder addTemplates(Collection<String> files) {
      Assert.notNull("files", files);
      files.forEach(this::addTemplate);

      return this;
    }

    public JHipsterModuleFileBatchBuilder addFile(String file) {
      return add(source.file(file), destination.append(file));
    }

    public JHipsterModuleFileBatchBuilder addExecutable(String file) {
      files.addExecutable(source.file(file), destination.append(file));

      return this;
    }

    public JHipsterModuleFileBatchBuilder addExecutableTemplate(String file) {
      files.addExecutable(source.template(file), destination.append(file));

      return this;
    }

    private JHipsterModuleFileBatchBuilder add(SeedSource source, SeedDestination destination) {
      files.add(source, destination);

      return this;
    }

    public JHipsterModuleFilesBuilder and() {
      return files;
    }
  }
}
