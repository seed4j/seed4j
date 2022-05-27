package tech.jhipster.lite.generator.project.domain;

import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

public class ProjectDomainService implements ProjectService {

  private final NpmService npmService;
  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;

  public ProjectDomainService(NpmService npmService, BuildToolService buildToolService, SpringBootCommonService springBootCommonService) {
    this.npmService = npmService;
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
  }

  @Override
  public Project getProjectDetails(String folderPath) {
    Project project = Project.builder().folder(folderPath).build();

    Map<String, Object> config = new HashMap<>();
    config.put(PROJECT_NAME, npmService.getDescription(project.getFolder()).orElse(null));

    if (project.isMavenProject()) {
      config.put(PACKAGE_NAME, buildToolService.getGroup(project).orElse(null));
      config.put(BASE_NAME, buildToolService.getName(project).orElse(null));
    } else if (project.isGradleProject()) {
      config.put(PACKAGE_NAME, buildToolService.getGroup(project).orElse(null));
    }

    Optional<String> serverPort = springBootCommonService.getProperty(project, "server.port");
    if (serverPort.isPresent()) {
      config.put("serverPort", Integer.parseInt(serverPort.get()));
    }

    return Project.builder().folder(folderPath).config(config).build();
  }
}
