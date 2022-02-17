package tech.jhipster.lite.generator.server.springboot.database.mongodb.domain;

import tech.jhipster.lite.generator.project.domain.Project;

public interface MongodbService {
  void init(Project project);

  void addSpringDataMongodb(Project project);
  void addMongodbDriver(Project project);
  void addDockerCompose(Project project);
  void addJavaFiles(Project project);
  void addConfigurationFiles(Project project);
  void addProperties(Project project);
  void addTestcontainers(Project project);
  void addLoggerInConfiguration(Project project);
}
