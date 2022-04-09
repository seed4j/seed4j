package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2SecurityService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class OAuth2SecurityApplicationService {

  private final OAuth2SecurityService oauth2SecurityService;

  public OAuth2SecurityApplicationService(OAuth2SecurityService oauth2SecurityService) {
    this.oauth2SecurityService = oauth2SecurityService;
  }

  public void addOAuth2(Project project) {
    oauth2SecurityService.addOAuth2(project);
  }

  public void addAccountContext(Project project) {
    oauth2SecurityService.addAccountContext(project);
  }
}
