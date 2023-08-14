package tech.jhipster.lite.module.infrastructure.secondary;

import static java.nio.file.attribute.PosixFilePermission.*;

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
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;
import tech.jhipster.lite.module.domain.ProjectFiles;
import tech.jhipster.lite.module.domain.file.JHipsterFileToMove;
import tech.jhipster.lite.module.domain.file.JHipsterFilesToDelete;
import tech.jhipster.lite.module.domain.file.JHipsterFilesToMove;
import tech.jhipster.lite.module.domain.file.JHipsterTemplatedFile;
import tech.jhipster.lite.module.domain.file.JHipsterTemplatedFiles;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.shared.error.domain.GeneratorException;
import tech.jhipster.lite.shared.generation.domain.ExcludeFromGeneratedCodeCoverage;

@Repository
class FileSystemJHipsterModuleFiles {

  private static final Logger log = LoggerFactory.getLogger(FileSystemJHipsterModuleFiles.class);
  private static final Set<PosixFilePermission> EXECUTABLE_FILE_PERMISSIONS = buildExecutableFilePermission();

  private final ProjectFiles files;

  public FileSystemJHipsterModuleFiles(ProjectFiles files) {
    this.files = files;
  }

  private static Set<PosixFilePermission> buildExecutableFilePermission() {
    return Set.of(OWNER_READ, OWNER_WRITE, OWNER_EXECUTE, GROUP_READ, GROUP_WRITE, GROUP_EXECUTE);
  }

  void create(JHipsterProjectFolder projectFolder, JHipsterTemplatedFiles files) {
    files.get().forEach(writeFile(projectFolder));
  }

  private Consumer<JHipsterTemplatedFile> writeFile(JHipsterProjectFolder projectFolder) {
    return file -> {
      Path filePath = file.path(projectFolder);

      try {
        Files.createDirectories(file.folder(projectFolder));
        Files.write(filePath, file.content(files));

        setExecutable(file, filePath);

        log.debug("{} added", filePath);
      } catch (IOException e) {
        throw GeneratorException.technicalError("Can't write file to " + filePath.toString() + ": " + e.getMessage(), e);
      }
    };
  }

  @ExcludeFromGeneratedCodeCoverage(reason = "Ensuring posix FS will be a nightmare :)")
  private void setExecutable(JHipsterTemplatedFile file, Path filePath) throws IOException {
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

  void move(JHipsterProjectFolder folder, JHipsterFilesToMove filesToMove) {
    filesToMove.stream().forEach(moveFile(folder));
  }

  private Consumer<JHipsterFileToMove> moveFile(JHipsterProjectFolder folder) {
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

  void delete(JHipsterProjectFolder folder, JHipsterFilesToDelete filesToDelete) {
    filesToDelete.stream().forEach(deleteFile(folder));
  }

  private Consumer<JHipsterProjectFilePath> deleteFile(JHipsterProjectFolder folder) {
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
