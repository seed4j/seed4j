package tech.jhipster.lite.generator.client.react.core.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class ReactDomainService implements ReactService {

  public static final String SOURCE = "client/react";

  private final ProjectRepository projectRepository;
  private final NpmService npmService;

  public ReactDomainService(ProjectRepository projectRepository, NpmService npmService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
  }

  @Override
  public void init(Project project) {
    addDependencies(project);
    addDevDependencies(project);
    addScripts(project);
    addReactFiles(project);
  }

  public void addDependencies(Project project) {
    React
      .dependencies()
      .forEach(dependency ->
        npmService
          .getVersionInReact(dependency)
          .ifPresentOrElse(
            version -> npmService.addDependency(project, dependency, version),
            () -> {
              throw new GeneratorException("Dependency not found: " + dependency);
            }
          )
      );
  }

  public void addDevDependencies(Project project) {
    React
      .devDependencies()
      .forEach(devDependency ->
        npmService
          .getVersionInReact(devDependency)
          .ifPresentOrElse(
            version -> npmService.addDevDependency(project, devDependency, version),
            () -> {
              throw new GeneratorException("DevDependency not found: " + devDependency);
            }
          )
      );
  }

  private void addScripts(Project project) {
    React.scripts().forEach((name, cmd) -> npmService.addScript(project, name, cmd));
  }

  private void addReactFiles(Project project) {
    React.files().forEach(file -> projectRepository.add(project, SOURCE, file));
    React.reactFiles().forEach((file, path) -> projectRepository.template(project, getPath(SOURCE, path), file, path));
  }
}
