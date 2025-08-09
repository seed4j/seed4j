package com.seed4j.module.infrastructure.secondary.javabuild;

import com.seed4j.module.domain.file.SeedDestination;
import com.seed4j.module.domain.file.SeedFileToMove;
import com.seed4j.module.domain.file.SeedModuleFile;
import com.seed4j.module.domain.file.SeedModuleFiles;
import com.seed4j.module.domain.javabuild.JavaBuildTool;
import com.seed4j.module.domain.javabuild.ProjectJavaBuildToolRepository;
import com.seed4j.module.domain.properties.SeedProjectFolder;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class FileSystemProjectJavaBuildToolRepository implements ProjectJavaBuildToolRepository {

  private static final String POM_XML = "pom.xml";
  private static final String BUILD_GRADLE_KTS = "build.gradle.kts";

  @Override
  public Optional<JavaBuildTool> detect(SeedProjectFolder projectFolder) {
    if (projectFolder.fileExists(POM_XML)) {
      return Optional.of(JavaBuildTool.MAVEN);
    }

    if (projectFolder.fileExists(BUILD_GRADLE_KTS)) {
      return Optional.of(JavaBuildTool.GRADLE);
    }

    return Optional.empty();
  }

  @Override
  public Optional<JavaBuildTool> detect(SeedModuleFiles moduleFiles) {
    if (hasMavenFile(moduleFiles)) {
      return Optional.of(JavaBuildTool.MAVEN);
    }

    if (hasGradleFile(moduleFiles)) {
      return Optional.of(JavaBuildTool.GRADLE);
    }

    return Optional.empty();
  }

  private static boolean hasMavenFile(SeedModuleFiles moduleFiles) {
    return hasBuildFiles(moduleFiles, POM_XML);
  }

  private static boolean hasGradleFile(SeedModuleFiles moduleFiles) {
    return hasBuildFiles(moduleFiles, BUILD_GRADLE_KTS);
  }

  private static boolean hasBuildFiles(SeedModuleFiles moduleFiles, String file) {
    return hasFileToAdd(moduleFiles, file) || hasFileToMove(moduleFiles, file);
  }

  private static boolean hasFileToMove(SeedModuleFiles files, String fileName) {
    return files.filesToMove().stream().map(SeedFileToMove::destination).map(SeedDestination::get).anyMatch(fileName::equals);
  }

  private static boolean hasFileToAdd(SeedModuleFiles files, String fileName) {
    return files.filesToAdd().stream().map(SeedModuleFile::destination).map(SeedDestination::get).anyMatch(fileName::equals);
  }
}
