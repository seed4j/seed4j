package tech.jhipster.lite.generator.project.domain;

public interface ProjectRepository {
  void create(Project project);

  void add(Project project, String source, String sourceFilename);
  void add(Project project, String source, String sourceFilename, String destination);
  void add(Project project, String source, String sourceFilename, String destination, String destinationFilename);

  void template(Project project, String source, String sourceFilename);
  void template(Project project, String source, String sourceFilename, String destination);
  void template(Project project, String source, String sourceFilename, String destination, String destinationFilename);

  String getComputedTemplate(Project project, String source, String sourceFilename);

  boolean containsRegexp(Project project, String source, String sourceFilename, String regexp);
  void replaceText(Project project, String source, String sourceFilename, String oldText, String newText);
  void replaceRegexp(Project project, String source, String sourceFilename, String regexpText, String newText);
  void write(Project project, String text, String destination, String destinationFilename);

  void setExecutable(Project project, String source, String sourceFilename);

  void gitInit(Project project);
  void gitAddAndCommit(Project project, String message);
  void gitApplyPatch(Project project, String patchFilename);
}
