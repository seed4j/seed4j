package tech.jhipster.lite.generator.server.springboot.banner.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.banner.domain.BannerModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class BannerApplicationService {

  private final BannerModuleFactory factory;

  public BannerApplicationService() {
    this.factory = new BannerModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
