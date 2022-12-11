package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.parser.domain.config.*;

@UnitTest
class DslConfigVisitorTest {

  @Test
  void shouldBuildConfigApp() {
    DslParser.File_Context fileCtx = AntlrUtils.getFileContextFromText(
      """
                        config {
                            useFluentMethod=no
                            baseName=baseNameOverride
                            basePackageName=basePackageNameOverride
                            packageInfrastructureName=packageInfrastructureNameOverride
                            packageInfrastructurePrimaryName=packageInfrastructurePrimaryNameOverride
                            packageInfrastructureSecondaryName=packageInfrastructureSecondaryNameOverride
                            packageDomainName=packageDomainNameOverride
                            projectFolder=/tmp/test
                        }
                        """
    );

    DslConfigVisitor.ConfigVisitor configVisitor = new DslConfigVisitor.ConfigVisitor();

    ConfigApp configApp = configVisitor.visitConfig(fileCtx.config());
    assertNotNull(configVisitor.visitConfig(fileCtx.config()));
    assertEquals(configApp.getBaseName().get(), new ConfigBaseName("baseNameOverride").get());
  }

  @Test
  void shouldContainsGoodValue() {
    DslParser.File_Context fileCtx = AntlrUtils.getFileContextFromText(
      """
                        config {
                            useFluentMethod=no
                            baseName=baseNameOverride
                            basePackageName=basePackageNameOverride
                            packageInfrastructureName=packageInfrastructureNameOverride
                            packageInfrastructurePrimaryName=packageInfrastructurePrimaryNameOverride
                            packageInfrastructureSecondaryName=packageInfrastructureSecondaryNameOverride
                            packageDomainName=packageDomainNameOverride
                            projectFolder=/tmp/test
                        }
                        """
    ); //include EOF

    DslConfigVisitor.ConfigVisitor configVisitor = new DslConfigVisitor.ConfigVisitor();

    ConfigApp configApp = configVisitor.visitConfig(fileCtx.config());
    assertNotNull(configVisitor.visitConfig(fileCtx.config()));
    assertEquals(ConfigFluentMethodBuilder.builderFluentMethod().fluentMethod("false").build(), configApp.getFluentMethod());
    assertEquals(new ConfigBaseName("baseNameOverride"), configApp.getBaseName());
    assertEquals(new ConfigBasePackage("basePackageNameOverride"), configApp.getBasePackage());
    assertEquals(new ConfigPackageInfrastructureName("packageInfrastructureNameOverride"), configApp.getPackageInfrastructureName());
    assertEquals(
      new ConfigPackageInfrastructurePrimaryName("packageInfrastructurePrimaryNameOverride"),
      configApp.getPackageInfrastructurePrimaryName()
    );
    assertEquals(
      new ConfigPackageInfrastructureSecondaryName("packageInfrastructureSecondaryNameOverride"),
      configApp.getPackageInfrastructureSecondaryName()
    );
    assertEquals(new ConfigPackageDomainName("packageDomainNameOverride"), configApp.getPackageDomainName());
    assertEquals(new ConfigProjectFolder("/tmp/test"), configApp.getProjectFolder());
  }

  @Test
  void shouldNotBuildConfigAppWithUnknownValidOption() {
    assertThrows(
      RecognitionException.class,
      () -> {
        AntlrUtils.getFileContextFromText(
          """
                            config {
                                unknowconfig=tes
                            }
                            """
        );
      },
      "RecognitionException was expected"
    );
  }

  @Test
  void shouldNotBuildConfigAppWithUnknownValidOptions() {
    assertThrows(
      RecognitionException.class,
      () -> {
        AntlrUtils.getFileContextFromText(
          """
                                    config {
                                        useFluentMethod=no
                                        baseName=Override
                                        basePackageName=Override
                                        packageInfrastructureName=infrastructureOverride
                                        packageInfrastructurePrimaryName=primaryOverride
                                        packageInfrastructureSecondaryName=secondaryOverride
                                        packageDomainName=domainOverride
                                        unknowconfig=tes
                                    }
                                    """
        );
      },
      "RecognitionException was expected"
    );
  }

  @Test
  void shouldBuildDefaultConfig() {
    DslParser.File_Context fileCtx = AntlrUtils.getFileContextFromText("config {}");
    DslConfigVisitor.ConfigVisitor configVisitor = new DslConfigVisitor.ConfigVisitor();
    ConfigApp configApp = configVisitor.visitConfig(fileCtx.config());

    testDefaultConfig(configApp);
  }

