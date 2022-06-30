package tech.jhipster.lite.generator.project.domain;

import java.util.Collection;
import java.util.List;
import tech.jhipster.lite.error.domain.Assert;

public interface ProjectRepository {
  void create(Project project);

  void add(Collection<ProjectFile> files);

  void template(Collection<ProjectFile> files);

  void rename(Project project, String source, String sourceFilename, String destinationFilename);

  String getComputedTemplate(Project project, String source, String sourceFilename);

  boolean containsRegexp(Project project, String source, String sourceFilename, String regexp);

  void replaceText(Project project, String source, String sourceFilename, String oldText, String newText);
  void replaceRegexp(Project project, String source, String sourceFilename, String regexpText, String newText);

  void write(Project project, String text, String destination, String destinationFilename);

  void writeAtTop(Project project, String text, String destination, String destinationFilename);

  void setExecutable(Project project, String source, String sourceFilename);

  void gitInit(Project project);
  void gitAddAndCommit(Project project, String message);
  void gitApplyPatch(Project project, String patchFilename);

  String zip(Project project);

  byte[] download(Project project);

  boolean isJHipsterLiteProject(String path);

  default void add(ProjectFile file) {
    Assert.notNull("file", file);

    add(List.of(file));
  }

  default void template(ProjectFile file) {
    Assert.notNull("file", file);

    template(List.of(file));
  }
}
