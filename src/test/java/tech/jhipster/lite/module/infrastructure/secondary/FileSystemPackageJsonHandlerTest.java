package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.emptyModuleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.emptyModuleContext;
import static tech.jhipster.lite.module.domain.npm.JHLiteNpmVersionSource.ANGULAR;
import static tech.jhipster.lite.module.domain.npm.JHLiteNpmVersionSource.COMMON;
import static tech.jhipster.lite.module.domain.packagejson.NodeModuleFormat.COMMONJS;
import static tech.jhipster.lite.module.domain.packagejson.NodeModuleFormat.MODULE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.npm.NpmPackageVersion;
import tech.jhipster.lite.module.domain.npm.NpmVersions;
import tech.jhipster.lite.module.domain.packagejson.JHipsterModulePackageJson;
import tech.jhipster.lite.module.domain.packagejson.JHipsterModulePackageJson.JHipsterModulePackageJsonBuilder;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.infrastructure.secondary.file.MustacheTemplateRenderer;

@UnitTest
@SuppressWarnings("java:S5976")
class FileSystemPackageJsonHandlerTest {

  private static final String PACKAGE_JSON = "package.json";

  private final NpmVersions npmVersions = mock(NpmVersions.class);

  private final FileSystemPackageJsonHandler packageJson = new FileSystemPackageJsonHandler(npmVersions, new MustacheTemplateRenderer());

  @Test
  void shouldHandleEmptyPackageJsonCommandsOnProjectWithoutPackageJson() {
    assertThatCode(() -> packageJson.handle(Indentation.DEFAULT, emptyFolder(), packageJson(), emptyModuleContext())
    ).doesNotThrowAnyException();
  }

  @Test
  void shouldNotHandleCommandsOnProjectWithoutPackageJson() {
    assertThatThrownBy(() ->
      packageJson.handle(
        Indentation.DEFAULT,
        emptyFolder(),
        packageJson(p -> p.addScript(scriptKey("key"), scriptCommand("value"))),
        emptyModuleContext()
      )
    ).isExactlyInstanceOf(MissingPackageJsonException.class);
  }

  private JHipsterProjectFolder emptyFolder() {
    return new JHipsterProjectFolder(TestFileUtils.tmpDirForTest());
  }

  @Test
  void shouldNotAddNotNeededBlock() {
    when(npmVersions.get("@playwright/test", COMMON.build())).thenReturn(new NpmPackageVersion("1.1.1"));

    JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/empty-node/package.json");

    packageJson.handle(
      Indentation.DEFAULT,
      folder,
      packageJson(p -> p.addDevDependency(packageName("@playwright/test"), COMMON)),
      emptyModuleContext()
    );

    assertThat(packageJsonContent(folder)).doesNotContain("scripts");
  }

  @Test
  void shouldNotGreedExistingBlocks() {
    JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/node/package.json");

    packageJson.handle(
      Indentation.DEFAULT,
      folder,
      packageJson(p -> p.addScript(scriptKey("@prettier/plugin-xml"), scriptCommand("test"))),
      emptyModuleContext()
    );

    assertPackageJsonContent(
      folder,
      """
        "scripts": {
          "@prettier/plugin-xml": "test",
          "build": "vue-tsc -p tsconfig-build.json --noEmit && vite build --emptyOutDir"
        },
      """
    );

    assertPackageJsonContent(
      folder,
      """
        "devDependencies": {
          "@prettier/plugin-xml": "2.1.0"
        },
      """
    );
  }

  @Nested
  class Type {

    @Test
    void shouldAddTypeToPackageJsonWithoutType() {
      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/empty-node/package.json");

      packageJson.handle(Indentation.DEFAULT, folder, packageJson(p -> p.type(MODULE)), emptyModuleContext());

      assertPackageJsonContent(
        folder,
        """
          "type": "module"
        }
        """
      );
    }

    @Test
    void shouldReplaceExistingType() {
      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/node/package.json");

      packageJson.handle(Indentation.DEFAULT, folder, packageJson(p -> p.type(COMMONJS)), emptyModuleContext());

      assertPackageJsonContent(
        folder,
        """
          "type": "commonjs"
        }
        """
      );
    }

    @Test
    void deprecatedApiShouldWork() {
      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/empty-node/package.json");

      packageJson.handle(Indentation.DEFAULT, folder, packageJson(p -> p.addType("module")), emptyModuleContext());

      assertPackageJsonContent(
        folder,
        """
          "type": "module"
        }
        """
      );
    }
  }

  @Nested
  class Scripts {

    @Test
    void shouldAddScriptToPackageJsonWithoutScriptSection() {
      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/empty-node/package.json");

      packageJson.handle(
        Indentation.DEFAULT,
        folder,
        packageJson(p -> p.addScript(scriptKey("key"), scriptCommand("value"))),
        emptyModuleContext()
      );

      assertPackageJsonContent(
        folder,
        """
          "scripts": {
            "key": "value"
          }
        }
        """
      );
    }

