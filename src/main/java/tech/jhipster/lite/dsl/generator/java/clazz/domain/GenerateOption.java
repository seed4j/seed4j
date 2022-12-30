package tech.jhipster.lite.dsl.generator.java.clazz.domain;

import tech.jhipster.lite.dsl.generator.java.clazz.domain.reference.RefContexteName;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslContextName;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;

public class GenerateOption {

  private ReferenceManager refManager;
  private ConfigApp configApp;
  private DslContextName ctx;
  private TypePackage typePackage;

  public GenerateOption() {}

  public GenerateOption configApp(ConfigApp configApp) {
    this.configApp = configApp;
    return this;
  }

  public GenerateOption ctx(DslContextName ctx) {
    this.ctx = ctx;
    return this;
  }

  public GenerateOption refManager(ReferenceManager refManager) {
    this.refManager = refManager;
    return this;
  }

  public GenerateOption typePackage(TypePackage typePackage) {
    this.typePackage = typePackage;
    return this;
  }

  public ReferenceManager getRefManager() {
    return refManager;
  }

  public ConfigApp getConfigApp() {
    return configApp;
  }

  public DslContextName getCtx() {
    return ctx;
  }

  public TypePackage getTypePackage() {
    return typePackage;
  }
}
