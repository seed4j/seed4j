package tech.jhipster.lite.generator.server.springboot.database.mongodb.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.database.mongodb.domain.MongodbService;

@Service
public class MongodbApplicationService {

  private final MongodbService mongodbService;

  public MongodbApplicationService(MongodbService mongodbService) {
    this.mongodbService = mongodbService;
  }

  public void init(Project project) {
    mongodbService.init(project);
  }
}
