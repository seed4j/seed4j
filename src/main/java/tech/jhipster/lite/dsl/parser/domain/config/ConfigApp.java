package tech.jhipster.lite.dsl.parser.domain.config;

import java.util.Objects;

public class ConfigApp {

  public static ConfigAppBuilder configBuilder() {
    return new ConfigAppBuilder();
  }

  protected ConfigApp() {}

  private ConfigBaseName baseName;
  private ConfigBasePackage basePackage;
  private ConfigFluentMethod fluentMethod;
  private ConfigPackageDomainName packageDomainName;
  private ConfigPackageInfrastructureName packageInfrastructureName;
  private ConfigPackageInfrastructurePrimaryName packageInfrastructurePrimaryName;
  private ConfigPackageInfrastructureSecondaryName packageInfrastructureSecondaryName;
  private ConfigProjectFolder projectFolder;
  private ConfigUseAssertAsValidation useAssertAsValidation;

  public ConfigBaseName getBaseName() {
    return baseName;
  }

  public ConfigBasePackage getBasePackage() {
    return basePackage;
  }

  public ConfigFluentMethod getFluentMethod() {
    return fluentMethod;
  }

  public ConfigPackageDomainName getPackageDomainName() {
    return packageDomainName;
  }

  public ConfigPackageInfrastructureName getPackageInfrastructureName() {
    return packageInfrastructureName;
  }

  public ConfigPackageInfrastructurePrimaryName getPackageInfrastructurePrimaryName() {
    return packageInfrastructurePrimaryName;
  }

  public ConfigPackageInfrastructureSecondaryName getPackageInfrastructureSecondaryName() {
    return packageInfrastructureSecondaryName;
  }

  public ConfigProjectFolder getProjectFolder() {
    return projectFolder;
  }

  public ConfigUseAssertAsValidation getUseAssertAsValidation() {
    return useAssertAsValidation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ConfigApp configApp = (ConfigApp) o;
    return (
      baseName.equals(configApp.baseName) &&
      basePackage.equals(configApp.basePackage) &&
      fluentMethod.equals(configApp.fluentMethod) &&
      packageDomainName.equals(configApp.packageDomainName) &&
      packageInfrastructureName.equals(configApp.packageInfrastructureName) &&
      packageInfrastructurePrimaryName.equals(configApp.packageInfrastructurePrimaryName) &&
      packageInfrastructureSecondaryName.equals(configApp.packageInfrastructureSecondaryName) &&
      projectFolder.equals(configApp.projectFolder) &&
      useAssertAsValidation.equals(configApp.useAssertAsValidation)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      baseName,
      basePackage,
      fluentMethod,
      packageDomainName,
      packageInfrastructureName,
      packageInfrastructurePrimaryName,
      packageInfrastructureSecondaryName,
      projectFolder,
      useAssertAsValidation
    );
  }

  @Override
  public String toString() {
    return (
      "ConfigApp{" +
      "baseName=" +
      baseName +
      ", basePackage=" +
      basePackage +
      ", fluentMethod=" +
      fluentMethod +
      ", packageDomainName=" +
      packageDomainName +
      ", packageInfrastructureName=" +
      packageInfrastructureName +
      ", packageInfrastructurePrimaryName=" +
      packageInfrastructurePrimaryName +
      ", packageInfrastructureSecondaryName=" +
      packageInfrastructureSecondaryName +
      ", projectFolder=" +
      projectFolder +
      ", useAssertAsValidation=" +
      useAssertAsValidation +
      '}'
    );
  }

  public static final class ConfigAppBuilder {

    private ConfigBaseName baseName = ConfigBaseName.DEFAULT;

    private ConfigBasePackage basePackage = ConfigBasePackage.DEFAULT;
    private ConfigFluentMethod fluentMethod = ConfigFluentMethodBuilder.DEFAULT;
    private ConfigPackageDomainName packageDomainName = ConfigPackageDomainName.DEFAULT;
    private ConfigPackageInfrastructureName packageInfrastructureName = ConfigPackageInfrastructureName.DEFAULT;
    private ConfigPackageInfrastructurePrimaryName packageInfrastructurePrimaryName = ConfigPackageInfrastructurePrimaryName.DEFAULT;
    private ConfigPackageInfrastructureSecondaryName packageInfrastructureSecondaryName = ConfigPackageInfrastructureSecondaryName.DEFAULT;
    private ConfigProjectFolder projectFolder = ConfigProjectFolder.newConfigProjectFolder();
    private ConfigUseAssertAsValidation useAssertAsValidation = ConfigUseAssertAsValidation.DEFAULT;

    private ConfigAppBuilder() {}

    public ConfigAppBuilder useAssertAsValidation(String useAssert) {
      this.useAssertAsValidation = ConfigUseAssertAsValidationBuilder.builderAssertAsValidation().useAssertAsValidation(useAssert).build();
      return this;
    }

    public ConfigAppBuilder baseName(String baseName) {
      this.baseName = new ConfigBaseName(baseName);
      return this;
    }

    public ConfigAppBuilder basePackage(String basePackage) {
      this.basePackage = new ConfigBasePackage(basePackage);
      return this;
    }

    public ConfigAppBuilder fluentMethod(String fluentMethod) {
      this.fluentMethod = ConfigFluentMethodBuilder.builderFluentMethod().fluentMethod(fluentMethod).build();
      return this;
    }

    public ConfigAppBuilder packageDomainName(String packageDomainName) {
      this.packageDomainName = new ConfigPackageDomainName(packageDomainName);
      return this;
    }

    public ConfigAppBuilder packageInfrastructureName(String packageInfrastructureName) {
      this.packageInfrastructureName = new ConfigPackageInfrastructureName(packageInfrastructureName);
      return this;
    }

    public ConfigAppBuilder packageInfrastructurePrimaryName(String packageInfrastructurePrimaryName) {
      this.packageInfrastructurePrimaryName = new ConfigPackageInfrastructurePrimaryName(packageInfrastructurePrimaryName);
      return this;
    }

    public ConfigAppBuilder packageInfrastructureSecondaryName(String packageInfrastructureSecondaryName) {
      this.packageInfrastructureSecondaryName = new ConfigPackageInfrastructureSecondaryName(packageInfrastructureSecondaryName);
      return this;
    }

    public ConfigAppBuilder projectFolder(String projectFolder) {
      this.projectFolder = new ConfigProjectFolder(projectFolder);
      return this;
    }

    public ConfigApp build() {
      ConfigApp configApp = new ConfigApp();

      configApp.baseName = this.baseName;
      configApp.basePackage = this.basePackage;
      configApp.fluentMethod = this.fluentMethod;
      configApp.packageDomainName = this.packageDomainName;
      configApp.packageInfrastructureName = this.packageInfrastructureName;
      configApp.packageInfrastructurePrimaryName = this.packageInfrastructurePrimaryName;
      configApp.packageInfrastructureSecondaryName = this.packageInfrastructureSecondaryName;
      configApp.projectFolder = this.projectFolder;
      configApp.useAssertAsValidation = this.useAssertAsValidation;

      return configApp;
    }
  }
}
