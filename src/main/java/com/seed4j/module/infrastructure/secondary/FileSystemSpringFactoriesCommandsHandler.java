package com.seed4j.module.infrastructure.secondary;

import static com.seed4j.module.infrastructure.secondary.FileSystemJHipsterModulesRepository.*;

import com.seed4j.module.domain.javaproperties.SpringFactories;
import com.seed4j.module.domain.javaproperties.SpringFactory;
import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import com.seed4j.shared.error.domain.Assert;
import java.nio.file.Path;
import java.util.function.Consumer;

class FileSystemSpringFactoriesCommandsHandler {

  public void handle(JHipsterProjectFolder projectFolder, SpringFactories factories) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("factories", factories);

    factories.get().forEach(setProperty(projectFolder));
  }

  private Consumer<SpringFactory> setProperty(JHipsterProjectFolder projectFolder) {
    return property -> new PropertiesFileSpringFactoriesHandler(getPath(projectFolder, property)).append(property.key(), property.value());
  }

  private static Path getPath(JHipsterProjectFolder projectFolder, SpringFactory factory) {
    return switch (factory.type()) {
      case TEST_FACTORIES -> projectFolder.filePath(TEST_META_INF_FOLDER + "spring.factories");
    };
  }
}
