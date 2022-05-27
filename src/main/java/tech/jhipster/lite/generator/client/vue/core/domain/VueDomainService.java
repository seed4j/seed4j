package tech.jhipster.lite.generator.client.vue.core.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.common.domain.WordUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class VueDomainService implements VueService {

  public static final String SOURCE = "client/vue";
  public static final String NEEDLE_IMPORT = "// jhipster-needle-main-ts-import";
  public static final String NEEDLE_PROVIDER = "// jhipster-needle-main-ts-provider";
  public static final String SOURCE_PRIMARY = getPath(SOURCE, "webapp/app/common/primary");
  public static final String SOURCE_DOMAIN = getPath(SOURCE, "webapp/app/common/domain");
  public static final String SOURCE_SECONDARY = getPath(SOURCE, "webapp/app/common/secondary");
  public static final String SOURCE_TEST_PRIMARY = getPath(SOURCE, "test/spec/common/primary");
  public static final String SOURCE_PRIMARY_APP = getPath(SOURCE_PRIMARY, "app");
  public static final String DESTINATION_PRIMARY = "src/main/webapp/app/common/primary";
  public static final String DESTINATION_DOMAIN = "src/main/webapp/app/common/domain";
  public static final String DESTINATION_SECONDARY = "src/main/webapp/app/common/secondary";
  public static final String DESTINATION_PRIMARY_APP = DESTINATION_PRIMARY + "/app";
  public static final String DESTINATION_PRIMARY_TEST = "src/test/javascript/spec/common/primary";
  public static final String DESTINATION_PRIMARY_ROUTER = DESTINATION_PRIMARY + "/app";
  public static final String DESTINATION_APP = "src/main/webapp/app";
  public static final String DESTINATION_ROUTER = DESTINATION_APP + "/router";

  private final ProjectRepository projectRepository;
  private final NpmService npmService;

  public VueDomainService(ProjectRepository projectRepository, NpmService npmService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
  }

  @Override
  public void addVue(Project project) {
    addCommonVue(project);
    addAppFilesWithCss(project);
  }

  @Override
  public void addPinia(Project project) {
    Assert.notNull("project", project);

    addPiniaDependencies(project);
    addPiniaMainConfiguration(project);
  }

  private void addCommonVue(Project project) {
    Assert.notNull("project", project);
    addDependencies(project);
    addDevDependencies(project);
    addScripts(project);
    addJestSonar(project);
    addViteConfigFiles(project);
    addAxios(project);
    addRootFiles(project);
    addAppFiles(project);
    addRouter(project);
    addLogger(project);
  }

  public void addDependencies(Project project) {
    Vue.dependencies().forEach(dependency -> addDependency(project, dependency));
  }

  public void addPiniaDependencies(Project project) {
    Vue.piniaDependencies().forEach(dependency -> addDependency(project, dependency));
    Vue.piniaDevDependencies().forEach(devDependency -> addDevDependency(project, devDependency));
  }

  private void addPiniaMainConfiguration(Project project) {
    Vue.PINIA_IMPORTS.forEach(importLine -> addNewNeedleLineToFile(project, importLine, DESTINATION_APP, MAIN_TYPESCRIPT, NEEDLE_IMPORT));
    Vue.PINIA_PROVIDERS.forEach(providerLine ->
      addNewNeedleLineToFile(project, providerLine, DESTINATION_APP, MAIN_TYPESCRIPT, NEEDLE_PROVIDER)
    );
  }

  public void addAxios(Project project) {
    addAxiosDependency(project);
    addAxiosFile(project);
    addAxiosTestFiles(project);
  }

  private void addAxiosTestFiles(Project project) {
    String destinationAxiosTest = "src/test/javascript/spec/http";
    String sourceAxiosTest = "test/spec/http";
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, sourceAxiosTest), "AxiosHttp.spec.ts")
        .withDestinationFolder(destinationAxiosTest)
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, sourceAxiosTest), "AxiosHttpStub.ts")
        .withDestinationFolder(destinationAxiosTest)
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, sourceAxiosTest), "AxiosStub.ts")
        .withDestinationFolder(destinationAxiosTest)
    );
  }

  private void addAxiosDependency(Project project) {
    Vue.axiosDependency().forEach(dependency -> addDependency(project, dependency));
  }

  private void addAxiosFile(Project project) {
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, "webapp/app/http"), "AxiosHttp.ts")
        .withDestinationFolder("src/main/webapp/app/http")
    );
  }

  public void addLogger(Project project) {
    addLoggerFiles(project);
    addLoggerTestFiles(project);
  }

  private void addLoggerFiles(Project project) {
    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE_DOMAIN, "Logger.ts").withDestinationFolder(DESTINATION_DOMAIN)
    );
    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE_DOMAIN, "Message.ts").withDestinationFolder(DESTINATION_DOMAIN)
    );
    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE_SECONDARY, "ConsoleLogger.ts").withDestinationFolder(DESTINATION_SECONDARY)
    );
  }

  private void addLoggerTestFiles(Project project) {
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, "test/spec/common/domain"), "Logger.fixture.ts")
        .withDestinationFolder("src/test/javascript/spec/common/domain")
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, "test/spec/common/secondary"), "ConsoleLogger.spec.ts")
        .withDestinationFolder("src/test/javascript/spec/common/secondary")
    );
  }

  public void addDevDependencies(Project project) {
    Vue.devDependencies().forEach(devDependency -> addDevDependency(project, devDependency));
  }

  public void addRouter(Project project) {
    addRouterDependency(project);
    addRouterFiles(project);
  }

  private void addRouterDependency(Project project) {
    Vue.routerDependencies().forEach(dependency -> addDependency(project, dependency));
  }

  private void addDependency(Project project, String dependency) {
    npmService
      .getVersion("vue", dependency)
      .ifPresentOrElse(
        version -> npmService.addDependency(project, dependency, version),
        () -> {
          throw new GeneratorException("Dependency not found: " + dependency);
        }
      );
  }

  private void addDevDependency(Project project, String devDependency) {
    npmService
      .getVersion("vue", devDependency)
      .ifPresentOrElse(
        version -> npmService.addDevDependency(project, devDependency, version),
        () -> {
          throw new GeneratorException("DevDependency not found: " + devDependency);
        }
      );
  }

  public void addScripts(Project project) {
    Vue.scripts().forEach((name, cmd) -> npmService.addScript(project, name, cmd));
  }

  public void addViteConfigFiles(Project project) {
    projectRepository.add(ProjectFile.forProject(project).all(SOURCE, ".eslintrc.js", "jest.config.js", "tsconfig.json", "vite.config.ts"));
  }

  public void addRootFiles(Project project) {
    projectRepository.template(
      ProjectFile.forProject(project).withSource(getPath(SOURCE, "webapp"), "index.html").withDestinationFolder("src/main/webapp")
    );
    projectRepository.template(
      ProjectFile.forProject(project).withSource(getPath(SOURCE, "webapp/app"), "env.d.ts").withDestinationFolder(DESTINATION_APP)
    );
    projectRepository.template(
      ProjectFile.forProject(project).withSource(getPath(SOURCE, "webapp/app"), MAIN_TYPESCRIPT).withDestinationFolder(DESTINATION_APP)
    );
  }

  private void addRouterFiles(Project project) {
    addRouterConfigAndTestFiles(project);
    addRouterMainConfiguration(project);
  }

  private void addRouterConfigAndTestFiles(Project project) {
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, "webapp/app/router"), ROUTER_TYPESCRIPT)
        .withDestinationFolder("src/main/webapp/app/router")
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, "test/spec/router"), "Router.spec.ts")
        .withDestinationFolder("src/test/javascript/spec/router")
    );
  }

  private void addRouterMainConfiguration(Project project) {
    addNewNeedleLineToFile(project, Vue.ROUTER_IMPORT, DESTINATION_APP, MAIN_TYPESCRIPT, NEEDLE_IMPORT);
    addNewNeedleLineToFile(project, Vue.ROUTER_PROVIDER, DESTINATION_APP, MAIN_TYPESCRIPT, NEEDLE_PROVIDER);
  }

  private void addNewNeedleLineToFile(Project project, String importLine, String folder, String file, String needle) {
    String importWithNeedle = importLine + LF + needle;
    projectRepository.replaceText(project, getPath(folder), file, needle, importWithNeedle);
  }

  public void addAppFiles(Project project) {
    project.addDefaultConfig(BASE_NAME);

    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE_PRIMARY_APP, "App.component.ts").withDestinationFolder(DESTINATION_PRIMARY_APP)
    );
    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE_PRIMARY_APP, "index.ts").withDestinationFolder(DESTINATION_PRIMARY_APP)
    );

    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE_TEST_PRIMARY, "app"), "App.spec.ts")
        .withDestinationFolder(DESTINATION_PRIMARY_TEST + "/app")
    );
  }

  public void addAppFilesWithCss(Project project) {
    project.addDefaultConfig(BASE_NAME);

    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE_PRIMARY_APP, "App.html").withDestination(DESTINATION_PRIMARY_APP, "App.html")
    );
    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE_PRIMARY_APP, "App.vue").withDestination(DESTINATION_PRIMARY_APP, "App.vue")
    );

    projectRepository.add(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, "webapp/content/images"), "JHipster-Lite-neon-green.png")
        .withDestinationFolder("src/main/webapp/content/images")
    );
    projectRepository.add(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, "webapp/content/images"), "VueLogo.png")
        .withDestinationFolder("src/main/webapp/content/images")
    );
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
    projectRepository.replaceText(project, "", PACKAGE_JSON, oldText, newText);
  }
}
