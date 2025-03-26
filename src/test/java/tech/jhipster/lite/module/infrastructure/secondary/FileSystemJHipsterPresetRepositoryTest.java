package tech.jhipster.lite.module.infrastructure.secondary;

import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.JsonHelper;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModuleSlug;
import tech.jhipster.lite.module.domain.JHipsterModuleSlugs;
import tech.jhipster.lite.module.domain.ProjectFiles;
import tech.jhipster.lite.module.domain.preset.Preset;
import tech.jhipster.lite.module.domain.preset.PresetName;
import tech.jhipster.lite.shared.error.domain.GeneratorException;

@UnitTest
class FileSystemJHipsterPresetRepositoryTest {

  private static final String DEFAULT_PRESET_FILE = "preset.json";

  @Test
  void shouldHandleDeserializationErrors() throws IOException {
    ObjectMapper json = mock(ObjectMapper.class);
    when(json.readValue(any(byte[].class), eq(PersistedPresets.class))).thenThrow(IOException.class);
    FileSystemJHipsterPresetRepository fileSystemJHipsterPresetRepository = new FileSystemJHipsterPresetRepository(
      json,
      mockProjectFilesWithValidPresetJson(),
      DEFAULT_PRESET_FILE
    );

    assertThatThrownBy(fileSystemJHipsterPresetRepository::getPresets).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotReturnPresetFromUnknownFile() {
    ProjectFiles projectFiles = mock(ProjectFiles.class);
    lenient().when(projectFiles.readBytes("/preset.json")).thenThrow(GeneratorException.class);
    FileSystemJHipsterPresetRepository fileSystemJHipsterPresetRepository = new FileSystemJHipsterPresetRepository(
      JsonHelper.jsonMapper(),
      projectFiles,
      DEFAULT_PRESET_FILE
    );

    assertThatThrownBy(fileSystemJHipsterPresetRepository::getPresets).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldGetExistingPreset() {
    FileSystemJHipsterPresetRepository fileSystemJHipsterPresetRepository = new FileSystemJHipsterPresetRepository(
      JsonHelper.jsonMapper(),
      mockProjectFilesWithValidPresetJson(),
      DEFAULT_PRESET_FILE
    );

    Collection<Preset> presets = fileSystemJHipsterPresetRepository.getPresets();

    assertThat(presets).containsExactly(expectedPreset());
  }

  private static ProjectFiles mockProjectFilesWithValidPresetJson() {
    ProjectFiles projectFiles = mock(ProjectFiles.class);

    String validPresetJson =
      """
      {
        "presets": [
          {
            "name": "angular + spring boot",
            "modules": [
              "init",
              "application-service-hexagonal-architecture-documentation",
              "maven-java",
              "prettier",
              "angular-core",
              "java-base",
              "maven-wrapper",
              "spring-boot",
              "spring-boot-mvc-empty",
              "logs-spy",
              "spring-boot-tomcat"
            ]
          }
        ]
      }
      """;
    lenient().when(projectFiles.readBytes("/preset.json")).thenReturn(validPresetJson.getBytes(UTF_8));

    return projectFiles;
  }

  private static Preset expectedPreset() {
    return new Preset(
      new PresetName("angular + spring boot"),
      new JHipsterModuleSlugs(
        List.of(
          new JHipsterModuleSlug("init"),
          new JHipsterModuleSlug("application-service-hexagonal-architecture-documentation"),
          new JHipsterModuleSlug("maven-java"),
          new JHipsterModuleSlug("prettier"),
          new JHipsterModuleSlug("angular-core"),
          new JHipsterModuleSlug("java-base"),
          new JHipsterModuleSlug("maven-wrapper"),
          new JHipsterModuleSlug("spring-boot"),
          new JHipsterModuleSlug("spring-boot-mvc-empty"),
          new JHipsterModuleSlug("logs-spy"),
          new JHipsterModuleSlug("spring-boot-tomcat")
        )
      )
    );
  }
}
