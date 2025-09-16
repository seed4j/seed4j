package com.seed4j.module.domain.javabuild;

import com.seed4j.module.domain.file.Seed4JModuleFiles;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import java.util.Optional;

public interface ProjectJavaBuildToolRepository {
  Optional<JavaBuildTool> detect(Seed4JProjectFolder projectFolder);

  Optional<JavaBuildTool> detect(Seed4JModuleFiles moduleFiles);
}
