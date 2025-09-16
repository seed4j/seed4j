package com.seed4j.generator.server.springboot.database.redis.application;

import com.seed4j.generator.server.springboot.database.redis.domain.RedisModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class RedisApplicationService {

  private final RedisModuleFactory redis;

  public RedisApplicationService(DockerImages dockerImages) {
    redis = new RedisModuleFactory(dockerImages);
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return redis.buildModule(properties);
  }
}
