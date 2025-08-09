package com.seed4j.module.infrastructure.secondary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seed4j.module.domain.ProjectFiles;
import com.seed4j.module.domain.SeedPresetRepository;
import com.seed4j.module.domain.preset.Preset;
import com.seed4j.module.domain.preset.PresetName;
import com.seed4j.module.domain.preset.Presets;
import com.seed4j.shared.error.domain.GeneratorException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
class FileSystemSeedPresetRepository implements SeedPresetRepository {

  private static final String ROOT_FOLDER = "/";

  private final ObjectMapper json;
  private final ProjectFiles projectFiles;
  private final PresetName presetName;

  public FileSystemSeedPresetRepository(
    ObjectMapper json,
    ProjectFiles projectFiles,
    @Value("${seed4j.preset-folder:presets}") String presetFolderName
  ) {
    this.json = json;
    this.projectFiles = projectFiles;
    this.presetName = PresetName.from(presetFolderName);
  }

  @Override
  public Presets getPresets() {
    return new Presets(readAllPresets());
  }

  private List<Preset> readAllPresets() {
    return projectFiles
      .findRecursivelyInPath(presetFolderPath())
      .stream()
      .filter(jsonExtensionFiles())
      .map(readPresetFile())
      .map(PersistedPresets::toDomain)
      .flatMap(Collection::stream)
      .toList();
  }

  private String presetFolderPath() {
    return ROOT_FOLDER + presetName.name();
  }

  private static Predicate<String> jsonExtensionFiles() {
    return path -> path.endsWith(".json");
  }

  private Function<String, PersistedPresets> readPresetFile() {
    return path -> {
      try {
        return json.readValue(projectFiles.readBytes(path), PersistedPresets.class);
      } catch (IOException e) {
        throw GeneratorException.technicalError("Can't read preset file at " + path + ": " + e.getMessage(), e);
      }
    };
  }
}