    @Test
    void shouldAddScriptsToPackageJsonWithScriptSection() {
      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/node/package.json");

      packageJson.handle(
        Indentation.DEFAULT,
        folder,
        packageJson(
          p -> p.addScript(scriptKey("key"), scriptCommand("value")),
          p -> p.addScript(scriptKey("key2"), scriptCommand("value2"))
        ),
        emptyModuleContext()
      );

      assertPackageJsonContent(
        folder,
        """
          "scripts": {
            "key": "value",
            "key2": "value2",
            "build": "vue-tsc -p tsconfig-build.json --noEmit && vite build --emptyOutDir"
        """
      );
    }

    @Test
    void shouldAddScriptsToPackageJsonWithScriptsTemplate() {
      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/node-template/package.json");

      packageJson.handle(
        Indentation.DEFAULT,
        folder,
        packageJson(p -> p.addScript(scriptKey("key"), scriptCommand("value"))),
        emptyModuleContext()
      );

      assertPackageJsonContent(
        folder,
        """
          "scripts": {
            "key": "value"
          },
        """
      );
    }

    @Test
    void shouldReplaceOnlyExistingScript() {
      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/node/package.json");

      packageJson.handle(
        Indentation.DEFAULT,
        folder,
        packageJson(p -> p.addScript(scriptKey("build"), scriptCommand("test"))),
        emptyModuleContext()
      );

      String result = packageJsonContent(folder);
      assertThat(result)
        .as(() -> "Can't find " + displayLineBreaks(result) + " in result " + displayLineBreaks(result))
        .contains(
          """
            "scripts": {
              "build": "test"
            },
          """
        );
    }

    @Test
    void shouldReplaceExistingScript() {
      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/node-multiple-scripts/package.json");

      packageJson.handle(
        Indentation.DEFAULT,
        folder,
        packageJson(p -> p.addScript(scriptKey("build"), scriptCommand("test"))),
        emptyModuleContext()
      );

      assertPackageJsonContent(
        folder,
        """
          "scripts": {
            "build": "test",
            "serve": "vue-tsc -p tsconfig-build.json --noEmit && vite build --emptyOutDir"
        """
      );
    }
  }

  @Nested
  class DevDependencies {

    @Test
    void shouldAddDevDependencyToPackageJsonWithoutDevDependencySection() {
      mockDevVersion();

      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/empty-node/package.json");

      packageJson.handle(
        Indentation.DEFAULT,
        folder,
        packageJson(p -> p.addDevDependency(packageName("@prettier/plugin-xmll"), COMMON)),
        emptyModuleContext()
      );

      assertPackageJsonContent(
        folder,
        """
          "devDependencies": {
            "@prettier/plugin-xmll": "1.1.1"
          }
        }
        """
      );
    }

    @Test
    void shouldAddDevDependencyToPackageJsonWithDevDependencySection() {
      mockDevVersion();

      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/node/package.json");

      packageJson.handle(
        Indentation.DEFAULT,
        folder,
        packageJson(p -> p.addDevDependency(packageName("@prettier/plugin-xmll"), COMMON)),
        emptyModuleContext()
      );

      assertPackageJsonContent(
        folder,
        """
          "devDependencies": {
            "@prettier/plugin-xmll": "1.1.1",
            "@prettier/plugin-xml": "2.1.0"
        """
      );
    }

    @Test
    void shouldAddDevDependencyToPackageJsonUsingVersionSourcePackage() {
      when(npmVersions.get("@angular/core", ANGULAR.build())).thenReturn(new NpmPackageVersion("1.1.1"));

      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/node/package.json");

      packageJson.handle(
        Indentation.DEFAULT,
        folder,
        packageJson(p -> p.addDevDependency(packageName("@angular/animations"), ANGULAR, packageName("@angular/core"))),
        emptyModuleContext()
      );

      assertPackageJsonContent(
        folder,
        """
          "devDependencies": {
            "@angular/animations": "1.1.1",
        """
      );
    }

    @Test
    void shouldReplaceExistingDevDependency() {
      mockDevVersion();

      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/node/package.json");

      packageJson.handle(
        Indentation.DEFAULT,
        folder,
        packageJson(p -> p.addDevDependency(packageName("@prettier/plugin-xml"), COMMON)),
        emptyModuleContext()
      );

      assertPackageJsonContent(
        folder,
        """
          "devDependencies": {
            "@prettier/plugin-xml": "1.1.1"
          },
        """
      );
    }

    @Test
    void shouldRemoveExistingDevDependency() {
      mockDevVersion();

      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/node/package.json");

      packageJson.handle(
        Indentation.DEFAULT,
        folder,
        packageJson(p -> p.removeDevDependency(packageName("@prettier/plugin-xml"))),
        emptyModuleContext()
      );

      assertPackageJsonContent(
        folder,
        """
          "devDependencies": {
              },
        """
      );
    }

