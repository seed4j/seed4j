package com.seed4j.module.domain.file;

import com.seed4j.module.domain.Seed4JModule.Seed4JModuleBuilder;
import com.seed4j.module.domain.Seed4JModuleUpgrade;
import com.seed4j.module.domain.Seed4JProjectFilePath;
import com.seed4j.shared.collection.domain.Seed4JCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.*;

public final class Seed4JModuleFiles {

  private final Collection<Seed4JModuleFile> filesToAdd;
  private final Seed4JFilesToDelete filesToDelete;
  private final Seed4JFilesToMove filesToMove;

  private Seed4JModuleFiles(Seed4JModuleFilesBuilder builder) {
    filesToAdd = Seed4JCollections.immutable(builder.filesToAdd);
    filesToMove = new Seed4JFilesToMove(builder.filesToMove);
    filesToDelete = new Seed4JFilesToDelete(builder.filesToDelete);
  }

  private Seed4JModuleFiles(Seed4JModuleFiles source, Seed4JModuleUpgrade upgrade) {
    Assert.notNull("ignoredFiles", upgrade);

    filesToAdd = buildFilesToAdd(source, upgrade.skippedFiles());
    filesToDelete = source.filesToDelete.add(upgrade.filesToDelete());
    filesToMove = source.filesToMove;
  }

  private List<Seed4JModuleFile> buildFilesToAdd(Seed4JModuleFiles source, Seed4JDestinations skippedFiles) {
    return source.filesToAdd
      .stream()
      .filter(file -> skippedFiles.doesNotContain(file.destination()))
      .toList();
  }

  public static Seed4JModuleFilesBuilder builder(Seed4JModuleBuilder module) {
    return new Seed4JModuleFilesBuilder(module);
  }

  public Seed4JModuleFiles forUpgrade(Seed4JModuleUpgrade upgrade) {
    return new Seed4JModuleFiles(this, upgrade);
  }

  public Collection<Seed4JModuleFile> filesToAdd() {
    return filesToAdd;
  }

  public Seed4JFilesToMove filesToMove() {
    return filesToMove;
  }

  public Seed4JFilesToDelete filesToDelete() {
    return filesToDelete;
  }

  public static final class Seed4JModuleFilesBuilder {

    private final Seed4JModuleBuilder module;
    private final Collection<Seed4JModuleFile> filesToAdd = new ArrayList<>();
    private final Collection<Seed4JFileToMove> filesToMove = new ArrayList<>();
    private final Collection<Seed4JProjectFilePath> filesToDelete = new ArrayList<>();

    private Seed4JModuleFilesBuilder(Seed4JModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public Seed4JModuleFilesBuilder add(Seed4JSource source, Seed4JDestination destination) {
      filesToAdd.add(new Seed4JModuleFile(new Seed4JFileContent(source), destination, false));

      return this;
    }

    public Seed4JModuleFilesBuilder addExecutable(Seed4JSource source, Seed4JDestination destination) {
      filesToAdd.add(new Seed4JModuleFile(new Seed4JFileContent(source), destination, true));

      return this;
    }

    public Seed4JModuleFileBatchBuilder batch(Seed4JSource source, Seed4JDestination destination) {
      return new Seed4JModuleFileBatchBuilder(source, destination, this);
    }

    public Seed4JModuleFilesBuilder move(Seed4JProjectFilePath source, Seed4JDestination destination) {
      filesToMove.add(new Seed4JFileToMove(source, destination));

      return this;
    }

    public Seed4JModuleFilesBuilder delete(Seed4JProjectFilePath path) {
      Assert.notNull("path", path);

      filesToDelete.add(path);

      return this;
    }

    public Seed4JModuleBuilder and() {
      return module;
    }

    public Seed4JModuleFiles build() {
      return new Seed4JModuleFiles(this);
    }
  }

  public static final class Seed4JModuleFileBatchBuilder {

    private final Seed4JSource source;
    private final Seed4JDestination destination;
    private final Seed4JModuleFilesBuilder files;

    private Seed4JModuleFileBatchBuilder(Seed4JSource source, Seed4JDestination destination, Seed4JModuleFilesBuilder files) {
      this.source = source;
      this.destination = destination;
      this.files = files;
    }

    public Seed4JModuleFileBatchBuilder addTemplate(String file) {
      return add(source.template(file), destination.append(file));
    }

    public Seed4JModuleFileBatchBuilder addTemplates(Collection<String> files) {
      Assert.notNull("files", files);
      files.forEach(this::addTemplate);

      return this;
    }

    public Seed4JModuleFileBatchBuilder addFile(String file) {
      return add(source.file(file), destination.append(file));
    }

    public Seed4JModuleFileBatchBuilder addExecutable(String file) {
      files.addExecutable(source.file(file), destination.append(file));

      return this;
    }

    public Seed4JModuleFileBatchBuilder addExecutableTemplate(String file) {
      files.addExecutable(source.template(file), destination.append(file));

      return this;
    }

    private Seed4JModuleFileBatchBuilder add(Seed4JSource source, Seed4JDestination destination) {
      files.add(source, destination);

      return this;
    }

    public Seed4JModuleFilesBuilder and() {
      return files;
    }
  }
}
