package tech.jhipster.lite.generator.server.springboot.cache.common.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.tools.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.tools.domain.DefaultConfig.PACKAGE_NAME;

import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.tools.domain.Project;
import tech.jhipster.lite.generator.tools.domain.ProjectRepository;

public class SpringBootCacheDomainService implements SpringBootCacheService {

  public static final String SOURCE = "server/springboot/cache/common";
  public static final String DESTINATION = "technical/infrastructure/secondary/cache";

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;

  public SpringBootCacheDomainService(BuildToolService buildToolService, ProjectRepository projectRepository) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
  }

  @Override
  public void addDependencies(Project project) {
    buildToolService.addDependency(project, SpringBootCache.springBootStarterCacheDependency());
  }

  @Override
  public void addEnableCaching(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(BASE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));

    templateToCache(project, packageNamePath, "src", "CacheConfiguration.java", MAIN_JAVA);
  }

  private void templateToCache(Project project, String source, String type, String sourceFilename, String destination) {
    projectRepository.template(project, getPath(SOURCE, type), sourceFilename, getPath(destination, source, DESTINATION));
  }
}
