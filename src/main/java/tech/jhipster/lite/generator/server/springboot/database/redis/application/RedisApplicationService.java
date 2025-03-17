package tech.jhipster.lite.generator.server.springboot.database.redis.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.database.redis.domain.RedisModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class RedisApplicationService {

  private final RedisModuleFactory redis;

  public RedisApplicationService(DockerImages dockerImages) {
    redis = new RedisModuleFactory(dockerImages);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return redis.buildModule(properties);
  }
}
