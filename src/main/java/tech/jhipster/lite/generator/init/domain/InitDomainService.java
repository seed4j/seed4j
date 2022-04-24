package tech.jhipster.lite.generator.init.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.common.domain.WordUtils.CRLF;
import static tech.jhipster.lite.generator.project.domain.Constants.PACKAGE_JSON;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PRETTIER_DEFAULT_INDENT;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PROJECT_NAME;

import java.util.List;
import java.util.Map;
import tech.jhipster.lite.common.domain.WordUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class InitDomainService implements InitService {

  public static final String SOURCE = "init";
  public static final String HUSKY_FOLDER = ".husky";

  private final NpmService npmService;
  private final ProjectRepository projectRepository;

  public InitDomainService(NpmService npmService, ProjectRepository projectRepository) {
    this.npmService = npmService;
    this.projectRepository = projectRepository;
  }

  @Override
  public void init(Project project) {
    addReadme(project);
    addGitConfiguration(project);
    addEditorConfiguration(project);
    addPrettier(project);
    addPackageJson(project);
    gitInit(project);
  }

  @Override
  public void initMinimal(Project project) {
    addReadme(project);
    addGitConfiguration(project);
    addEditorConfiguration(project);
    gitInit(project);
  }

  @Override
  public void addPackageJson(Project project) {
    project.addDefaultConfig(PROJECT_NAME);
    project.addDefaultConfig(BASE_NAME);
    project.addConfig("nodeVersion", Init.getNodeVersion());

    String baseName = project.getBaseName().orElse("");
    project.addConfig("dasherizedBaseName", WordUtils.kebabCase(baseName));

    projectRepository.template(project, SOURCE, PACKAGE_JSON);
    addDevDependencies(project);
    addScripts(project);
  }

  private void addDevDependencies(Project project) {
    List
      .of("@prettier/plugin-xml", "husky", "lint-staged", "prettier", "prettier-plugin-java", "prettier-plugin-packagejson")
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
  }

  private void addScripts(Project project) {
    Map
      .of(
        "prepare",
        "husky install",
        "prettier:check",
        "prettier --check \\\\\"{,src/**/}*.{md,json,yml,html,js,ts,tsx,css,scss,vue,java,xml}\\\\\"",
        "prettier:format",
        "prettier --write \\\\\"{,src/**/}*.{md,json,yml,html,js,ts,tsx,css,scss,vue,java,xml}\\\\\""
      )
      .forEach((name, cmd) -> npmService.addScript(project, name, cmd));
  }

  @Override
  public void addReadme(Project project) {
    project.addDefaultConfig(PROJECT_NAME);

    projectRepository.template(project, SOURCE, "README.md");
  }

  @Override
  public void addGitConfiguration(Project project) {
    projectRepository.add(project, SOURCE, "gitignore", ".", ".gitignore");
    projectRepository.add(project, SOURCE, "gitattributes", ".", ".gitattributes");
  }

  @Override
  public void addEditorConfiguration(Project project) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);

    project.addConfig("editorConfigEndOfLine", CRLF.equals(project.getEndOfLine()) ? "crlf" : "lf");
    projectRepository.template(project, SOURCE, ".editorconfig");

    projectRepository.add(project, SOURCE, ".eslintignore");
  }

  @Override
  public void addPrettier(Project project) {
    projectRepository.add(project, SOURCE, ".lintstagedrc.js");
    projectRepository.add(project, SOURCE, ".prettierignore");
    projectRepository.add(project, getPath(SOURCE, HUSKY_FOLDER), "pre-commit", HUSKY_FOLDER);
    projectRepository.setExecutable(project, HUSKY_FOLDER, "pre-commit");

    project.addConfig("prettierEndOfLine", CRLF.equals(project.getEndOfLine()) ? "crlf" : "lf");
    projectRepository.template(project, SOURCE, ".prettierrc");
  }

  @Override
  public void gitInit(Project project) {
    projectRepository.gitInit(project);
  }

  @Override
  public byte[] download(Project project) {
    if (projectRepository.isJHipsterLiteProject(project.getFolder())) {
      return projectRepository.download(project);
    }
    throw new GeneratorException("This project is not a JHipster Lite project");
  }
}
