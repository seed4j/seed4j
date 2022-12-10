package tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary.roaster;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.ClassToGenerate;

public class RoasterClassGenerator {

  public static final String LF = "\n";

  private final RoasterFieldGenerator fieldGenerator;

  public RoasterClassGenerator(RoasterFieldGenerator fieldGenerator) {
    this.fieldGenerator = fieldGenerator;
  }

  public JavaClassSource generateClass(ClassToGenerate classToGenerate) {
    final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);

    classToGenerate.getImports().forEach(s -> javaClass.addImport(s.aImport()));

    classToGenerate.getComment().ifPresent(comment -> javaClass.getJavaDoc().setText(comment.get()));

    javaClass.setPackage(classToGenerate.getPackage().get()).setName(classToGenerate.getName().get());

    classToGenerate
      .getFields()
      .forEach(field -> {
        fieldGenerator.addProperty(field, classToGenerate, javaClass);
      });

    //if useFluent - pass config to generateClass?
    classToGenerate
      .getFields()
      .forEach(field -> {
        fieldGenerator.generateFluentForProperty(field, classToGenerate.getName().get(), javaClass);
      });

    return javaClass;
  }
}
