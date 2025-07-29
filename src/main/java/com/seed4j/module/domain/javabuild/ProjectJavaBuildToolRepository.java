package com.seed4j.module.domain.javabuild;

import com.seed4j.module.domain.file.JHipsterModuleFiles;
import com.seed4j.module.domain.properties.JHipsterProjectFolder;
import java.util.Optional;

public interface ProjectJavaBuildToolRepository {
  Optional<JavaBuildTool> detect(JHipsterProjectFolder projectFolder);

  Optional<JavaBuildTool> detect(JHipsterModuleFiles moduleFiles);
}
