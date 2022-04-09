package tech.jhipster.lite.generator.typescript.domain;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

public class TypescriptDomainService implements TypescriptService {

  public static final String SOURCE = "typescript";

  private final ProjectRepository projectRepository;
  private final NpmService npmService;

  public TypescriptDomainService(ProjectRepository projectRepository, NpmService npmService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
  }

  @Override
  public void init(Project project) {
    Typescript
      .devDependencies()
      .forEach(dependency ->
        npmService
          .getVersionInCommon(dependency)
          .ifPresentOrElse(
            version -> npmService.addDevDependency(project, dependency, version),
            () -> {
              throw new GeneratorException("Dependency not found: " + dependency);
            }
          )
      );
    Typescript.scripts().forEach((name, cmd) -> npmService.addScript(project, name, cmd));
    Typescript.files().forEach(file -> projectRepository.add(project, SOURCE, file));
  }
}
