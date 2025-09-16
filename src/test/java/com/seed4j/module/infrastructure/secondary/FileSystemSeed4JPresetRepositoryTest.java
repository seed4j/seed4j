package com.seed4j.module.infrastructure.secondary;

import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seed4j.JsonHelper;
import com.seed4j.UnitTest;
import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.module.domain.Seed4JModuleSlug;
import com.seed4j.module.domain.Seed4JModuleSlugs;
import com.seed4j.module.domain.preset.Preset;
import com.seed4j.module.domain.preset.PresetName;
import com.seed4j.module.domain.preset.Presets;
import com.seed4j.shared.error.domain.GeneratorException;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class FileSystemSeed4JPresetRepositoryTest {

  private static final String DEFAULT_PRESET_FOLDER = "presets";

  private static final String PRESET_JSON_TEMPLATE = """
    {
      "presets": [
        {
          "name": "%s",
          "modules": [
            %s
          ]
        }
      ]
    }
    """;
  private static final String PRESET_JSON_MULTIPLE_TEMPLATE = """
    {
      "presets": [
        %s
      ]
    }
    """;
  private static final String PRESET_ENTRY_TEMPLATE = """
    {
      "name": "%s",
      "modules": [
        %s
      ]
    }
    """;

  @Test
  void shouldHandleDeserializationErrors() throws IOException {
    ObjectMapper json = mock(ObjectMapper.class);
    when(json.readValue(any(byte[].class), eq(PersistedPresets.class))).thenThrow(IOException.class);
    FileSystemSeed4JPresetRepository fileSystemSeed4JPresetRepository = new FileSystemSeed4JPresetRepository(
      json,
      mockProjectFilesWithValidPresetJson(),
      DEFAULT_PRESET_FOLDER
    );

    assertThatThrownBy(fileSystemSeed4JPresetRepository::getPresets).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotReturnPresetFromUnknownFolder() {
    ProjectFiles projectFiles = mock(ProjectFiles.class);
    lenient().when(projectFiles.findRecursivelyInPath("/%s".formatted(DEFAULT_PRESET_FOLDER))).thenThrow(GeneratorException.class);
    FileSystemSeed4JPresetRepository fileSystemSeed4JPresetRepository = new FileSystemSeed4JPresetRepository(
      JsonHelper.jsonMapper(),
      projectFiles,
      DEFAULT_PRESET_FOLDER
    );

    assertThatThrownBy(fileSystemSeed4JPresetRepository::getPresets).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotReturnPresetFromUnknownFile() {
    ProjectFiles projectFiles = mockProjectFilesWithValidPresetJson();
    lenient().when(projectFiles.readBytes("/presets/preset-maven.json")).thenThrow(GeneratorException.class);
    FileSystemSeed4JPresetRepository fileSystemSeed4JPresetRepository = new FileSystemSeed4JPresetRepository(
      JsonHelper.jsonMapper(),
      projectFiles,
      DEFAULT_PRESET_FOLDER
    );

    assertThatThrownBy(fileSystemSeed4JPresetRepository::getPresets).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldGetExistingPresetInAlphabeticalOrder() {
    FileSystemSeed4JPresetRepository fileSystemSeed4JPresetRepository = new FileSystemSeed4JPresetRepository(
      JsonHelper.jsonMapper(),
      mockProjectFilesWithValidPresetJson(),
      DEFAULT_PRESET_FOLDER
    );

    Presets presets = fileSystemSeed4JPresetRepository.getPresets();

    assertThat(presets.presets()).containsExactly(
      expectedPresetAngularCore(),
      expectedPresetJavaLibraryWithGradle(),
      expectedPresetJavaLibraryWithMaven(),
      expectedPresetVueCore(),
      expectedPresetWebappVueSpringBoot()
    );
  }

  @Test
  void shouldIgnorePresetFilesWithNonJsonExtension() {
    FileSystemSeed4JPresetRepository fileSystemSeed4JPresetRepository = new FileSystemSeed4JPresetRepository(
      JsonHelper.jsonMapper(),
      mockProjectFilesWithValidPresetJson(),
      DEFAULT_PRESET_FOLDER
    );

    Presets presets = fileSystemSeed4JPresetRepository.getPresets();

    assertThat(presets.presets()).isNotEmpty();
    assertThat(presets.presets()).doesNotContain(expectedPresetReactCore());
  }

  private static ProjectFiles mockProjectFilesWithValidPresetJson() {
    ProjectFiles projectFiles = mock(ProjectFiles.class);

    String mavenPreset = createPresetJson(
      "Java Library with Maven",
      """
      "init",
      "application-service-hexagonal-architecture-documentation",
      "maven-java",
      "maven-wrapper",
      "prettier",
      "java-base",
      "checkstyle",
      "jacoco-with-min-coverage-check"
      """
    );

    String gradlePreset = createPresetJson(
      "Java Library with Gradle",
      """
      "init",
      "application-service-hexagonal-architecture-documentation",
      "gradle-java",
      "gradle-wrapper",
      "prettier",
      "java-base",
      "checkstyle",
      "jacoco-with-min-coverage-check"
      """
    );

    String typescriptPreset = createPresetJson(
      "Vue Core",
      """
      "init",
      "application-service-hexagonal-architecture-documentation",
      "prettier",
      "typescript",
      "vue-core"
      """
    );

    String personalPresets = createMultiplePresetsJson(
      createPresetEntry(
        "Webapp: Vue + Spring Boot",
        """
        "init",
        "application-service-hexagonal-architecture-documentation",
        "maven-java",
        "maven-wrapper",
        "prettier",
        "vue-core",
        "java-base",
        "checkstyle",
        "jacoco-with-min-coverage-check",
        "spring-boot",
        "spring-boot-mvc-empty",
        "logs-spy",
        "spring-boot-tomcat",
        "frontend-maven-plugin"
        """
      ),
      createPresetEntry(
        "Angular Core",
        """
        "init",
        "application-service-hexagonal-architecture-documentation",
        "prettier",
        "typescript",
        "angular-core"
        """
      )
    );

    String reactCorePreset = createPresetJson(
      "React Core",
      """
      "init",
      "application-service-hexagonal-architecture-documentation",
      "prettier",
      "typescript",
      "react-core"
      """
    );

    lenient().when(projectFiles.readBytes("/presets/preset-maven.json")).thenReturn(mavenPreset.getBytes(UTF_8));
    lenient().when(projectFiles.readBytes("/presets/preset-gradle.json")).thenReturn(gradlePreset.getBytes(UTF_8));
    lenient().when(projectFiles.readBytes("/presets/preset-typescript.json")).thenReturn(typescriptPreset.getBytes(UTF_8));
    lenient().when(projectFiles.readBytes("/presets/personal/preset-personal.json")).thenReturn(personalPresets.getBytes(UTF_8));
    lenient().when(projectFiles.readBytes("/presets/personal/react-core.xml")).thenReturn(reactCorePreset.getBytes(UTF_8));

    lenient()
      .when(projectFiles.findRecursivelyInPath("/presets"))
      .thenReturn(
        List.of(
          "/presets/preset-maven.json",
          "/presets/preset-gradle.json",
          "/presets/preset-typescript.json",
          "/presets/personal/preset-personal.json",
          "/presets/personal/react-core.xml"
        )
      );

    return projectFiles;
  }

  private static String createPresetJson(String name, String modules) {
    return PRESET_JSON_TEMPLATE.formatted(name, modules);
  }

  private static String createPresetEntry(String name, String modules) {
    return PRESET_ENTRY_TEMPLATE.formatted(name, modules);
  }

  private static String createMultiplePresetsJson(String... presetEntries) {
    return PRESET_JSON_MULTIPLE_TEMPLATE.formatted(String.join(",\n", presetEntries));
  }

  private static Preset expectedPresetJavaLibraryWithMaven() {
    return new Preset(
      new PresetName("Java Library with Maven"),
      new Seed4JModuleSlugs(
        List.of(
          new Seed4JModuleSlug("init"),
          new Seed4JModuleSlug("application-service-hexagonal-architecture-documentation"),
          new Seed4JModuleSlug("maven-java"),
          new Seed4JModuleSlug("maven-wrapper"),
          new Seed4JModuleSlug("prettier"),
          new Seed4JModuleSlug("java-base"),
          new Seed4JModuleSlug("checkstyle"),
          new Seed4JModuleSlug("jacoco-with-min-coverage-check")
        )
      )
    );
  }

  private static Preset expectedPresetJavaLibraryWithGradle() {
    return new Preset(
      new PresetName("Java Library with Gradle"),
      new Seed4JModuleSlugs(
        List.of(
          new Seed4JModuleSlug("init"),
          new Seed4JModuleSlug("application-service-hexagonal-architecture-documentation"),
          new Seed4JModuleSlug("gradle-java"),
          new Seed4JModuleSlug("gradle-wrapper"),
          new Seed4JModuleSlug("prettier"),
          new Seed4JModuleSlug("java-base"),
          new Seed4JModuleSlug("checkstyle"),
          new Seed4JModuleSlug("jacoco-with-min-coverage-check")
        )
      )
    );
  }

  private static Preset expectedPresetVueCore() {
    return new Preset(
      new PresetName("Vue Core"),
      new Seed4JModuleSlugs(
        List.of(
          new Seed4JModuleSlug("init"),
          new Seed4JModuleSlug("application-service-hexagonal-architecture-documentation"),
          new Seed4JModuleSlug("prettier"),
          new Seed4JModuleSlug("typescript"),
          new Seed4JModuleSlug("vue-core")
        )
      )
    );
  }

  private static Preset expectedPresetWebappVueSpringBoot() {
    return new Preset(
      new PresetName("Webapp: Vue + Spring Boot"),
      new Seed4JModuleSlugs(
        List.of(
          new Seed4JModuleSlug("init"),
          new Seed4JModuleSlug("application-service-hexagonal-architecture-documentation"),
          new Seed4JModuleSlug("maven-java"),
          new Seed4JModuleSlug("maven-wrapper"),
          new Seed4JModuleSlug("prettier"),
          new Seed4JModuleSlug("vue-core"),
          new Seed4JModuleSlug("java-base"),
          new Seed4JModuleSlug("checkstyle"),
          new Seed4JModuleSlug("jacoco-with-min-coverage-check"),
          new Seed4JModuleSlug("spring-boot"),
          new Seed4JModuleSlug("spring-boot-mvc-empty"),
          new Seed4JModuleSlug("logs-spy"),
          new Seed4JModuleSlug("spring-boot-tomcat"),
          new Seed4JModuleSlug("frontend-maven-plugin")
        )
      )
    );
  }

  private static Preset expectedPresetAngularCore() {
    return new Preset(
      new PresetName("Angular Core"),
      new Seed4JModuleSlugs(
        List.of(
          new Seed4JModuleSlug("init"),
          new Seed4JModuleSlug("application-service-hexagonal-architecture-documentation"),
          new Seed4JModuleSlug("prettier"),
          new Seed4JModuleSlug("typescript"),
          new Seed4JModuleSlug("angular-core")
        )
      )
    );
  }

  private static Preset expectedPresetReactCore() {
    return new Preset(
      new PresetName("React Core"),
      new Seed4JModuleSlugs(
        List.of(
          new Seed4JModuleSlug("init"),
          new Seed4JModuleSlug("application-service-hexagonal-architecture-documentation"),
          new Seed4JModuleSlug("prettier"),
          new Seed4JModuleSlug("typescript"),
          new Seed4JModuleSlug("react-core")
        )
      )
    );
  }
}
