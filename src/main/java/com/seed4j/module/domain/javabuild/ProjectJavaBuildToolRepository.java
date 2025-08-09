package com.seed4j.module.domain.javabuild;

import com.seed4j.module.domain.file.SeedModuleFiles;
import com.seed4j.module.domain.properties.SeedProjectFolder;
import java.util.Optional;

public interface ProjectJavaBuildToolRepository {
  Optional<JavaBuildTool> detect(SeedProjectFolder projectFolder);

  Optional<JavaBuildTool> detect(SeedModuleFiles moduleFiles);
}