    private void mockDevVersion() {
      when(npmVersions.get(anyString(), eq(COMMON.build()))).thenReturn(new NpmPackageVersion("1.1.1"));
    }
  }

  @Nested
  class Dependencies {

    @Test
    void shouldAddDependencyToPackageJsonWithoutDependencySection() {
      mockVersion();

      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/empty-node/package.json");

      packageJson.handle(
        Indentation.DEFAULT,
        folder,
        packageJson(p -> p.addDependency(packageName("@fortawesome/fontawesome-svg-core"), COMMON)),
        emptyModuleContext()
      );

      assertPackageJsonContent(
        folder,
        """
          "dependencies": {
            "@fortawesome/fontawesome-svg-core": "1.1.1"
          }
        }
        """
      );
    }

    @Test
    void shouldAddDependencyToPackageJsonWithDependencySection() {
      mockVersion();

      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/node/package.json");

      packageJson.handle(
        Indentation.DEFAULT,
        folder,
        packageJson(p -> p.addDependency(packageName("@fortawesome/fontawesome-svg-coree"), COMMON)),
        emptyModuleContext()
      );

      assertPackageJsonContent(
        folder,
        """
          "dependencies": {
            "@fortawesome/fontawesome-svg-coree": "1.1.1",
            "@fortawesome/fontawesome-svg-core": "6.1.1"
        """
      );
    }

    @Test
    void shouldAddDependencyToPackageJsonUsingVersionSourcePackage() {
      when(npmVersions.get("@angular/core", ANGULAR.build())).thenReturn(new NpmPackageVersion("1.1.1"));

      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/node/package.json");

      packageJson.handle(
        Indentation.DEFAULT,
        folder,
        packageJson(p -> p.addDependency(packageName("@angular/animations"), ANGULAR, packageName("@angular/core"))),
        emptyModuleContext()
      );

      assertPackageJsonContent(
        folder,
        """
          "dependencies": {
            "@angular/animations": "1.1.1",
        """
      );
    }

    @Test
    void shouldReplaceExistingDependency() {
      mockVersion();

      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/node/package.json");

      packageJson.handle(
        Indentation.DEFAULT,
        folder,
        packageJson(p -> p.addDependency(packageName("@fortawesome/fontawesome-svg-core"), COMMON)),
        emptyModuleContext()
      );

      assertPackageJsonContent(
        folder,
        """
          "dependencies": {
            "@fortawesome/fontawesome-svg-core": "1.1.1"
          },
        """
      );
    }

    @Test
    void shouldRemoveExistingDependency() {
      mockVersion();

      JHipsterProjectFolder folder = projectWithPackageJson("src/test/resources/projects/node/package.json");

      packageJson.handle(
        Indentation.DEFAULT,
        folder,
        packageJson(p -> p.removeDependency(packageName("@fortawesome/fontawesome-svg-core"))),
        emptyModuleContext()
      );

      assertPackageJsonContent(
        folder,
        """
          "dependencies": {
              },
        """
      );
    }

    private void mockVersion() {
      when(npmVersions.get(anyString(), eq(COMMON.build()))).thenReturn(new NpmPackageVersion("1.1.1"));
    }
  }

  @SafeVarargs
  private @NotNull JHipsterModulePackageJson packageJson(Consumer<JHipsterModulePackageJsonBuilder>... builderConfigurations) {
    JHipsterModulePackageJsonBuilder builder = emptyBuilder();
    Stream.of(builderConfigurations).forEach(configuration -> configuration.accept(builder));

    return builder.build();
  }

  private JHipsterModulePackageJsonBuilder emptyBuilder() {
    return JHipsterModulePackageJson.builder(emptyModuleBuilder());
  }

  private static JHipsterProjectFolder projectWithPackageJson(String packageJson) {
    String target = TestFileUtils.tmpDirForTest();

    try {
      Files.createDirectories(Paths.get(target));
      Files.copy(Paths.get(packageJson), Paths.get(target, PACKAGE_JSON));
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    return new JHipsterProjectFolder(target);
  }

  private static void assertPackageJsonContent(JHipsterProjectFolder folder, String expectedContent) {
    String packageJsonContent = packageJsonContent(folder);

    assertThat(packageJsonContent)
      .as(() -> "Can't find " + displayLineBreaks(expectedContent) + " in result " + displayLineBreaks(packageJsonContent))
      .contains(expectedContent);
  }

  private static String packageJsonContent(JHipsterProjectFolder folder) {
    try {
      return Files.readString(folder.filePath(PACKAGE_JSON)).replace("\r", "");
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  private static String displayLineBreaks(String value) {
    return value.replace("\r", "[R]").replace("\n", "[N]");
  }
}
