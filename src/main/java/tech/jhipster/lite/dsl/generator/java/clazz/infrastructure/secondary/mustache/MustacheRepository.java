package tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary.mustache;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.github.mustachejava.SpecMustacheFactory;
import com.github.mustachejava.util.DecoratedCollection;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.ClassToGenerate;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.EnumToGenerate;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.FileInfoForGenerate;
import tech.jhipster.lite.dsl.generator.java.clazz.domain.GeneratorJavaRepository;
import tech.jhipster.lite.dsl.generator.java.clazz.infrastructure.secondary.DslProjectFormatter;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.project.domain.ProjectPath;

@Repository
public class MustacheRepository implements GeneratorJavaRepository {

  private static final Logger log = LoggerFactory.getLogger(MustacheRepository.class);
  public static final String MUSTACHE_JAVA_TEMPLATE_DIR = "dsl/java/";

  private final DslProjectFormatter formatter;

  public MustacheRepository(DslProjectFormatter formatter) {
    this.formatter = formatter;
  }

  public void format(ProjectPath projectPath) {
    Assert.notNull("projectPath", projectPath);
    formatter.format(projectPath);
  }

  @Override
  @Generated(reason = "The error handling is an hard to test")
  public void generate(ClassToGenerate classToGenerate) {
    Assert.notNull("classToGenerate", classToGenerate);
    try {
      MustacheFactory mf = new SpecMustacheFactory();
      Mustache m = mf.compile(MUSTACHE_JAVA_TEMPLATE_DIR + classToGenerate.getType().key() + ".mustache");

      StringWriter writer = new StringWriter();
      Map<String, Object> context = new HashMap<>();
      addClassToContext(classToGenerate, context);
      addAllFieldsToContext(classToGenerate, context);
      m.execute(writer, context).flush();
      writeFile(classToGenerate, writer);
    } catch (IOException e) {
      throw new MustacheIoException("Error Io for generate a class", e);
    }
  }

  @Override
  @Generated(reason = "The error handling is an hard to test")
  public void generate(EnumToGenerate enumToGenerate) {
    Assert.notNull("classToGenerate", enumToGenerate);
    try {
      MustacheFactory mf = new SpecMustacheFactory();
      Mustache m = mf.compile(MUSTACHE_JAVA_TEMPLATE_DIR + "enum.mustache");

      StringWriter writer = new StringWriter();
      Map<String, Object> context = new HashMap<>();
      addEnumToContext(enumToGenerate, context);

      m.execute(writer, context).flush();
      writeFile(enumToGenerate, writer);
    } catch (IOException e) {
      throw new MustacheIoException("Error Io for generate a enum", e);
    }
  }

  private static void writeFile(FileInfoForGenerate fileToGenerate, StringWriter writer) throws IOException {
    Assert.notNull("fileToGenerate", fileToGenerate);
    Assert.notNull("writer", writer);
    String content = writer.toString();

    Path pathFolder = fileToGenerate.getFolder();
    File fileClass = new File(fileToGenerate.getPathFile().toString());

    Files.createDirectories(pathFolder);
    log.debug("generate class to {}", fileClass);
    FileUtils.write(fileClass, content, StandardCharsets.UTF_8);
  }

  private static void addEnumToContext(EnumToGenerate enumToGenerate, Map<String, Object> context) {
    context.put("className", enumToGenerate.getName().get());
    context.put("classPackage", enumToGenerate.getPackage().get());
    context.put("hasImport", enumToGenerate.asImport());
    context.put("createConstructor", !enumToGenerate.getElementValue().isEmpty());
    context.put("enumToGenerate", enumToGenerate);

    context.put("elementSimple", new DecoratedCollection<>(enumToGenerate.getElementSimple()));
    context.put("elementValue", new DecoratedCollection<>(enumToGenerate.getElementValue()));
    enumToGenerate.getComment().ifPresent(comment -> context.put("classComment", comment));
  }

  private static void addClassToContext(ClassToGenerate classToGenerate, Map<String, Object> context) {
    context.put("className", classToGenerate.getName().get());
    context.put("classType", classToGenerate.getType().key());
    context.put("classPackage", classToGenerate.getPackage().get());
    classToGenerate.getComment().ifPresent(comment -> context.put("classComment", comment));
    context.put("imports", classToGenerate.getImports().stream().filter(i -> !i.isStatic()).map(ClassImport::get).toList());
    context.put("importsStatic", classToGenerate.getImports().stream().filter(ClassImport::isStatic).map(ClassImport::get).toList());
    context.put("fluent", classToGenerate.isGenerateFluent());
  }

  private static void addAllFieldsToContext(ClassToGenerate classToGenerate, Map<String, Object> context) {
    List<FieldsMustache> fields = new LinkedList<>();
    classToGenerate.getFields().forEach(field -> fields.add(FieldsMustache.builder().from(field).build()));

    context.put("fields", fields);
    context.put("decoratedFields", new DecoratedCollection<>(fields));
  }
}
