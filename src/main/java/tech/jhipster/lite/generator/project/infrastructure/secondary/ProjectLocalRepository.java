package tech.jhipster.lite.generator.project.infrastructure.secondary;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Repository;
import org.zeroturnaround.zip.ZipException;
import org.zeroturnaround.zip.ZipUtil;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.FilePath;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@Repository
public class ProjectLocalRepository implements ProjectRepository {

  private static final String FILE_SEPARATOR = "/";

  @Override
  public void create(Project project) {
    try {
      FileUtils.createFolder(project.getFolder());
    } catch (IOException ex) {
      throw new GeneratorException("The folder can't be created");
    }
  }

  @Override
  public void add(Collection<ProjectFile> files) {
    Assert.field("files", files).notEmpty().noNullElement();

    files.forEach(file -> {
      try (InputStream in = getInputStream(file.source())) {
        String destinationFolder = FileUtils.getPath(file.project().getFolder(), file.destination().folder());
        Path destinationPath = FileUtils.getPathOf(destinationFolder, file.destination().file());

        FileUtils.createFolder(destinationFolder);
        Files.copy(in, destinationPath, StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException ex) {
        throw new GeneratorException("The file '" + file.destination().file() + "' can't be added");
      }
    });
  }

  private static InputStream getInputStream(FilePath path) {
    String resolvedPath = getPath(TEMPLATE_FOLDER, path.folder(), path.file());

    InputStream in = ProjectLocalRepository.class.getResourceAsStream(FILE_SEPARATOR + resolvedPath);

    if (in == null) {
      throw new GeneratorException("File '" + resolvedPath + "' not found in classpath");
    }

    return in;
  }

  @Override
  public void template(Collection<ProjectFile> files) {
    Assert.field("files", files).notEmpty().noNullElement();

    files.forEach(templatizeFile());
  }

  private Consumer<ProjectFile> templatizeFile() {
    return file -> {
      try {
        String result = templatizeContent(file);

        writeTemplatizedContent(file, result);
      } catch (IOException ex) {
        throw new GeneratorException("The file '" + file.destination().file() + "' can't be added");
      }
    };
  }

  private String templatizeContent(ProjectFile file) throws IOException {
    String sourcePath = FileUtils.getPath(TEMPLATE_FOLDER, file.source().folder(), MustacheUtils.withExt(file.source().file()));

    return MustacheUtils.template(sourcePath, file.project().getConfig());
  }

  private void writeTemplatizedContent(ProjectFile file, String result) {
    write(file.project(), result, file.destination().folder(), MustacheUtils.withoutExt(file.destination().file()));
  }

  @Override
  public void rename(Project project, String source, String sourceFilename, String destinationFilename) {
    try {
      FileUtils.rename(getPath(project.getFolder(), source), sourceFilename, destinationFilename);
    } catch (IOException e) {
      throw new GeneratorException("Error when renaming file: " + sourceFilename + " -> " + destinationFilename, e);
    }
  }

  @Override
  public String getComputedTemplate(Project project, String source, String sourceFilename) {
    String filename = MustacheUtils.withExt(sourceFilename);
    String filePath = getPath(TEMPLATE_FOLDER, source, filename);
    try {
      return MustacheUtils.template(filePath, project.getConfig());
    } catch (IOException e) {
      throw new GeneratorException("The file " + filePath + " can't be read");
    }
  }

  @Override
  public boolean containsRegexp(Project project, String source, String sourceFilename, String regexp) {
    try {
      String text = read(getPath(project.getFolder(), source, sourceFilename));
      return FileUtils.containsRegexp(text, regexp);
    } catch (IOException e) {
      throw new GeneratorException("Error when reading text from '" + sourceFilename + "'");
    }
  }

  @Override
  public void replaceText(Project project, String source, String sourceFilename, String oldText, String newText) {
    try {
      String currentText = read(getPath(project.getFolder(), source, sourceFilename));
      String updatedText = FileUtils.replace(currentText, oldText, newText);
      write(project, updatedText, source, sourceFilename);
    } catch (IOException e) {
      throw new GeneratorException(getErrorWritingMessage(sourceFilename));
    }
  }

  @Override
  public void replaceRegexp(Project project, String source, String sourceFilename, String regexpText, String newText) {
    try {
      String sourcePath = getPath(project.getFolder(), source, sourceFilename);
      String updatedText = FileUtils.replaceInFile(sourcePath, regexpText, newText);
      write(project, updatedText, source, sourceFilename);
    } catch (IOException e) {
      throw new GeneratorException(getErrorWritingMessage(sourceFilename));
    }
  }

  @Override
  public void write(Project project, String text, String destination, String destinationFilename) {
    Assert.notNull("text", text);

    String projectDestination = getPath(project.getFolder(), destination);
    String projectDestinationFilename = getPath(projectDestination, destinationFilename);

    try {
      FileUtils.createFolder(projectDestination);
      FileUtils.write(projectDestinationFilename, text, project.getEndOfLine());
    } catch (IOException e) {
      throw new GeneratorException(getErrorWritingMessage(projectDestinationFilename));
    }
  }

  @Override
  public void writeAtTop(Project project, String text, String destination, String destinationFilename) {
    Assert.notNull("text", text);

    try {
      String currentText = read(getPath(project.getFolder(), destination, destinationFilename));
      String updatedText = String.join("", text, currentText);
      write(project, updatedText, destination, destinationFilename);
    } catch (IOException e) {
      throw new GeneratorException(getErrorWritingMessage(destinationFilename));
    }
  }

  @Override
  public void setExecutable(Project project, String source, String sourceFilename) {
    if (!FileUtils.isPosix()) {
      return;
    }
    Set<PosixFilePermission> perms = new HashSet<>();
    perms.add(PosixFilePermission.OWNER_READ);
    perms.add(PosixFilePermission.OWNER_WRITE);
    perms.add(PosixFilePermission.OWNER_EXECUTE);

    perms.add(PosixFilePermission.GROUP_READ);
    perms.add(PosixFilePermission.GROUP_WRITE);
    perms.add(PosixFilePermission.GROUP_EXECUTE);
    File file = new File(getPath(project.getFolder(), source, sourceFilename));
    try {
      Files.setPosixFilePermissions(file.toPath(), perms);
    } catch (IOException e) {
      throw new GeneratorException("Can't change file permission for " + sourceFilename, e);
    }
  }

  @Override
  public void gitInit(Project project) {
    try {
      GitUtils.init(project.getFolder());
    } catch (GitAPIException e) {
      throw new GeneratorException("Error when git init", e);
    }
  }

  @Override
  public void gitAddAndCommit(Project project, String message) {
    try {
      GitUtils.addAndCommit(project.getFolder(), message);
    } catch (GitAPIException | IOException e) {
      throw new GeneratorException("Error when git add and commit", e);
    }
  }

  @Override
  public void gitApplyPatch(Project project, String patchFilename) {
    try {
      GitUtils.apply(project.getFolder(), patchFilename);
    } catch (GitAPIException | IOException e) {
      throw new GeneratorException("Error when git apply patch", e);
    }
  }

  @Override
  public String zip(Project project) {
    File workingDir = new File(project.getFolder());
    String filename = workingDir.getName() + ".zip";
    try {
      ZipUtil.pack(workingDir, new File(tmpDir() + FileSystems.getDefault().getSeparator() + filename));
      return filename;
    } catch (ZipException e) {
      throw new GeneratorException("Error when zipping " + project.getFolder(), e);
    }
  }

  @Override
  public byte[] download(Project project) {
    String filename = zip(project);
    try {
      return FileUtils.convertFileInTmpToByte(filename);
    } catch (IOException ioe) {
      throw new GeneratorException("Error when creating ", ioe);
    }
  }

  @Override
  public boolean isJHipsterLiteProject(String path) {
    return FileUtils.exists(getPath(path, JHIPSTER_FOLDER, HISTORY_JSON));
  }

  private String getErrorWritingMessage(String filename) {
    return "Error when writing text to '" + filename + "'";
  }
}
