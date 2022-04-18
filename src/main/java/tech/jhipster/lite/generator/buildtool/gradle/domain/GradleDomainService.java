package tech.jhipster.lite.generator.buildtool.gradle.domain;

import tech.jhipster.lite.common.domain.WordUtils;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.BUILD_GRADLE_KTS;
import static tech.jhipster.lite.generator.project.domain.Constants.SETTINGS_GRADLE_KTS;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PROJECT_NAME;

public record GradleDomainService(
  ProjectRepository projectRepository) implements GradleService {

  public static final String SOURCE = "buildtool/gradle";

  @Override
  public void initJava(Project project) {
    addJavaBuildGradleKts(project);
    addGradleWrapper(project);
  }

  @Override
  public void addJavaBuildGradleKts(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(PROJECT_NAME);
    project.addDefaultConfig(BASE_NAME);

    String baseName = project.getBaseName().orElse("");
    project.addConfig("dasherizedBaseName", WordUtils.kebabCase(baseName));

    projectRepository.template(project, SOURCE, BUILD_GRADLE_KTS);
    projectRepository.template(project, SOURCE, SETTINGS_GRADLE_KTS);
  }

  @Override
  public void addGradleWrapper(Project project) {
    Gradle
      .gradleWrapper()
      .forEach((file, location) -> projectRepository.add(project, getPath(SOURCE, location), file, location));
    projectRepository.setExecutable(project, "", "gradlew");
    projectRepository.setExecutable(project, "", "gradlew.bat");
  }
}
