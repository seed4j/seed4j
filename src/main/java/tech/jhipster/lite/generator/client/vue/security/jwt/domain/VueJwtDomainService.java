package tech.jhipster.lite.generator.client.vue.security.jwt.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class VueJwtDomainService implements VueJwtService {

  public static final String SOURCE = "client/vue";

  public static final String SOURCE_JWT = "webapp/app/jwt/";

  public static final String COMMON = "/app/common/";
  public static final String DESTINATION_PRIMARY = MAIN_WEBAPP + COMMON + PRIMARY;
  public static final String SOURCE_PRIMARY = SOURCE_JWT + PRIMARY;

  public static final String DESTINATION_DOMAIN = MAIN_WEBAPP + COMMON + DOMAIN;
  public static final String SOURCE_DOMAIN = SOURCE_JWT + DOMAIN;

  public static final String DESTINATION_SECONDARY = MAIN_WEBAPP + COMMON + SECONDARY;
  public static final String SOURCE_SECONDARY = SOURCE_JWT + SECONDARY;

  private final ProjectRepository projectRepository;

  public VueJwtDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addJWT(Project project) {
    checkIfProjectNotNull(project);
    if (VueJwt.isPiniaNotImplemented(project)) throw new GeneratorException("Pinia has not been added");
    addLoginContext(project);
  }

  private void checkIfProjectNotNull(Project project) {
    Assert.notNull("project", project);
  }

  public void addLoginContext(Project project) {
    String destinationPrimaryLoginContext = DESTINATION_PRIMARY + "/login";
    String sourcePrimaryLoginContext = SOURCE_PRIMARY + "/login";
    projectRepository.template(project, getPath(SOURCE, SOURCE_DOMAIN), "Login.ts", DESTINATION_DOMAIN);
    VueJwt
      .primaryLoginFiles()
      .forEach(file -> projectRepository.template(project, getPath(SOURCE, sourcePrimaryLoginContext), file, destinationPrimaryLoginContext)
      );
    projectRepository.template(project, getPath(SOURCE, SOURCE_SECONDARY), "LoginDTO.ts", DESTINATION_SECONDARY);
  }
}
