package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import java.util.List;
import tech.jhipster.lite.dsl.DslBaseVisitor;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;

public class DslConfigVisitor {

  private DslConfigVisitor() {}

  public static class ConfigVisitor extends DslBaseVisitor<ConfigApp> {

    @Override
    public ConfigApp visitConfig(DslParser.ConfigContext ctx) {
      ConfigApp.ConfigAppBuilder configBuilder = ConfigApp.configBuilder();
      if (ctx == null) {
        return configBuilder.build();
      }

      ConfigVisitorInternal configVisitorInternal = new ConfigVisitorInternal();
      return configBuilder
        .baseName(configVisitorInternal.visitBaseName(ctx.configbody().baseName()))
        .basePackage(configVisitorInternal.visitBasePackageName(ctx.configbody().basePackageName()))
        .fluentMethod(configVisitorInternal.visitUseFluentMethod(ctx.configbody().useFluentMethod()))
        .packageDomainName(configVisitorInternal.visitPackageDomainName(ctx.configbody().packageDomainName()))
        .packageInfrastructureName(configVisitorInternal.visitPackageInfrastructureName(ctx.configbody().packageInfrastructureName()))
        .packageInfrastructurePrimaryName(
          configVisitorInternal.visitPackageInfrastructurePrimaryName(ctx.configbody().packageInfrastructurePrimaryName())
        )
        .packageInfrastructureSecondaryName(
          configVisitorInternal.visitPackageInfrastructureSecondaryName(ctx.configbody().packageInfrastructureSecondaryName())
        )
        .projectFolder(configVisitorInternal.visitProjectFolder(ctx.configbody().projectFolder()))
        .build();
    }
  }

  public static class ConfigVisitorInternal extends DslBaseVisitor<String> {

    public String visitBaseName(List<DslParser.BaseNameContext> ctx) {
      if (ctx == null || ctx.isEmpty()) {
        return "";
      }

      return ctx.get(ctx.size() - 1).getChild(2).getText();
    }

    public String visitBasePackageName(List<DslParser.BasePackageNameContext> ctx) {
      if (ctx == null || ctx.isEmpty()) {
        return "";
      }
      return ctx.get(ctx.size() - 1).qualifiedName().getText();
    }

    public String visitUseFluentMethod(List<DslParser.UseFluentMethodContext> ctx) {
      if (ctx == null || ctx.isEmpty()) {
        return "";
      }
      return ctx.get(ctx.size() - 1).getChild(ctx.get(ctx.size() - 1).getChildCount() - 1).getText();
    }

    public String visitPackageDomainName(List<DslParser.PackageDomainNameContext> ctx) {
      if (ctx == null || ctx.isEmpty()) {
        return "";
      }
      return ctx.get(ctx.size() - 1).packageFormat().qualifiedName().getText();
    }

    public String visitPackageInfrastructureName(List<DslParser.PackageInfrastructureNameContext> ctx) {
      if (ctx == null || ctx.isEmpty()) {
        return "";
      }
      return ctx.get(ctx.size() - 1).packageFormat().qualifiedName().getText();
    }

    public String visitPackageInfrastructurePrimaryName(List<DslParser.PackageInfrastructurePrimaryNameContext> ctx) {
      if (ctx == null || ctx.isEmpty()) {
        return "";
      }
      return ctx.get(ctx.size() - 1).packageFormat().qualifiedName().getText();
    }

    public String visitPackageInfrastructureSecondaryName(List<DslParser.PackageInfrastructureSecondaryNameContext> ctx) {
      if (ctx == null || ctx.isEmpty()) {
        return "";
      }
      return ctx.get(ctx.size() - 1).packageFormat().qualifiedName().getText();
    }

    public String visitProjectFolder(List<DslParser.ProjectFolderContext> ctx) {
      if (ctx == null || ctx.isEmpty()) {
        return "";
      }
      return ctx.get(ctx.size() - 1).directoryPath().getText();
    }
  }
}
