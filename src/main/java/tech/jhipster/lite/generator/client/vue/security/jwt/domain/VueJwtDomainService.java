package tech.jhipster.lite.generator.client.vue.security.jwt.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class VueJwtDomainService implements VueJwtService {

  public static final String SOURCE = "client/vue";

  public static final String DESTINATION_PRIMARY = "src/main/webapp/app/common/primary/";
  public static final String SOURCE_PRIMARY = "webapp/app/jwt/primary/";

  public static final String DESTINATION_DOMAIN = "src/main/webapp/app/common/domain/";
  public static final String SOURCE_DOMAIN = "webapp/app/jwt/domain/";

  public static final String DESTINATION_SECONDARY = "src/main/webapp/app/common/secondary/";
  public static final String SOURCE_SECONDARY = "webapp/app/jwt/secondary/";

  private final ProjectRepository projectRepository;

  public VueJwtDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addJWT(Project project) {
    Assert.notNull("project", project);
    if (!VueJwt.checkIfPinia(project)) throw new GeneratorException("Pinia has not been added");
    addLoginContext(project);
  }

  public void addLoginContext(Project project) {
    String destinationPrimaryLoginContext = DESTINATION_PRIMARY + "/login";
    String sourcePrimaryLoginContext = SOURCE_PRIMARY + "/login";
    projectRepository.template(project, getPath(SOURCE, SOURCE_DOMAIN), "Login.ts", DESTINATION_DOMAIN);
    projectRepository.template(project, getPath(SOURCE, sourcePrimaryLoginContext), "index.ts", destinationPrimaryLoginContext);
    projectRepository.template(project, getPath(SOURCE, sourcePrimaryLoginContext), "Login.component.ts", destinationPrimaryLoginContext);
    projectRepository.template(project, getPath(SOURCE, sourcePrimaryLoginContext), "Login.html", destinationPrimaryLoginContext);
    projectRepository.template(project, getPath(SOURCE, sourcePrimaryLoginContext), "Login.vue", destinationPrimaryLoginContext);
    projectRepository.template(project, getPath(SOURCE, SOURCE_SECONDARY), "LoginDTO.ts", DESTINATION_SECONDARY);
  }
}
