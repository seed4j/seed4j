package tech.jhipster.lite.generator.server.springboot.database.mongodb.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.database.mongodb.domain.MongodbService;

@Service
public record MongodbApplicationService(MongodbService mongodbService) {
  public void init(Project project) {
    mongodbService.init(project);
  }
}
