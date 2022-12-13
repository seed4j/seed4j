package tech.jhipster.lite.dsl.generator.java.clazz.domain;

import java.util.*;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;
import tech.jhipster.lite.error.domain.Assert;

public class ReferenceManager {

  public static final String CLAZZ = "clazz";
  private final Map<String, List<ClassImport>> refClassImport = new LinkedHashMap<>();
  private final Map<String, List<String>> unknownTypeByClass = new LinkedHashMap<>();

  public void addImportToClass(String clazz, ClassImport classImport) {
    Assert.field(CLAZZ, clazz).notBlank();
    Assert.notNull("import_", classImport);
    String classLower = clazz.toLowerCase();
    List<ClassImport> importForClass = new ArrayList<>(refClassImport.getOrDefault(classLower, new LinkedList<>()));
    importForClass.add(classImport);
    refClassImport.put(classLower, importForClass);
  }

  public List<ClassImport> getImportsForClass(String clazz) {
    Assert.field(CLAZZ, clazz).notBlank();
    return refClassImport.getOrDefault(clazz.toLowerCase(), new LinkedList<>()).stream().distinct().toList();
  }

  public void addUnknownPropertyTypeInClass(String clazz, String type) {
    Assert.notNull(CLAZZ, clazz);
    Assert.notNull("type", type);
    String classLower = clazz.toLowerCase();
    List<String> unknownType = unknownTypeByClass.getOrDefault(classLower, new LinkedList<>());
    unknownType.add(type.toLowerCase());
    unknownTypeByClass.put(classLower, unknownType);
  }

  public boolean hasUnknownTypeForClass(String clazz) {
    Assert.notNull(CLAZZ, clazz);
    return unknownTypeByClass.containsKey(clazz.toLowerCase());
  }

  public List<String> getUnknownTypeForClass(String clazz) {
    Assert.notNull(CLAZZ, clazz);
    return unknownTypeByClass.getOrDefault(clazz.toLowerCase(), new LinkedList<>()).stream().distinct().toList();
  }

  public void removeUnknownTypeIfPresent(String clazz, String type) {
    Assert.notNull(CLAZZ, clazz);
    Assert.notNull("type", type);
    String classLower = clazz.toLowerCase();
    List<String> unknownType = unknownTypeByClass.get(classLower);
    unknownType.remove(type.toLowerCase());
  }

  public void cleanNoMoreUsedReference() {
    unknownTypeByClass.entrySet().removeIf(k -> k.getValue().isEmpty());

    for (Map.Entry<String, List<ClassImport>> entry : refClassImport.entrySet()) {
      entry.setValue(entry.getValue().stream().distinct().toList());
    }
  }
}
