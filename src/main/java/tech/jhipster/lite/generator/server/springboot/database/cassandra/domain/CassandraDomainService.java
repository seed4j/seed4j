package tech.jhipster.lite.generator.server.springboot.database.cassandra.domain;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;

public class CassandraDomainService implements CassandraService {

  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;
  private final SQLCommonService sqlCommonService;

  public CassandraDomainService(
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService,
    SQLCommonService sqlCommonService
  ) {
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
    this.sqlCommonService = sqlCommonService;
  }

  @Override
  public void init(Project project) {
    Assert.notNull("project", project);

    addSpringDataCassandra(project);
  }

  public void addSpringDataCassandra(Project project) {
    Dependency dependency = Dependency
      .builder()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-starter-data-cassandra")
      .build();

    buildToolService.addDependency(project, dependency);
  }
}
