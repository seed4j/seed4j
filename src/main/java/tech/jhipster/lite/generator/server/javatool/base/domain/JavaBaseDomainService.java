package tech.jhipster.lite.generator.server.javatool.base.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_NAME;

import tech.jhipster.lite.generator.tools.domain.DefaultConfig;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

public class JavaBaseDomainService implements JavaBaseService {

  public static final String SOURCE = "server/javatool/base";
  public static final String ERROR_DOMAIN_PATH = "error/domain";

  private final ProjectRepository projectRepository;

  public JavaBaseDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addJavaBase(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(DefaultConfig.PACKAGE_PATH);

    JavaBase
      .errorDomainFiles()
      .forEach(file -> projectRepository.template(project, SOURCE, file, getPath(MAIN_JAVA, packageNamePath, ERROR_DOMAIN_PATH)));

    JavaBase
      .errorDomainTestFiles()
      .forEach(file -> projectRepository.template(project, SOURCE, file, getPath(TEST_JAVA, packageNamePath, ERROR_DOMAIN_PATH)));

    JavaBase.annotationsFiles().forEach(file -> projectRepository.template(project, SOURCE, file, getPath(TEST_JAVA, packageNamePath)));
  }
}
