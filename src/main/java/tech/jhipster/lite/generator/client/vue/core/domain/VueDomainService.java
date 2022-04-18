package tech.jhipster.lite.generator.client.vue.core.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.common.domain.WordUtils.LF;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_TYPESCRIPT;
import static tech.jhipster.lite.generator.project.domain.Constants.PACKAGE_JSON;
import static tech.jhipster.lite.generator.project.domain.Constants.ROUTER_TYPESCRIPT;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;

import java.util.List;
import java.util.Map;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public record VueDomainService(ProjectRepository projectRepository, NpmService npmService) implements VueService {
  public static final String SOURCE = "client/vue";
  public static final String NEEDLE_IMPORT = "// jhipster-needle-main-ts-import";
  public static final String NEEDLE_PROVIDER = "// jhipster-needle-main-ts-provider";
  public static final String SOURCE_PRIMARY = getPath(SOURCE, "webapp/app/common/primary");
  public static final String SOURCE_TEST_PRIMARY = getPath(SOURCE, "test/spec/common/primary");
  public static final String SOURCE_PRIMARY_APP = getPath(SOURCE_PRIMARY, "app");
  public static final String DESTINATION_PRIMARY = "src/main/webapp/app/common/primary";
  public static final String DESTINATION_PRIMARY_APP = DESTINATION_PRIMARY + "/app";
  public static final String DESTINATION_PRIMARY_TEST = "src/test/javascript/spec/common/primary";
  public static final String DESTINATION_PRIMARY_ROUTER = DESTINATION_PRIMARY + "/app";
  public static final String DESTINATION_APP = "src/main/webapp/app";
  public static final String DESTINATION_ROUTER = DESTINATION_APP + "/router";

  @Override
  public void addVue(Project project) {
    addCommonVue(project);
    addAppFilesWithoutCss(project);
  }

  @Override
  public void addStyledVue(Project project) {
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
    addRootFiles(project);
    addAppFiles(project);
    addRouter(project);
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
    // prettier-ignore
    Map
      .of(
        "build", "vue-tsc --noEmit && vite build --emptyOutDir",
        "dev", "vite",
        "jest", "jest src/test/javascript/spec --logHeapUsage --maxWorkers=2 --no-cache",
        "preview", "vite preview",
        "start", "vite",
        "test", "npm run jest --",
        "test:watch", "npm run jest -- --watch"
      )
      .forEach((name, cmd) -> npmService.addScript(project, name, cmd));
  }

  public void addViteConfigFiles(Project project) {
    List
      .of(".eslintrc.js", "jest.config.js", "tsconfig.json", "vite.config.ts")
      .forEach(file -> projectRepository.add(project, SOURCE, file));
  }

  public void addRootFiles(Project project) {
    projectRepository.template(project, getPath(SOURCE, "webapp"), "index.html", "src/main/webapp");
    projectRepository.template(project, getPath(SOURCE, "webapp/app"), "env.d.ts", DESTINATION_APP);
    projectRepository.template(project, getPath(SOURCE, "webapp/app"), MAIN_TYPESCRIPT, DESTINATION_APP);
  }

  private void addRouterFiles(Project project) {
    addRouterConfigAndTestFiles(project);
    addRouterMainConfiguration(project);
  }

  private void addRouterConfigAndTestFiles(Project project) {
    projectRepository.template(project, getPath(SOURCE, "webapp/app/router"), ROUTER_TYPESCRIPT, "src/main/webapp/app/router");
    projectRepository.template(project, getPath(SOURCE, "test/spec/router"), "Router.spec.ts", "src/test/javascript/spec/router");
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

    projectRepository.template(project, SOURCE_PRIMARY_APP, "App.component.ts", DESTINATION_PRIMARY_APP);
    projectRepository.template(project, SOURCE_PRIMARY_APP, "index.ts", DESTINATION_PRIMARY_APP);

    projectRepository.template(project, getPath(SOURCE_TEST_PRIMARY, "app"), "App.spec.ts", DESTINATION_PRIMARY_TEST + "/app");
  }

  public void addAppFilesWithoutCss(Project project) {
    project.addDefaultConfig(BASE_NAME);

    projectRepository.template(project, SOURCE_PRIMARY_APP, "App.html", DESTINATION_PRIMARY_APP);
    projectRepository.template(project, SOURCE_PRIMARY_APP, "App.vue", DESTINATION_PRIMARY_APP);
  }

  public void addAppFilesWithCss(Project project) {
    project.addDefaultConfig(BASE_NAME);

    projectRepository.template(project, SOURCE_PRIMARY_APP, "StyledApp.html", DESTINATION_PRIMARY_APP, "App.html");
    projectRepository.template(project, SOURCE_PRIMARY_APP, "StyledApp.vue", DESTINATION_PRIMARY_APP, "App.vue");

    projectRepository.add(
      project,
      getPath(SOURCE, "webapp/content/images"),
      "JHipster-Lite-neon-green.png",
      "src/main/webapp/content/images"
    );
    projectRepository.add(project, getPath(SOURCE, "webapp/content/images"), "VueLogo.png", "src/main/webapp/content/images");
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
