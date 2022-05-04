package tech.jhipster.lite.generator.client.vue.security.jwt.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class JWTDomainService implements JWTService {

  public static final String SOURCE = "client/vue";

  private final ProjectRepository projectRepository;

  public JWTDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addJWT(Project project) {
    Assert.notNull("project", project);
    if (!JWT.checkIfPinia(project)) throw new GeneratorException("Pinia has not been added");
    addLoginContext(project);
  }

  public void addLoginContext(Project project) {
    String destinationPrimaryLoginContext = "src/main/webapp/app/common/primary/login";
    String sourcePrimaryLoginContext = "webapp/app/jwt/primary/login";
    projectRepository.template(project, getPath(SOURCE, "webapp/app/jwt/domain"), "Login.ts", "src/main/webapp/app/common/domain");
    projectRepository.template(project, getPath(SOURCE, sourcePrimaryLoginContext), "index.ts", destinationPrimaryLoginContext);
    projectRepository.template(project, getPath(SOURCE, sourcePrimaryLoginContext), "Login.component.ts", destinationPrimaryLoginContext);
    projectRepository.template(project, getPath(SOURCE, sourcePrimaryLoginContext), "Login.html", destinationPrimaryLoginContext);
    projectRepository.template(project, getPath(SOURCE, sourcePrimaryLoginContext), "Login.vue", destinationPrimaryLoginContext);
    projectRepository.template(project, getPath(SOURCE, "webapp/app/jwt/secondary"), "LoginDTO.ts", "src/main/webapp/app/common/secondary");
  }
}
