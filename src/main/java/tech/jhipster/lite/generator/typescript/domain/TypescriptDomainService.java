package tech.jhipster.lite.generator.typescript.domain;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public record TypescriptDomainService(ProjectRepository projectRepository, NpmService npmService) implements TypescriptService {
  public static final String SOURCE = "typescript";

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
