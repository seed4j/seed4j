package tech.jhipster.lite.generator.server.springboot.database.cassandra.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.database.cassandra.domain.CassandraDomainService;
import tech.jhipster.lite.generator.server.springboot.database.cassandra.domain.CassandraService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;

@Configuration
public class CassandraBeanConfiguration {

  public final BuildToolService buildToolService;
  public final SpringBootCommonService springBootCommonService;
  public final SQLCommonService sqlCommonService;

  public CassandraBeanConfiguration(
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService,
    SQLCommonService sqlCommonService
  ) {
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
    this.sqlCommonService = sqlCommonService;
  }

  @Bean
  public CassandraService cassandraService() {
    return new CassandraDomainService(buildToolService, springBootCommonService, sqlCommonService);
  }
}
