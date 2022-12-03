package tech.jhipster.lite.dsl.generator.clazz.domain;

import java.nio.file.Path;
import java.nio.file.Paths;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassPackage;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslClass;
import tech.jhipster.lite.dsl.parser.domain.clazz.DslContextName;
import tech.jhipster.lite.dsl.parser.domain.config.ConfigApp;

public class ClassConverter {

  private FieldConverter fieldConverter;

  public ClassConverter(FieldConverter fieldConverter) {
    this.fieldConverter = fieldConverter;
  }

  public ClassToGenerate convertDslClassToGenerate(DslClass dslClass, ConfigApp config, DslContextName contextName) {
    Path packageClass = Paths.get(config.getBasePackage().path(), contextName.get(), config.getPackageDomainName().path());
    Path folderClass = Paths.get(config.getProjectFolder().get(), packageClass.toString());
    Path file = Paths.get(folderClass.toString(), dslClass.getName().get().concat(".java"));

    ClassToGenerate.ClassToGenerateBuilder builder = ClassToGenerate.classToGenerateBuilder();
    builder.fromDslClass(dslClass);
    dslClass
      .getFields()
      .forEach(s -> {
        builder.addField(this.fieldConverter.convertFieldToGenerate(s));
      });

    return builder.definePackage(new ClassPackage(packageClass.toString())).folder(folderClass).file(file).build();
  }
}
