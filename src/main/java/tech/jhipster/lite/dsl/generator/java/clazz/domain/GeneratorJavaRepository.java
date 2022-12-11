package tech.jhipster.lite.dsl.generator.java.clazz.domain;

import tech.jhipster.lite.project.domain.ProjectPath;

public interface GeneratorJavaRepository {
  void format(ProjectPath projectPath);
  void generate(ClassToGenerate classToGenerate);

  void generate(EnumToGenerate enumToGenerate);
}
