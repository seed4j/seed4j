package com.seed4j.module.infrastructure.secondary;

import com.seed4j.module.domain.javaproperties.ApplicationProperties;
import com.seed4j.module.domain.javaproperties.ApplicationProperty;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;

@Service
class FileSystemApplicationPropertiesCommandsHandler {

  public void handle(Seed4JProjectFolder projectFolder, ApplicationProperties properties) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("properties", properties);

    properties.get().forEach(setProperty(projectFolder));
  }

  private Consumer<ApplicationProperty> setProperty(Seed4JProjectFolder projectFolder) {
    return property ->
      new PropertiesFileApplicationPropertiesHandler(
        ApplicationPropertiesFilePaths.existingOrDefault(projectFolder, property.file(), ".properties")
      ).set(property.key(), property.value());
  }
}
