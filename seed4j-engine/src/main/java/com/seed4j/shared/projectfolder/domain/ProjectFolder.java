package com.seed4j.shared.projectfolder.domain;

public interface ProjectFolder {
  boolean isInvalid(String folderPath);

  String generatePath();
}
