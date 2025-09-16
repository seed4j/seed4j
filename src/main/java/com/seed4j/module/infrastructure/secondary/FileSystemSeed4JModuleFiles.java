package com.seed4j.module.infrastructure.secondary;

import static java.nio.file.attribute.PosixFilePermission.*;

import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.module.domain.Seed4JProjectFilePath;
import com.seed4j.module.domain.file.Seed4JFileToMove;
import com.seed4j.module.domain.file.Seed4JFilesToDelete;
import com.seed4j.module.domain.file.Seed4JFilesToMove;
import com.seed4j.module.domain.file.Seed4JTemplatedFile;
import com.seed4j.module.domain.file.Seed4JTemplatedFiles;
import com.seed4j.module.domain.file.TemplateRenderer;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.shared.error.domain.GeneratorException;
import com.seed4j.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class FileSystemSeed4JModuleFiles {

  private static final Logger log = LoggerFactory.getLogger(FileSystemSeed4JModuleFiles.class);
  private static final Set<PosixFilePermission> EXECUTABLE_FILE_PERMISSIONS = buildExecutableFilePermission();

  private final ProjectFiles files;
  private final TemplateRenderer templateRenderer;

  public FileSystemSeed4JModuleFiles(ProjectFiles files, TemplateRenderer templateRenderer) {
    this.files = files;
    this.templateRenderer = templateRenderer;
  }

  private static Set<PosixFilePermission> buildExecutableFilePermission() {
    return Set.of(OWNER_READ, OWNER_WRITE, OWNER_EXECUTE, GROUP_READ, GROUP_WRITE, GROUP_EXECUTE);
  }

  public void create(Seed4JProjectFolder projectFolder, Seed4JTemplatedFiles files) {
    files.get().forEach(writeFile(projectFolder));
  }

  private Consumer<Seed4JTemplatedFile> writeFile(Seed4JProjectFolder projectFolder) {
    return file -> {
      Path filePath = file.path(projectFolder);

      try {
        Files.createDirectories(file.folder(projectFolder));
        Files.write(filePath, file.content(files, templateRenderer));

        setExecutable(file, filePath);

        log.debug("Added: {}", filePath);
      } catch (IOException e) {
        throw GeneratorException.technicalError("Can't write file to " + filePath.toString() + ": " + e.getMessage(), e);
      }
    };
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Ensuring posix FS will be a nightmare :)")
  private void setExecutable(Seed4JTemplatedFile file, Path filePath) throws IOException {
    if (isNotPosix()) {
      return;
    }

    if (file.isNotExecutable()) {
      return;
    }

    Files.setPosixFilePermissions(filePath, EXECUTABLE_FILE_PERMISSIONS);
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Only tested on POSIX systems")
  private static boolean isNotPosix() {
    return !FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
  }

  void move(Seed4JProjectFolder folder, Seed4JFilesToMove filesToMove) {
    filesToMove.stream().forEach(moveFile(folder));
  }

  private Consumer<Seed4JFileToMove> moveFile(Seed4JProjectFolder folder) {
    return file -> {
      String filename = file.source().get();
      Path source = folder.filePath(filename);

      Path destination = file.destination().pathInProject(folder);

      if (Files.exists(destination)) {
        log.info("Not moving {} since {} already exists", source.toAbsolutePath(), destination.toAbsolutePath());

        return;
      }

      if (Files.notExists(source)) {
        throw new UnknownFileToMoveException(filename);
      }

      move(source, destination);
    };
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Move error case is hard to test and with low value")
  private void move(Path source, Path destination) {
    try {
      Files.move(source, destination);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error moving file: " + e.getMessage(), e);
    }
  }

  void delete(Seed4JProjectFolder folder, Seed4JFilesToDelete filesToDelete) {
    filesToDelete.stream().forEach(deleteFile(folder));
  }

  private Consumer<Seed4JProjectFilePath> deleteFile(Seed4JProjectFolder folder) {
    return file -> {
      Path path = folder.filePath(file.path());

      if (Files.notExists(path)) {
        throw new UnknownFileToDeleteException(file);
      }

      delete(path);
    };
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Deletion error case is hard to test and with low value")
  private void delete(Path path) {
    try {
      Files.delete(path);
    } catch (IOException e) {
      throw GeneratorException.technicalError("Error deleting file: " + e.getMessage(), e);
    }
  }
}
