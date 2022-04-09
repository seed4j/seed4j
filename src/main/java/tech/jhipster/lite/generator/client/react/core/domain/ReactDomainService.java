package tech.jhipster.lite.generator.client.react.core.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

public class ReactDomainService implements ReactService {

  public static final String SOURCE = "client/react";
  public static final String SOURCE_APP = "src/main/webapp/app/common/primary/app";

  private final ProjectRepository projectRepository;
  private final NpmService npmService;

  public ReactDomainService(ProjectRepository projectRepository, NpmService npmService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
  }

  @Override
  public void addReact(Project project) {
    project.addDefaultConfig(BASE_NAME);
    addCommonReact(project);
    addReactUnstyledFiles(project);
  }

  @Override
  public void addStyledReact(Project project) {
    project.addDefaultConfig(BASE_NAME);
    addCommonReact(project);
    addReactStyledFiles(project);
  }

  public void addCommonReact(Project project) {
    addDevDependencies(project);
    addDependencies(project);
    addScripts(project);
    addFiles(project);
    addJestSonar(project);
    addReactCommonFiles(project);
  }

  public void addDevDependencies(Project project) {
    React
      .devDependencies()
      .forEach(dependency ->
        npmService
          .getVersionInReact(dependency)
          .ifPresentOrElse(
            version -> npmService.addDevDependency(project, dependency, version),
            () -> {
              throw new GeneratorException("DevDependency not found: " + dependency);
            }
          )
      );
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

  public void addScripts(Project project) {
    React.scripts().forEach((name, cmd) -> npmService.addScript(project, name, cmd));
  }

  public void addFiles(Project project) {
    React.files().forEach(file -> projectRepository.add(project, SOURCE, file));
  }

  public void addReactCommonFiles(Project project) {
    React.reactCommonFiles().forEach((file, path) -> projectRepository.template(project, getPath(SOURCE, path), file, path));
  }

  public void addReactUnstyledFiles(Project project) {
    projectRepository.template(project, getPath(SOURCE, SOURCE_APP), "App.tsx", SOURCE_APP);
  }

  public void addReactStyledFiles(Project project) {
    String imagesPath = "src/main/webapp/content/images";
    projectRepository.template(project, getPath(SOURCE, SOURCE_APP), "StyledApp.tsx", SOURCE_APP, "App.tsx");
    projectRepository.template(project, getPath(SOURCE, SOURCE_APP), "App.css", SOURCE_APP);
    projectRepository.add(project, getPath(SOURCE, imagesPath), "ReactLogo.png", imagesPath);
    projectRepository.add(project, getPath(SOURCE, imagesPath), "JHipster-Lite-neon-blue.png", imagesPath);
  }

  public void addJestSonar(Project project) {
    String oldText = "\"cacheDirectories\": \\[";
    String newText =
      """
      "jestSonar": \\{
          "reportPath": "target/test-results/jest",
          "reportFile": "TESTS-results-sonar.xml"
        \\},
        "cacheDirectories": \\[""";
    projectRepository.replaceText(project, "", "package.json", oldText, newText);
  }
}
