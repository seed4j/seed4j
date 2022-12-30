package tech.jhipster.lite.dsl.generator.java.clazz.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import tech.jhipster.lite.dsl.common.domain.git.GitRepository;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.converter.ClassConverter;
import tech.jhipster.lite.dsl.parser.domain.DslApplication;
import tech.jhipster.lite.dsl.parser.domain.DslContext;
import tech.jhipster.lite.dsl.parser.domain.DslFrom;
import tech.jhipster.lite.dsl.parser.domain.HasClassAndEnum;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslEnum;
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
    List<IsImportable> list = new LinkedList<>();
    GenerateOption options = new GenerateOption().configApp(dslApplication.getConfig()).refManager(refManager);

    dslApplication
      .getLstDslContext()
      .forEach(ctx -> {
        options.ctx(ctx.getName()).typePackage(TypePackage.DOMAIN);
        ctx.getDomain().getDslClasses().forEach(dslClass -> list.add(getDslClassToGenerate(dslClass, options)));
        ctx.getDomain().getDslEnum().forEach(dslEnum -> list.add(getDslEnumToGenerate(dslEnum, options)));
        options.typePackage(TypePackage.PRIMARY);
        ctx
          .getPrimaries()
          .forEach(primary -> {
            primary.getDslClasses().forEach(dslClass -> list.add(getDslClassToGenerate(dslClass, options)));
            primary.getDslEnum().forEach(dslEnum -> list.add(getDslEnumToGenerate(dslEnum, options)));
            primary.getDslFroms().forEach(dslFrom -> manageFromReference(list, ctx, primary, dslFrom, options));
          });
        options.typePackage(TypePackage.SECONDARY);
        ctx
          .getSecondaries()
          .forEach(secondary -> {
            secondary.getDslClasses().forEach(dslClass -> list.add(getDslClassToGenerate(dslClass, options)));
            secondary.getDslEnum().forEach(dslEnum -> list.add(getDslEnumToGenerate(dslEnum, options)));
            secondary.getDslFroms().forEach(dslFrom -> manageFromReference(list, ctx, secondary, dslFrom, options));
          });

        fixUnknownTypeIfPossible(ctx.getName().get(), list, refManager);
      });
  }

  private void manageFromReference(
    List<IsImportable> list,
    DslContext ctx,
    HasClassAndEnum hasClassAndEnum,
    DslFrom dslFrom,
    GenerateOption options
  ) {
    if (options.getRefManager().hasKnowClass(options.getCtx().get(), dslFrom.name())) {
      Optional<DslClass> optClass = ctx.getDomain().getClass(dslFrom.name());
      if (optClass.isPresent()) {
        DslClass.DslClassBuilder builder = DslClass.dslClassBuilder();
        DslClass newDslClass = builder.from(optClass.get()).build();
        hasClassAndEnum.getDslClasses().add(newDslClass);
        list.add(getDslClassToGenerate(newDslClass, options));
      } else {
        Optional<DslEnum> optEnum = ctx.getDomain().getEnum(dslFrom.name());
        optEnum.ifPresent(val -> {
          DslEnum.DslEnumBuilder builder = DslEnum.dslEnumBuilder();
          DslEnum newDslEnum = builder.from(val).build();
          hasClassAndEnum.getDslEnum().add(newDslEnum);
          list.add(getDslEnumToGenerate(newDslEnum, options));
        });
      }
    }
  }

  private ClassToGenerate getDslClassToGenerate(DslClass dslClass, GenerateOption options) {
    return classConverter.convertDslClassToGenerate(
      dslClass,
      options.getCtx(),
      options.getConfigApp(),
      options.getRefManager(),
      options.getTypePackage()
    );
  }

  private EnumToGenerate getDslEnumToGenerate(DslEnum dslEnum, GenerateOption options) {
    return classConverter.convertDslEnumToGenerate(
      dslEnum,
      options.getCtx(),
      options.getConfigApp(),
      options.getRefManager(),
      options.getTypePackage()
    );
  }

  //    private ClassToGenerate getDslClassToGenerate(DslClass dslClass, ReferenceManager refManager, ConfigApp configApp, DslContext ctx, TypePackage typePackage) {
  //        return classConverter.convertDslClassToGenerate(dslClass, ctx.getName(), configApp, refManager, typePackage);
  //    }
  //
  //    private EnumToGenerate getDslEnumToGenerate(DslEnum dslEnum, ReferenceManager refManager, ConfigApp configApp, DslContext ctx, TypePackage typePackage) {
  //        return classConverter.convertDslEnumToGenerate(dslEnum, ctx.getName(), configApp, refManager, typePackage);
  //    }

  public void generate(DslApplication dslApplication) {
    Assert.notNull("dslApplication", dslApplication);

    ReferenceManager refManager = new ReferenceManager();

    prepareGenerate(dslApplication, refManager);

    GenerateOption options = new GenerateOption().configApp(dslApplication.getConfig()).refManager(refManager);

    List<ClassToGenerate> lstClassToGenerate = new LinkedList<>();
    List<EnumToGenerate> lstEnumToGenerate = new LinkedList<>();

    ConfigApp configApp = dslApplication.getConfig();
    dslApplication
      .getLstDslContext()
      .forEach(ctx -> {
        options.ctx(ctx.getName()).typePackage(TypePackage.DOMAIN);
        ctx.getDomain().getDslClasses().forEach(cls -> lstClassToGenerate.add(getDslClassToGenerate(cls, options)));
        ctx.getDomain().getDslEnum().forEach(enu -> lstEnumToGenerate.add(getDslEnumToGenerate(enu, options)));

        options.typePackage(TypePackage.PRIMARY);
        ctx
          .getPrimaries()
          .forEach(primary -> {
            primary.getDslClasses().forEach(cls -> lstClassToGenerate.add(getDslClassToGenerate(cls, options)));
            primary.getDslEnum().forEach(enu -> lstEnumToGenerate.add(getDslEnumToGenerate(enu, options)));
          });

        options.typePackage(TypePackage.SECONDARY);
        ctx
          .getSecondaries()
          .forEach(secondary -> {
            secondary.getDslClasses().forEach(cls -> lstClassToGenerate.add(getDslClassToGenerate(cls, options)));
            secondary.getDslEnum().forEach(enu -> lstEnumToGenerate.add(getDslEnumToGenerate(enu, options)));
          });
      });

    lstClassToGenerate.stream().filter(s -> !s.isIgnore()).forEach(generatorJavaRepository::generate);
    lstEnumToGenerate.stream().filter(s -> !s.isIgnore()).forEach(generatorJavaRepository::generate);

    ProjectPath projectPath = new ProjectPath(configApp.getProjectFolder().get());
    git.init(projectPath);

    generatorJavaRepository.format(projectPath);
    // generate
  }

  private void fixUnknownTypeIfPossible(String context, List<IsImportable> lstClass, ReferenceManager refManager) {
    Assert.field("context", context).notBlank();
    Assert.field("lstClass", lstClass).notNull().noNullElement();
    Assert.notNull("refManager", refManager);
    lstClass.forEach(cl -> {
      if (refManager.hasUnknownTypeForClass(context, cl.getNameForThisObject())) {
        manageUnknownType(context, lstClass, cl.getNameForThisObject(), refManager);
      }
    });
    refManager.cleanNoMoreUsedReference(context);
  }

  private static void manageUnknownType(String context, List<IsImportable> lstClass, String currentClass, ReferenceManager refManager) {
    List<String> unknownTypeForClass = refManager.getUnknownTypeForClass(context, currentClass);
    for (String unknownType : unknownTypeForClass) {
      lstClass
        .stream()
        .filter(s -> unknownType.equalsIgnoreCase(s.getNameForThisObject()))
        .findFirst()
        .ifPresent(val -> {
          refManager.addImportToClass(context, currentClass, val.getImportForThisObject());
          refManager.removeUnknownTypeIfPresent(context, currentClass, unknownType);
        });
    }
  }
}
