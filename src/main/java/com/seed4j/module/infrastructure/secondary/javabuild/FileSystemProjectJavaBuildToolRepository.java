package com.seed4j.module.infrastructure.secondary.javabuild;

import com.seed4j.module.domain.file.Seed4JDestination;
import com.seed4j.module.domain.file.Seed4JFileToMove;
import com.seed4j.module.domain.file.Seed4JModuleFile;
import com.seed4j.module.domain.file.Seed4JModuleFiles;
import com.seed4j.module.domain.javabuild.JavaBuildTool;
import com.seed4j.module.domain.javabuild.ProjectJavaBuildToolRepository;
import com.seed4j.module.domain.properties.Seed4JProjectFolder;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class FileSystemProjectJavaBuildToolRepository implements ProjectJavaBuildToolRepository {

  private static final String POM_XML = "pom.xml";
  private static final String BUILD_GRADLE_KTS = "build.gradle.kts";

  @Override
  public Optional<JavaBuildTool> detect(Seed4JProjectFolder projectFolder) {
    if (projectFolder.fileExists(POM_XML)) {
      return Optional.of(JavaBuildTool.MAVEN);
    }

    if (projectFolder.fileExists(BUILD_GRADLE_KTS)) {
      return Optional.of(JavaBuildTool.GRADLE);
    }

    return Optional.empty();
  }

  @Override
  public Optional<JavaBuildTool> detect(Seed4JModuleFiles moduleFiles) {
    if (hasMavenFile(moduleFiles)) {
      return Optional.of(JavaBuildTool.MAVEN);
    }

    if (hasGradleFile(moduleFiles)) {
      return Optional.of(JavaBuildTool.GRADLE);
    }

    return Optional.empty();
  }

  private static boolean hasMavenFile(Seed4JModuleFiles moduleFiles) {
    return hasBuildFiles(moduleFiles, POM_XML);
  }

  private static boolean hasGradleFile(Seed4JModuleFiles moduleFiles) {
    return hasBuildFiles(moduleFiles, BUILD_GRADLE_KTS);
  }

  private static boolean hasBuildFiles(Seed4JModuleFiles moduleFiles, String file) {
    return hasFileToAdd(moduleFiles, file) || hasFileToMove(moduleFiles, file);
  }

  private static boolean hasFileToMove(Seed4JModuleFiles files, String fileName) {
    return files.filesToMove().stream().map(Seed4JFileToMove::destination).map(Seed4JDestination::get).anyMatch(fileName::equals);
  }

  private static boolean hasFileToAdd(Seed4JModuleFiles files, String fileName) {
    return files.filesToAdd().stream().map(Seed4JModuleFile::destination).map(Seed4JDestination::get).anyMatch(fileName::equals);
  }
}
