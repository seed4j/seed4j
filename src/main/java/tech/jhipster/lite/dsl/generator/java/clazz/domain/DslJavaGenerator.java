package tech.jhipster.lite.dsl.generator.java.clazz.domain;

import java.util.LinkedList;
import java.util.List;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;
import tech.jhipster.lite.dsl.common.domain.git.GitRepository;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.converter.ClassConverter;
import tech.jhipster.lite.dsl.parser.domain.DslApplication;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.project.domain.ProjectPath;

public class DslJavaGenerator {

  private final GeneratorJavaRepository generatorJavaRepository;
  private final ClassConverter classConverter;

  private final GitRepository git;

  public DslJavaGenerator(GeneratorJavaRepository generatorJavaRepository, ClassConverter classConverter, GitRepository git) {
    Assert.notNull("generatorJavaRepository", generatorJavaRepository);
    Assert.notNull("converter", classConverter);
    Assert.notNull("git", git);

    this.generatorJavaRepository = generatorJavaRepository;
    this.classConverter = classConverter;
    this.git = git;
  }

  protected void prepareGenerate(DslApplication dslApplication, ReferenceManager refManager) {
    List<ClassToGenerate> lstClass = new LinkedList<>();
    List<EnumToGenerate> lstEnum = new LinkedList<>();
    ConfigApp configApp = dslApplication.getConfig();
    dslApplication
      .getLstDslContext()
      .forEach(ctx -> {
        ctx
          .getDomain()
          .getDslClasses()
          .forEach(dslClass -> lstClass.add(classConverter.convertDslClassToGenerate(dslClass, ctx.getName(), configApp, refManager)));
        ctx
          .getDomain()
          .getDslEnum()
          .forEach(dslEnum -> lstEnum.add(classConverter.convertDslEnumToGenerate(dslEnum, ctx.getName(), configApp, refManager)));
      });

    List<IsImportable> list = new LinkedList<>();
    list.addAll(lstClass);
    list.addAll(lstEnum);
    fixUnknownTypeIfPossible(list, refManager);
  }

  public void generate(DslApplication dslApplication) {
    Assert.notNull("dslApplication", dslApplication);

    ReferenceManager refManager = new ReferenceManager();

    prepareGenerate(dslApplication, refManager);

    ConfigApp configApp = dslApplication.getConfig();
    dslApplication
      .getLstDslContext()
      .forEach(ctx -> {
        ctx
          .getDomain()
          .getDslClasses()
          .forEach(dslClass -> {
            ClassToGenerate clsToGenerate = classConverter.convertDslClassToGenerate(dslClass, ctx.getName(), configApp, refManager);
            if (!clsToGenerate.isIgnore()) {
              generatorJavaRepository.generate(clsToGenerate);
            }
          });
        ctx
          .getDomain()
          .getDslEnum()
          .forEach(dslEnum -> {
            EnumToGenerate enumToGenerate = classConverter.convertDslEnumToGenerate(dslEnum, ctx.getName(), configApp, refManager);
            if (!enumToGenerate.isIgnore()) {
              generatorJavaRepository.generate(enumToGenerate);
            }
          });
      });
    ProjectPath projectPath = new ProjectPath(configApp.getProjectFolder().get());
    git.init(projectPath);

    generatorJavaRepository.format(projectPath);
    // generate
  }

  private void fixUnknownTypeIfPossible(List<IsImportable> lstClass, ReferenceManager refManager) {
    Assert.field("lstClass", lstClass).notNull().noNullElement();
    Assert.notNull("refManager", refManager);
    lstClass.forEach(cl -> {
      if (refManager.hasUnknownTypeForClass(cl.getNameForThisObject())) {
        manageUnknownType(lstClass, cl.getNameForThisObject(), refManager);
      }
    });

    refManager.cleanNoMoreUsedReference();
  }

  private static void manageUnknownType(List<IsImportable> lstClass, String currentClass, ReferenceManager refManager) {
    List<String> unknownTypeForClass = refManager.getUnknownTypeForClass(currentClass);
    for (String unknownType : unknownTypeForClass) {
      lstClass
        .stream()
        .filter(s -> unknownType.equalsIgnoreCase(s.getNameForThisObject()))
        .findFirst()
        .ifPresent(val -> {
          refManager.addImportToClass(currentClass, new ClassImport(val.getImportForThisObject(), false));
          refManager.removeUnknownTypeIfPresent(currentClass, unknownType);
        });
    }
  }
}
