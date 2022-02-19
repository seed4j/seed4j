package tech.jhipster.lite.generator.project.infrastructure.secondary;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.common.domain.FileUtils.read;
import static tech.jhipster.lite.generator.project.domain.Constants.TEMPLATE_FOLDER;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@Repository
public class ProjectLocalRepository implements ProjectRepository {

  private final Logger log = LoggerFactory.getLogger(ProjectLocalRepository.class);

  @Override
  public void create(Project project) {
    try {
      FileUtils.createFolder(project.getFolder());
    } catch (IOException ex) {
      throw new GeneratorException("The folder can't be created");
    }
  }

  @Override
  public void add(Project project, String source, String sourceFilename) {
    add(project, source, sourceFilename, ".");
  }

  @Override
  public void add(Project project, String source, String sourceFilename, String destination) {
    add(project, source, sourceFilename, destination, sourceFilename);
  }

  @Override
  public void add(Project project, String source, String sourceFilename, String destination, String destinationFilename) {
    log.info("Adding file '{}'", destinationFilename);
    try {
      InputStream in = FileUtils.getInputStream(TEMPLATE_FOLDER, source, sourceFilename);

      String destinationFolder = FileUtils.getPath(project.getFolder(), destination);
      Path destinationPath = FileUtils.getPathOf(destinationFolder, destinationFilename);

      FileUtils.createFolder(destinationFolder);
      Files.copy(in, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException ex) {
      throw new GeneratorException("The file '" + destinationFilename + "' can't be added");
    }
  }

  @Override
  public void template(Project project, String source, String sourceFilename) {
    template(project, source, sourceFilename, ".");
  }

  @Override
  public void template(Project project, String source, String sourceFilename, String destination) {
    template(project, source, sourceFilename, destination, MustacheUtils.withoutExt(sourceFilename));
  }

  @Override
  public void template(Project project, String source, String sourceFilename, String destination, String destinationFilename) {
    try {
      log.info("Adding file '{}'", destinationFilename);
      String filename = MustacheUtils.withExt(sourceFilename);
      String result = MustacheUtils.template(FileUtils.getPath(TEMPLATE_FOLDER, source, filename), project.getConfig());

      write(project, result, destination, destinationFilename);
    } catch (IOException ex) {
      throw new GeneratorException("The file '" + destinationFilename + "' can't be added");
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

  private String getErrorWritingMessage(String filename) {
    return "Error when writing text to '" + filename + "'";
  }
}
