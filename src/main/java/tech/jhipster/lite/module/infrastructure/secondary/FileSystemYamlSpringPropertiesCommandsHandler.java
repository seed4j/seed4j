package tech.jhipster.lite.module.infrastructure.secondary;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.javaproperties.SpringProperties;
import tech.jhipster.lite.module.domain.javaproperties.SpringProperty;
import tech.jhipster.lite.module.domain.javaproperties.SpringPropertyType;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.shared.error.domain.Assert;

@Service
class FileSystemYamlSpringPropertiesCommandsHandler {

  private static final Map<SpringPropertyType, List<String>> PROPERTIES_PATHS = FileSystemJHipsterModulesRepository.buildPaths();

  public void handle(Indentation indentation, JHipsterProjectFolder projectFolder, SpringProperties properties) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("properties", properties);

    properties.get().forEach(setProperty(indentation, projectFolder));
  }

  private Consumer<SpringProperty> setProperty(Indentation indentation, JHipsterProjectFolder projectFolder) {
    return property ->
      new YamlFileSpringPropertiesHandler(getPath(projectFolder, property), indentation).setValue(property.key(), property.value());
  }

  private static Path getPath(JHipsterProjectFolder projectFolder, SpringProperty property) {
    return PROPERTIES_PATHS
      .get(property.type())
      .stream()
      .map(toFilePath(projectFolder, property))
      .filter(Files::exists)
      .findFirst()
      .orElseGet(defaultPropertiesFile(projectFolder, property));
  }

  private static Function<String, Path> toFilePath(JHipsterProjectFolder projectFolder, SpringProperty property) {
    return folder -> projectFolder.filePath(folder + yamlFilename(property));
  }

  private static Supplier<Path> defaultPropertiesFile(JHipsterProjectFolder projectFolder, SpringProperty property) {
    return switch (property.type()) {
      case MAIN_PROPERTIES, MAIN_BOOTSTRAP_PROPERTIES -> () ->
        projectFolder.filePath(FileSystemJHipsterModulesRepository.DEFAULT_MAIN_FOLDER + yamlFilename(property));
      case TEST_PROPERTIES, TEST_BOOTSTRAP_PROPERTIES -> () ->
        projectFolder.filePath(FileSystemJHipsterModulesRepository.DEFAULT_TEST_FOLDER + yamlFilename(property));
    };
  }

  private static String yamlFilename(SpringProperty property) {
    return property.filename() + ".yml";
  }
}
