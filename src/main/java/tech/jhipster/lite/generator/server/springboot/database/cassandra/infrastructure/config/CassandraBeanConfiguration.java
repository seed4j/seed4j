package tech.jhipster.lite.generator.server.springboot.database.cassandra.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.database.cassandra.domain.CassandraDomainService;
import tech.jhipster.lite.generator.server.springboot.database.cassandra.domain.CassandraService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;

@Configuration
public class CassandraBeanConfiguration {

  public final BuildToolService buildToolService;
  public final SQLCommonService sqlCommonService;
  public final ProjectRepository projectRepository;

  public CassandraBeanConfiguration(
    BuildToolService buildToolService,
    SQLCommonService sqlCommonService,
    ProjectRepository projectRepository
  ) {
    this.buildToolService = buildToolService;
    this.sqlCommonService = sqlCommonService;
    this.projectRepository = projectRepository;
  }

  @Bean
  public CassandraService cassandraService() {
    return new CassandraDomainService(buildToolService, sqlCommonService, projectRepository);
  }
}
