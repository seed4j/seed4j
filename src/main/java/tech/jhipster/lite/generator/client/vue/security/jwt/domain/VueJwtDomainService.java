package tech.jhipster.lite.generator.client.vue.security.jwt.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.common.domain.WordUtils.LF;
import static tech.jhipster.lite.generator.project.domain.Constants.*;

import java.io.IOException;
import java.util.List;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class VueJwtDomainService implements VueJwtService {

  public static final String SOURCE = "client/vue";

  public static final String SOURCE_JWT = "webapp/app/jwt/";
  public static final String SOURCE_TEST = "test/spec/jwt/";

  public static final String DESTINATION_TEST = TEST_JAVASCRIPT + "/common/";

  public static final String COMMON = "/app/common/";
  public static final String DESTINATION_PRIMARY = MAIN_WEBAPP + COMMON + PRIMARY;
  public static final String SOURCE_PRIMARY = SOURCE_JWT + PRIMARY;

  public static final String DESTINATION_DOMAIN = MAIN_WEBAPP + COMMON + DOMAIN;
  public static final String SOURCE_DOMAIN = SOURCE_JWT + DOMAIN;

  public static final String DESTINATION_SECONDARY = MAIN_WEBAPP + COMMON + SECONDARY;
  public static final String SOURCE_SECONDARY = SOURCE_JWT + SECONDARY;

  public static final String NEEDLE_MAIN_IMPORT = "// jhipster-needle-main-ts-import";

  public static final String NEEDLE_MAIN_PROVIDER = "// jhipster-needle-main-ts-provider";
  private static final String NEEDLE_MAIN_INSTANCIATION = "// jhipster-needle-main-ts-instanciation";

  public static final String NEEDLE_ROUTER = "// jhipster-needle-router";

  public static final String NEEDLE_APP = "// jhipster-needle-app";

  public static final String LOGIN = "/login";

  public static final String HOMEPAGE = "/homepage";

  private final ProjectRepository projectRepository;

  public VueJwtDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addJWT(Project project) {
    checkIfProjectNotNull(project);
    if (VueJwt.isPiniaNotImplemented(project)) {
      throw new GeneratorException("Pinia has not been added");
    }
    addAppContext(project);
    addLoginContext(project);
    addHomepageContext(project);
    addDomainRelated(project);
    addRoutes(project);
    addMain(project);
    addTests(project);
    addSecondary(project);
  }

  private void checkIfProjectNotNull(Project project) {
    Assert.notNull("project", project);
  }

  public void addAppContext(Project project) {
    String destinationAppHtml = DESTINATION_PRIMARY + "/app/App.html";
    String destinationAppComponent = DESTINATION_PRIMARY + "/app";
    try {
      FileUtils.appendLines(getPath(project.getFolder(), destinationAppHtml), VueJwt.appHTML());
    } catch (IOException e) {
      throw new GeneratorException("Error when writing to app.html. Make sur this file exist", e);
    }
    VueJwt
      .appComponent()
      .forEach((line, needle) ->
        addNewNeedleLineToFile(project, line, destinationAppComponent, "App.component.ts", NEEDLE_APP + "-" + needle)
      );
  }

  public void addLoginContext(Project project) {
    String destinationPrimaryLoginContext = DESTINATION_PRIMARY + LOGIN;
    String sourcePrimaryLoginContext = SOURCE_PRIMARY + LOGIN;
    projectRepository.template(
      ProjectFile.forProject(project).withSource(getPath(SOURCE, SOURCE_DOMAIN), "Login.ts").withDestinationFolder(DESTINATION_DOMAIN)
    );
    List<ProjectFile> primaryFiles = VueJwt
      .primaryLoginFiles()
      .stream()
      .map(entry ->
        ProjectFile
          .forProject(project)
          .withSource(getPath(SOURCE, sourcePrimaryLoginContext), entry)
          .withDestinationFolder(destinationPrimaryLoginContext)
      )
      .toList();
    projectRepository.template(primaryFiles);
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, SOURCE_SECONDARY), "RestLogin.ts")
        .withDestinationFolder(DESTINATION_SECONDARY)
    );
  }

  public void addHomepageContext(Project project) {
    String destinationPrimaryHomepageContext = DESTINATION_PRIMARY + HOMEPAGE;
    String sourcePrimaryHomepageContext = SOURCE_PRIMARY + HOMEPAGE;

    List<ProjectFile> primaryHomepageFiles = VueJwt
      .primaryHomepageFiles()
      .stream()
      .map(entry ->
        ProjectFile
          .forProject(project)
          .withSource(getPath(SOURCE, sourcePrimaryHomepageContext), entry)
          .withDestinationFolder(destinationPrimaryHomepageContext)
      )
      .toList();

    projectRepository.template(primaryHomepageFiles);

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

  public void addRoutes(Project project) {
    String routerPath = "src/main/webapp/app/router";

    projectRepository.replaceText(
      project,
      getPath(routerPath),
      ROUTER_TYPESCRIPT,
      "redirect: \\{ name: 'App' \\}",
      "redirect: { name: 'Homepage' }"
    );
    VueJwt.ROUTER_IMPORTS.forEach(providerLine ->
      addNewNeedleLineToFile(project, providerLine, routerPath, ROUTER_TYPESCRIPT, NEEDLE_ROUTER + "-imports")
    );
    VueJwt.LOGIN_ROUTES.forEach(providerLine ->
      addNewNeedleLineToFile(project, providerLine, routerPath, ROUTER_TYPESCRIPT, NEEDLE_ROUTER + "-routes")
    );
  }

  public void addMain(Project project) {
    String appPath = "src/main/webapp/app";
    projectRepository.replaceText(project, getPath(appPath), MAIN_TYPESCRIPT, "createApp\\(App\\)", "createApp(Homepage)");
    VueJwt.MAIN_IMPORTS.forEach(providerLine -> addNewNeedleLineToFile(project, providerLine, appPath, MAIN_TYPESCRIPT, NEEDLE_MAIN_IMPORT)
    );
    VueJwt.MAIN_PROVIDER.forEach(providerLine ->
      addNewNeedleLineToFile(project, providerLine, appPath, MAIN_TYPESCRIPT, NEEDLE_MAIN_INSTANCIATION)
    );
    VueJwt.MAIN_PROVIDES.forEach(providerLine ->
      addNewNeedleLineToFile(project, providerLine, appPath, MAIN_TYPESCRIPT, NEEDLE_MAIN_PROVIDER)
    );
  }

  public void addTests(Project project) {
    String testDomainPath = DESTINATION_TEST + "domain";
    String testPrimaryPath = DESTINATION_TEST + "primary";
    String testSecondaryPath = DESTINATION_TEST + "secondary";

    List<ProjectFile> testDomainFiles = VueJwt
      .testDomainFiles()
      .stream()
      .map(entry ->
        ProjectFile.forProject(project).withSource(getPath(SOURCE, SOURCE_TEST + DOMAIN), entry).withDestinationFolder(testDomainPath)
      )
      .toList();
    projectRepository.template(testDomainFiles);
    VueJwt
      .appTest()
      .forEach((line, needle) ->
        addNewNeedleLineToFile(project, line, "src/test/javascript/spec/common/primary/app", "App.spec.ts", NEEDLE_APP + "-" + needle)
      );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, SOURCE_TEST + PRIMARY + LOGIN), "Login.spec.ts")
        .withDestinationFolder(testPrimaryPath + LOGIN)
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, SOURCE_TEST + PRIMARY + HOMEPAGE), "Homepage.spec.ts")
        .withDestinationFolder(testPrimaryPath + HOMEPAGE)
    );

    List<ProjectFile> testSecondaryFiles = VueJwt
      .testSecondaryFiles()
      .stream()
      .map(entry ->
        ProjectFile.forProject(project).withSource(getPath(SOURCE, SOURCE_TEST + SECONDARY), entry).withDestinationFolder(testSecondaryPath)
      )
      .toList();

    projectRepository.template(testSecondaryFiles);

    VueJwt
      .routerspec()
      .forEach((line, needle) ->
        addNewNeedleLineToFile(project, line, getPath("src/test/javascript/spec/router"), "Router.spec.ts", NEEDLE_ROUTER + "-" + needle)
      );
  }

  private void addNewNeedleLineToFile(Project project, String importLine, String folder, String file, String needle) {
    String importWithNeedle = importLine + LF + needle;
    projectRepository.replaceText(project, getPath(folder), file, needle, importWithNeedle);
  }

  private void addSecondary(Project project) {
    List<ProjectFile> secondaryFiles = VueJwt
      .secondaryFiles()
      .stream()
      .map(entry ->
        ProjectFile.forProject(project).withSource(getPath(SOURCE, SOURCE_SECONDARY), entry).withDestinationFolder(DESTINATION_SECONDARY)
      )
      .toList();
    projectRepository.template(secondaryFiles);
  }

  public void addDomainRelated(Project project) {
    List<ProjectFile> domainFiles = VueJwt
      .domainFiles()
      .stream()
      .map(entry ->
        ProjectFile.forProject(project).withSource(getPath(SOURCE, SOURCE_DOMAIN), entry).withDestinationFolder(DESTINATION_DOMAIN)
      )
      .toList();
    projectRepository.template(domainFiles);
  }
}
