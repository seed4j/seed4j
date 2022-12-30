package tech.jhipster.lite.dsl.generator.java.clazz.domain.reference;

import java.util.LinkedList;
import java.util.List;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassName;
import tech.jhipster.lite.error.domain.Assert;

public class RefClass {

  private final ClassName name;
  private final List<ClassImport> imports = new LinkedList<>();
  private final List<String> unknownType = new LinkedList<>();

  public RefClass(ClassName name) {
    this.name = name;
  }

  public ClassName getName() {
    return name;
  }

  public List<ClassImport> getImports() {
    return imports;
  }

  public List<String> getUnknownType() {
    return unknownType;
  }

  public void addImport(ClassImport classImport) {
    Assert.notNull("classImport", classImport);
    imports.add(classImport);
  }

  public void addType(String type) {
    Assert.field("type", type).notBlank();
    unknownType.add(type.toLowerCase());
  }

  public void removeIfPresent(String type) {
    Assert.field("type", type).notBlank();
    unknownType.remove(type.toLowerCase());
  }

  public void clean() {
    List<String> tmpUnknown = List.copyOf(this.unknownType);
    this.unknownType.clear();
    this.unknownType.addAll(tmpUnknown.stream().distinct().toList());
    List<ClassImport> tmpClassImport = List.copyOf(this.imports);
    this.imports.clear();
    this.imports.addAll(tmpClassImport.stream().distinct().toList());
  }
}
