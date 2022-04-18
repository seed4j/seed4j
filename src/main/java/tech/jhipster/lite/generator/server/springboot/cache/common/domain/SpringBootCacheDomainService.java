package tech.jhipster.lite.generator.server.springboot.cache.common.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;

import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public record SpringBootCacheDomainService(BuildToolService buildToolService, ProjectRepository projectRepository)
  implements SpringBootCacheService {
  public static final String SOURCE = "server/springboot/cache/common";
  public static final String DESTINATION = "technical/infrastructure/secondary/cache";

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
