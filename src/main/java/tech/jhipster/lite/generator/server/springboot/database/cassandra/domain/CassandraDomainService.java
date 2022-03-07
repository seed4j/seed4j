package tech.jhipster.lite.generator.server.springboot.database.cassandra.domain;

import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommon.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;

public class CassandraDomainService implements CassandraService {

  private final BuildToolService buildToolService;
  private final SQLCommonService sqlCommonService;
  private final ProjectRepository projectRepository;
  private final String dockerImage = Cassandra.getDockerImageName();

  public CassandraDomainService(BuildToolService buildToolService, SQLCommonService sqlCommonService, ProjectRepository projectRepository) {
    this.buildToolService = buildToolService;
    this.sqlCommonService = sqlCommonService;
    this.projectRepository = projectRepository;
  }

  @Override
  public void init(Project project) {
    Assert.notNull("project", project);

    addSpringDataCassandra(project);
    addDockerCompose(project);
    addScripts(project);
    addYmlFiles(project);
    addDockerFile(project);
    addCQL(project);
  }

  private void addCQL(Project project) {
    addTemplateVariables(project);
    projectRepository.template(project, getSource(DatabaseType.CASSANDRA.id()), "create-keyspace.cql", "src/main/resources/config/cql");
  }

  private void addDockerFile(Project project) {
    projectRepository.add(project, getSource(DatabaseType.CASSANDRA.id()), "Cassandra-Migration.Dockerfile", "src/main/docker/cassandra");
  }

  private void addYmlFiles(Project project) {
    addTemplateVariables(project);
    Cassandra
      .getYmlFiles()
      .forEach(fileName ->
        projectRepository.template(project, getSource(DatabaseType.CASSANDRA.id()), fileName + ".yml", "src/main/docker", fileName + ".yml")
      );
  }

  private void addScripts(Project project) {
    Cassandra
      .getScripts()
      .forEach(fileName ->
        projectRepository.add(
          project,
          getSource(DatabaseType.CASSANDRA.id()),
          fileName + ".sh",
          "src/main/docker/cassandra/scripts",
          fileName + ".sh"
        )
      );
  }

  private void addDockerCompose(Project project) {
    addTemplateVariables(project);
    sqlCommonService.addDockerComposeTemplate(project, DatabaseType.CASSANDRA.id());
  }

  public void addSpringDataCassandra(Project project) {
    Dependency dependency = Dependency
      .builder()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-starter-data-cassandra")
      .build();

    buildToolService.addDependency(project, dependency);
  }

  public void addTemplateVariables(Project project) {
    project.addDefaultConfig(BASE_NAME);
    project.addConfig("dockerImageName", dockerImage);
  }
}
