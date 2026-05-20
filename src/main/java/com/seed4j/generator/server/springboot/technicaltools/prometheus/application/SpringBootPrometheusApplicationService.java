package com.seed4j.generator.server.springboot.technicaltools.prometheus.application;

import com.seed4j.generator.server.springboot.technicaltools.prometheus.domain.SpringBootPrometheusModuleFactory;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.springframework.stereotype.Service;

@Service
public class SpringBootPrometheusApplicationService {

  private final SpringBootPrometheusModuleFactory springBootPrometheus;

  public SpringBootPrometheusApplicationService() {
    springBootPrometheus = new SpringBootPrometheusModuleFactory();
  }

  public Seed4JModule buildModule(Seed4JModuleProperties properties) {
    return springBootPrometheus.buildModule(properties);
  }
}