  private static void testDefaultConfig(ConfigApp configApp) {
    assertEquals(ConfigBaseName.DEFAULT, configApp.getBaseName());
    assertEquals(ConfigBasePackage.DEFAULT, configApp.getBasePackage());
    assertEquals(ConfigFluentMethodBuilder.DEFAULT, configApp.getFluentMethod());
    assertEquals(ConfigPackageDomainName.DEFAULT, configApp.getPackageDomainName());
    assertEquals(ConfigPackageInfrastructureName.DEFAULT, configApp.getPackageInfrastructureName());
    assertEquals(ConfigPackageInfrastructurePrimaryName.DEFAULT, configApp.getPackageInfrastructurePrimaryName());
    assertEquals(ConfigPackageInfrastructureSecondaryName.DEFAULT, configApp.getPackageInfrastructureSecondaryName());
    assertEquals(ConfigUseAssertAsValidationBuilder.DEFAULT, configApp.getUseAssertAsValidation());
    assertTrue(configApp.getProjectFolder().get().contains("/tmp"));
  }

  @Test
  void shouldBuildDefaultConfigEmpty() {
    DslParser.File_Context fileCtx = AntlrUtils.getFileContextFromText("");
    DslConfigVisitor.ConfigVisitor configVisitor = new DslConfigVisitor.ConfigVisitor();
    ConfigApp configApp = configVisitor.visitConfig(fileCtx.config());
    testDefaultConfig(configApp);
  }

  @Test
  void shouldReturnEmptyString() {
    DslParser.ConfigbodyContext ctx = AntlrUtils.getConfigBodyFromText("{}");

    DslConfigVisitor.ConfigVisitorInternal configVisitorInternal = new DslConfigVisitor.ConfigVisitorInternal();
    assertEquals("", configVisitorInternal.visitBaseName(ctx.baseName()));
    assertEquals("", configVisitorInternal.visitBasePackageName(ctx.basePackageName()));
    assertEquals("", configVisitorInternal.visitUseFluentMethod(ctx.useFluentMethod()));
    assertEquals("", configVisitorInternal.visitPackageDomainName(ctx.packageDomainName()));
    assertEquals("", configVisitorInternal.visitPackageInfrastructureName(ctx.packageInfrastructureName()));
    assertEquals("", configVisitorInternal.visitPackageInfrastructurePrimaryName(ctx.packageInfrastructurePrimaryName()));
    assertEquals("", configVisitorInternal.visitPackageInfrastructureSecondaryName(ctx.packageInfrastructureSecondaryName()));
    assertEquals("", configVisitorInternal.visitProjectFolder(ctx.projectFolder()));
    assertEquals("", configVisitorInternal.visitUseAssertAsValidation(ctx.useAssertAsValidation()));
  }

  @Test
  void shouldReturnEmptyStringWithNullCtx() {
    DslParser.ConfigbodyContext ctx = AntlrUtils.getConfigBodyFromText("{}");

    DslConfigVisitor.ConfigVisitorInternal configVisitorInternal = new DslConfigVisitor.ConfigVisitorInternal();
    assertEquals("", configVisitorInternal.visitBaseName((List<DslParser.BaseNameContext>) null));
    assertEquals("", configVisitorInternal.visitBasePackageName((List<DslParser.BasePackageNameContext>) null));
    assertEquals("", configVisitorInternal.visitUseFluentMethod((List<DslParser.UseFluentMethodContext>) null));
    assertEquals("", configVisitorInternal.visitPackageDomainName((List<DslParser.PackageDomainNameContext>) null));
    assertEquals("", configVisitorInternal.visitPackageInfrastructureName((List<DslParser.PackageInfrastructureNameContext>) null));
    assertEquals(
      "",
      configVisitorInternal.visitPackageInfrastructurePrimaryName((List<DslParser.PackageInfrastructurePrimaryNameContext>) null)
    );
    assertEquals(
      "",
      configVisitorInternal.visitPackageInfrastructureSecondaryName((List<DslParser.PackageInfrastructureSecondaryNameContext>) null)
    );
    assertEquals("", configVisitorInternal.visitProjectFolder((List<DslParser.ProjectFolderContext>) null));
    assertEquals("", configVisitorInternal.visitUseAssertAsValidation((List<DslParser.UseAssertAsValidationContext>) null));
  }
}
