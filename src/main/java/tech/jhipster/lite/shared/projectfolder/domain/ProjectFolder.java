package tech.jhipster.lite.shared.projectfolder.domain;

public interface ProjectFolder {
  boolean isInvalid(String folderPath);

  String generatePath();
}
