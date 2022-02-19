package tech.jhipster.lite.generator.server.springboot.springcloud.common.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.COMMENT_PROPERTIES_PREFIX;
import static tech.jhipster.lite.generator.project.domain.Constants.KEY_VALUE_PROPERTIES_SEPARATOR;

import java.io.IOException;
import java.util.List;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class SpringCloudCommonDomainService implements SpringCloudCommonService {

  private final ProjectRepository projectRepository;

  public SpringCloudCommonDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addOrMergeBootstrapProperties(Project project, String sourceFolderPath, String sourceFileName, String destinationFileFolder) {
    String destinationFilePath = getPath(project.getFolder(), destinationFileFolder, sourceFileName);
    if (!FileUtils.exists(destinationFilePath)) {
      createFile(project, sourceFolderPath, sourceFileName, destinationFileFolder);
    } else {
      mergeContentFile(project, sourceFolderPath, sourceFileName, destinationFilePath);
    }
  }

  private void createFile(Project project, String sourceFolderPath, String sourceFileName, String destinationFileFolder) {
    projectRepository.template(project, sourceFolderPath, sourceFileName, destinationFileFolder);
  }

  private void mergeContentFile(Project project, String sourceFolderPath, String sourceFileName, String destinationFilePath) {
    List<String> sourceFileLines = projectRepository.getComputedTemplate(project, sourceFolderPath, sourceFileName).lines().toList();

    List<String> destinationFileLines;
    try {
      destinationFileLines = FileUtils.read(destinationFilePath).lines().toList();
    } catch (IOException e) {
      throw new GeneratorException("Cannot get content file: " + destinationFilePath, e);
    }

    List<String> destinationFilePropertyKeys = getPropertyKeys(destinationFileLines);

    List<String> linesToAdd = sourceFileLines
      .stream()
      .filter(line -> !line.trim().startsWith(COMMENT_PROPERTIES_PREFIX))
      .filter(line -> {
        String propertyKey = getPropertyKey(line);
        return !destinationFilePropertyKeys.contains(propertyKey);
      })
      .toList();
    if (!linesToAdd.isEmpty()) {
      try {
        FileUtils.appendLines(destinationFilePath, linesToAdd);
      } catch (IOException e) {
        throw new GeneratorException("Cannot get append lines in file: " + destinationFilePath, e);
      }
    }
  }

  private List<String> getPropertyKeys(List<String> fileContentLines) {
    return fileContentLines
      .stream()
      .filter(line -> !line.startsWith(COMMENT_PROPERTIES_PREFIX))
      .filter(line -> !line.trim().isEmpty())
      .map(this::getPropertyKey)
      .toList();
  }

  private String getPropertyKey(String propertyLine) {
    return propertyLine.split(KEY_VALUE_PROPERTIES_SEPARATOR, 2)[0];
  }
}
