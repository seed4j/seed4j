package com.seed4j.module.domain.file;

import com.seed4j.module.domain.JHipsterModule.JHipsterModuleBuilder;
import com.seed4j.module.domain.JHipsterModuleUpgrade;
import com.seed4j.module.domain.JHipsterProjectFilePath;
import com.seed4j.shared.collection.domain.JHipsterCollections;
import com.seed4j.shared.error.domain.Assert;
import java.util.*;

public final class JHipsterModuleFiles {

  private final Collection<JHipsterModuleFile> filesToAdd;
  private final JHipsterFilesToDelete filesToDelete;
  private final JHipsterFilesToMove filesToMove;

  private JHipsterModuleFiles(JHipsterModuleFilesBuilder builder) {
    filesToAdd = JHipsterCollections.immutable(builder.filesToAdd);
    filesToMove = new JHipsterFilesToMove(builder.filesToMove);
    filesToDelete = new JHipsterFilesToDelete(builder.filesToDelete);
  }

  private JHipsterModuleFiles(JHipsterModuleFiles source, JHipsterModuleUpgrade upgrade) {
    Assert.notNull("ignoredFiles", upgrade);

    filesToAdd = buildFilesToAdd(source, upgrade.skippedFiles());
    filesToDelete = source.filesToDelete.add(upgrade.filesToDelete());
    filesToMove = source.filesToMove;
  }

  private List<JHipsterModuleFile> buildFilesToAdd(JHipsterModuleFiles source, JHipsterDestinations skippedFiles) {
    return source.filesToAdd
      .stream()
      .filter(file -> skippedFiles.doesNotContain(file.destination()))
      .toList();
  }

  public static JHipsterModuleFilesBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleFilesBuilder(module);
  }

  public JHipsterModuleFiles forUpgrade(JHipsterModuleUpgrade upgrade) {
    return new JHipsterModuleFiles(this, upgrade);
  }

  public Collection<JHipsterModuleFile> filesToAdd() {
    return filesToAdd;
  }

  public JHipsterFilesToMove filesToMove() {
    return filesToMove;
  }

  public JHipsterFilesToDelete filesToDelete() {
    return filesToDelete;
  }

  public static final class JHipsterModuleFilesBuilder {

    private final JHipsterModuleBuilder module;
    private final Collection<JHipsterModuleFile> filesToAdd = new ArrayList<>();
    private final Collection<JHipsterFileToMove> filesToMove = new ArrayList<>();
    private final Collection<JHipsterProjectFilePath> filesToDelete = new ArrayList<>();

    private JHipsterModuleFilesBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleFilesBuilder add(JHipsterSource source, JHipsterDestination destination) {
      filesToAdd.add(new JHipsterModuleFile(new JHipsterFileContent(source), destination, false));

      return this;
    }

    public JHipsterModuleFilesBuilder addExecutable(JHipsterSource source, JHipsterDestination destination) {
      filesToAdd.add(new JHipsterModuleFile(new JHipsterFileContent(source), destination, true));

      return this;
    }

    public JHipsterModuleFileBatchBuilder batch(JHipsterSource source, JHipsterDestination destination) {
      return new JHipsterModuleFileBatchBuilder(source, destination, this);
    }

    public JHipsterModuleFilesBuilder move(JHipsterProjectFilePath source, JHipsterDestination destination) {
      filesToMove.add(new JHipsterFileToMove(source, destination));

      return this;
    }

    public JHipsterModuleFilesBuilder delete(JHipsterProjectFilePath path) {
      Assert.notNull("path", path);

      filesToDelete.add(path);

      return this;
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    public JHipsterModuleFiles build() {
      return new JHipsterModuleFiles(this);
    }
  }

  public static final class JHipsterModuleFileBatchBuilder {

    private final JHipsterSource source;
    private final JHipsterDestination destination;
    private final JHipsterModuleFilesBuilder files;

    private JHipsterModuleFileBatchBuilder(JHipsterSource source, JHipsterDestination destination, JHipsterModuleFilesBuilder files) {
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

    private JHipsterModuleFileBatchBuilder add(JHipsterSource source, JHipsterDestination destination) {
      files.add(source, destination);

      return this;
    }

    public JHipsterModuleFilesBuilder and() {
      return files;
    }
  }
}
