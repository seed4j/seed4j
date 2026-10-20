package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.javaproperties.ApplicationProperties;
import com.seed4j.module.domain.javaproperties.ApplicationProperty;
import com.seed4j.module.domain.javaproperties.SpringPropertyType;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;

@Service
class FileSystemYamlApplicationPropertiesCommandsHandler {

  private static final Map<SpringPropertyType, List<String>> PROPERTIES_PATHS = FileSystemSeed4JModulesRepository.buildPaths();

  public void handle(Indentation indentation, Seed4JProjectFolder projectFolder, ApplicationProperties properties) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("properties", properties);

    properties.get().forEach(setProperty(indentation, projectFolder));
  }

  private Consumer<ApplicationProperty> setProperty(Indentation indentation, Seed4JProjectFolder projectFolder) {
    return property ->
      new YamlFileApplicationPropertiesHandler(getPath(projectFolder, property), indentation).setValue(property.key(), property.value());
  }

  private static Path getPath(Seed4JProjectFolder projectFolder, ApplicationProperty property) {
    return Optional.ofNullable(PROPERTIES_PATHS.get(property.type()))
      .stream()
      .flatMap(Collection::stream)
      .map(toFilePath(projectFolder, property))
      .filter(Files::exists)
      .findFirst()
      .orElseGet(defaultPropertiesFile(projectFolder, property));
  }

  private static Function<String, Path> toFilePath(Seed4JProjectFolder projectFolder, ApplicationProperty property) {
    return folder -> projectFolder.filePath(folder + yamlFilename(property));
  }

  private static Supplier<Path> defaultPropertiesFile(Seed4JProjectFolder projectFolder, ApplicationProperty property) {
    return switch (property.type()) {
      case MAIN_PROPERTIES, MAIN_BOOTSTRAP_PROPERTIES -> () ->
        projectFolder.filePath(FileSystemSeed4JModulesRepository.DEFAULT_MAIN_FOLDER + yamlFilename(property));
      case TEST_PROPERTIES, TEST_BOOTSTRAP_PROPERTIES -> () ->
        projectFolder.filePath(FileSystemSeed4JModulesRepository.DEFAULT_TEST_FOLDER + yamlFilename(property));
    };
  }

  private static String yamlFilename(ApplicationProperty property) {
    return property.filename() + ".yml";
  }
}
