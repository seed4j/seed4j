package com.seed4j.generator.server.springboot.database.redis.application;

import com.seed4j.generator.server.springboot.database.redis.domain.RedisModuleFactory;
import com.seed4j.module.domain.SeedModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class RedisApplicationService {

  private final RedisModuleFactory redis;

  public RedisApplicationService(DockerImages dockerImages) {
    redis = new RedisModuleFactory(dockerImages);
  }

  public SeedModule buildModule(SeedModuleProperties properties) {
    return redis.buildModule(properties);
  }
}
