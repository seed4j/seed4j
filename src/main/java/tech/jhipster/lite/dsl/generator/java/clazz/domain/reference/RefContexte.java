package tech.jhipster.lite.dsl.generator.java.clazz.domain.reference;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassName;
import tech.jhipster.lite.error.domain.Assert;

public class RefContexte {

  private final RefContexteName name;
  private final List<RefClass> classes = new LinkedList<>();

  public RefContexte(RefContexteName name) {
    Assert.notNull("name", name);
    this.name = name;
  }

  public RefContexteName getName() {
    return name;
  }

  public List<RefClass> getClasses() {
    return classes;
  }

  public RefClass getOrCreateClass(String name) {
    Assert.field("name", name).notBlank();

    Optional<RefClass> optRefClass = classes.stream().filter(clz -> name.equalsIgnoreCase(clz.getName().get())).findFirst();
    RefClass curRefClass;
    if (optRefClass.isEmpty()) {
      curRefClass = new RefClass(new ClassName(name));
      classes.add(curRefClass);
    } else {
      curRefClass = optRefClass.get();
    }
    return curRefClass;
  }

  public boolean hasClass(String name) {
    Assert.field("name", name).notBlank();
    return classes.stream().anyMatch(clz -> name.equalsIgnoreCase(clz.getName().get()));
  }
}
