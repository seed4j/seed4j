package tech.jhipster.lite.generator.client.loader.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.loader.domain.TsLoaderModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class TsLoaderModuleApplicationService {

  private final TsLoaderModuleFactory tsLoader;

  public TsLoaderModuleApplicationService() {
    this.tsLoader = new TsLoaderModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return tsLoader.buildModule(properties);
  }
}
