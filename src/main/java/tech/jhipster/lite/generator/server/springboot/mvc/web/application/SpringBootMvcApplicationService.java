package tech.jhipster.lite.generator.server.springboot.mvc.web.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvcService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class SpringBootMvcApplicationService {

  private final SpringBootMvcService springBootMvcService;

  public SpringBootMvcApplicationService(SpringBootMvcService springBootMvcService) {
    this.springBootMvcService = springBootMvcService;
  }

  public void init(Project project) {
    this.springBootMvcService.init(project);
  }

  public void addSpringBootMvc(Project project) {
    springBootMvcService.addSpringBootMvc(project);
  }

  public void addSpringBootUndertow(Project project) {
    springBootMvcService.addSpringBootUndertow(project);
  }

  public void addSpringBootActuator(Project project) {
    springBootMvcService.addSpringBootActuator(project);
  }

  public void addExceptionHandler(Project project) {
    springBootMvcService.addExceptionHandler(project);
  }
}
