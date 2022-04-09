package tech.jhipster.lite.generator.server.javatool.base.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.javatool.base.domain.JavaBaseService;
import tech.jhipster.lite.generator.tools.domain.Project;

@Service
public class JavaBaseApplicationService {

  private final JavaBaseService javaBaseService;

  public JavaBaseApplicationService(JavaBaseService javaBaseService) {
    this.javaBaseService = javaBaseService;
  }

  public void addJavaBase(Project project) {
    javaBaseService.addJavaBase(project);
  }
}
