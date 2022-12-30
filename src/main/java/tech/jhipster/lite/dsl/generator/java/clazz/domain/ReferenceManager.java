package tech.jhipster.lite.dsl.generator.java.clazz.domain;

import java.util.*;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.reference.RefClass;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.reference.RefContexte;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.reference.RefContexteName;
import tech.jhipster.lite.error.domain.Assert;

public class ReferenceManager {

  public static final String CLAZZ = "clazz";
  public static final String CONTEXT = "context";

  private final List<RefContexte> contexts = new LinkedList<>();

  public void addImportToClass(String context, String clazz, String classImport) {
    addImportToClass(context, clazz, new ClassImport(classImport, false));
  }

  public void addImportToClass(String context, String clazz, ClassImport classImport) {
    Assert.notNull("import_", classImport);
    RefClass refClass = getRefClassInContext(context, clazz);
    refClass.addImport(classImport);
  }

  public List<ClassImport> getImportsForClass(String context, String clazz) {
    Assert.field(CLAZZ, clazz).notBlank();
    Assert.field(CONTEXT, context).notBlank();
    return getRefClassInContext(context, clazz).getImports().stream().distinct().toList();
  }

  private RefClass getRefClassInContext(String context, String clazz) {
    Assert.field(CONTEXT, context).notBlank();
    Assert.field(CLAZZ, clazz).notBlank();
    RefContexte curRefContexte = getRefContexte(context);

    return curRefContexte.getOrCreateClass(clazz);
  }

  public boolean hasKnowClass(String context, String clazz) {
    Assert.field(CONTEXT, context).notBlank();
    Assert.field(CLAZZ, clazz).notBlank();
    return getRefContexte(context).hasClass(clazz);
  }

  private RefContexte getRefContexte(String context) {
    Assert.field(CONTEXT, context).notBlank();
    RefContexte curRefContexte;
    Optional<RefContexte> optRefContexte = contexts.stream().filter(ctx -> context.equalsIgnoreCase(ctx.getName().name())).findFirst();
    if (optRefContexte.isEmpty()) {
      curRefContexte = new RefContexte(new RefContexteName(context));
      contexts.add(curRefContexte);
    } else {
      curRefContexte = optRefContexte.get();
    }
    return curRefContexte;
  }

  public void addUnknownPropertyTypeInClass(String context, String clazz, String type) {
    Assert.field(CONTEXT, context).notBlank();
    Assert.notNull(CLAZZ, clazz);
    Assert.notNull("type", type);
    getRefContexte(context).getOrCreateClass(clazz).addType(type);
  }

  public boolean hasUnknownTypeForClass(String context, String clazz) {
    Assert.field(CONTEXT, context).notBlank();
    Assert.notNull(CLAZZ, clazz);

    return !getRefContexte(context).getOrCreateClass(clazz).getUnknownType().isEmpty();
  }

  public List<String> getUnknownTypeForClass(String context, String clazz) {
    Assert.field(CONTEXT, context).notBlank();
    Assert.notNull(CLAZZ, clazz);
    return getRefContexte(context).getOrCreateClass(clazz).getUnknownType().stream().distinct().toList();
  }

  public void removeUnknownTypeIfPresent(String context, String clazz, String type) {
    Assert.field(CONTEXT, context).notBlank();
    Assert.notNull(CLAZZ, clazz);
    Assert.notNull("type", type);
    getRefContexte(context).getOrCreateClass(clazz).removeIfPresent(type);
  }

  public void cleanNoMoreUsedReference(String context) {
    getRefContexte(context).getClasses().forEach(RefClass::clean);
  }
  //
  //    @Deprecated
  //    public void addImportToClass(String clazz, ClassImport classImport) {
  //        Assert.field(CLAZZ, clazz).notBlank();
  //        Assert.notNull("import_", classImport);
  //        String classLower = clazz.toLowerCase();
  //        List<ClassImport> importForClass = new ArrayList<>(refClassImport.getOrDefault(classLower, new LinkedList<>()));
  //        importForClass.add(classImport);
  //        refClassImport.put(classLower, importForClass);
  //    }
  //
  //    @Deprecated
  //    public List<ClassImport> getImportsForClass(String clazz) {
  //        Assert.field(CLAZZ, clazz).notBlank();
  //        return refClassImport.getOrDefault(clazz.toLowerCase(), new LinkedList<>()).stream().distinct().toList();
  //    }
  //
  //    @Deprecated
  //    public void addUnknownPropertyTypeInClass(String clazz, String type) {
  //        Assert.notNull(CLAZZ, clazz);
  //        Assert.notNull("type", type);
  //        String classLower = clazz.toLowerCase();
  //        List<String> unknownType = unknownTypeByClass.getOrDefault(classLower, new LinkedList<>());
  //        unknownType.add(type.toLowerCase());
  //        unknownTypeByClass.put(classLower, unknownType);
  //    }
  //
  //    @Deprecated
  //    public boolean hasUnknownTypeForClass(String clazz) {
  //        Assert.notNull(CLAZZ, clazz);
  //        return unknownTypeByClass.containsKey(clazz.toLowerCase());
  //    }
  //
  //    @Deprecated
  //    public List<String> getUnknownTypeForClass(String clazz) {
  //        Assert.notNull(CLAZZ, clazz);
  //        return unknownTypeByClass.getOrDefault(clazz.toLowerCase(), new LinkedList<>()).stream().distinct().toList();
  //    }
  //
  //    @Deprecated
  //    public void removeUnknownTypeIfPresent(String clazz, String type) {
  //        Assert.notNull(CLAZZ, clazz);
  //        Assert.notNull("type", type);
  //        String classLower = clazz.toLowerCase();
  //        List<String> unknownType = unknownTypeByClass.get(classLower);
  //        unknownType.remove(type.toLowerCase());
  //    }
  //
  //    @Deprecated
  //    public void cleanNoMoreUsedReference() {
  //        unknownTypeByClass.entrySet().removeIf(k -> k.getValue().isEmpty());
  //
  //        for (Map.Entry<String, List<ClassImport>> entry : refClassImport.entrySet()) {
  //            entry.setValue(entry.getValue().stream().distinct().toList());
  //        }
  //    }
}
