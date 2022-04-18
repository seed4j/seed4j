package tech.jhipster.lite.generator.client.tools.playwright.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;

import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public record PlaywrightDomainService(ProjectRepository projectRepository, NpmService npmService) implements PlaywrightService {
  public static final String SOURCE = "client/common/playwright";

  @Override
  public void init(Project project) {
    addDevDependencies(project);
    addPlaywrightScripts(project);
    addPlaywrightFiles(project);
    addPlaywrightTestFiles(project);
    addPlaywrightPageObjectFiles(project);
  }

  public void addDevDependencies(Project project) {
    Playwright
      .devDependencies()
      .forEach(dependency ->
        npmService
          .getVersionInCommon(dependency)
          .ifPresentOrElse(
            version -> npmService.addDevDependency(project, dependency, version),
            () -> {
              throw new GeneratorException("DevDependency not found: " + dependency);
            }
          )
      );
  }

  public void addPlaywrightScripts(Project project) {
    Playwright.playwrightScripts().forEach((name, cmd) -> npmService.addScript(project, name, cmd));
  }

  public void addPlaywrightFiles(Project project) {
    project.addConfig("serverPort", 8080);
    projectRepository.template(project, FileUtils.getPath(SOURCE), "playwright.config.ts");
  }

  public void addPlaywrightTestFiles(Project project) {
    Playwright.playwrightTestFiles().forEach((file, path) -> projectRepository.template(project, getPath(SOURCE, path), file, path));
  }

  public void addPlaywrightPageObjectFiles(Project project) {
    Playwright.playwrightPageObjectFiles().forEach((file, path) -> projectRepository.template(project, getPath(SOURCE, path), file, path));
  }
}
