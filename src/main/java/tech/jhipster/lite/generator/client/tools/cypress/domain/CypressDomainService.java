package tech.jhipster.lite.generator.client.tools.cypress.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.client.tools.cypress.domain.Cypress.JAVASCRIPT_INTEGRATION;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.client.common.domain.ClientCommonService;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

public class CypressDomainService implements CypressService {

  public static final String SOURCE = "client/common/cypress";

  private final ProjectRepository projectRepository;
  private final NpmService npmService;
  private final ClientCommonService clientCommonService;

  public CypressDomainService(ProjectRepository projectRepository, NpmService npmService, ClientCommonService clientCommonService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
    this.clientCommonService = clientCommonService;
  }

  @Override
  public void init(Project project) {
    addDevDependencies(project);
    addCypressScripts(project);
    addCypressFiles(project);
    addCypressTestFiles(project);
    excludeIntegrationFilesTsConfig(project);
  }

  public void addDevDependencies(Project project) {
    Cypress
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

  public void addCypressScripts(Project project) {
    Cypress.cypressScripts().forEach((name, cmd) -> npmService.addScript(project, name, cmd));
  }

  public void addCypressFiles(Project project) {
    project.addConfig("serverPort", 8080);
    projectRepository.template(project, getPath(SOURCE, JAVASCRIPT_INTEGRATION), "cypress-config.json", getPath(JAVASCRIPT_INTEGRATION));
    Cypress.cypressFiles().forEach((file, path) -> projectRepository.add(project, getPath(SOURCE, path), file, path));
  }

  public void addCypressTestFiles(Project project) {
    Cypress.cypressTestFiles().forEach((file, path) -> projectRepository.template(project, getPath(SOURCE, path), file, path));
  }

  private void excludeIntegrationFilesTsConfig(Project project) {
    Cypress.tsconfigPatternsToExclude().forEach(value -> clientCommonService.excludeInTsconfigJson(project, value));
  }
}
