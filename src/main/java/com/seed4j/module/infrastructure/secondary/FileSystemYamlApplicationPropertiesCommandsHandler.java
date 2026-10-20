package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.Indentation;
import com.seed4j.module.domain.javaproperties.ApplicationProperties;
import com.seed4j.module.domain.javaproperties.ApplicationProperty;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

@Service
class FileSystemYamlApplicationPropertiesCommandsHandler {

  public void handle(Indentation indentation, Seed4JProjectFolder projectFolder, ApplicationProperties properties) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("properties", properties);

    properties.get().forEach(setProperty(indentation, projectFolder));
  }

  private Consumer<ApplicationProperty> setProperty(Indentation indentation, Seed4JProjectFolder projectFolder) {
    return property ->
      new YamlFileApplicationPropertiesHandler(
        ApplicationPropertiesFilePaths.existingOrDefault(projectFolder, property.file(), ".yml"),
        indentation
      ).setValue(property.key(), property.value());
  }
}
