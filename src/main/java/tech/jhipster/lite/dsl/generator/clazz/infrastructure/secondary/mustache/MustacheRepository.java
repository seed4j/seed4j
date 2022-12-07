package tech.jhipster.lite.dsl.generator.clazz.infrastructure.secondary.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.github.mustachejava.util.DecoratedCollection;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.dsl.common.domain.clazz.ClassImport;
import tech.jhipster.lite.dsl.generator.clazz.domain.ClassToGenerate;
import tech.jhipster.lite.error.domain.Assert;

@Repository
public class MustacheRepository {

  public static final String MUSTACHE_JAVA_TEMPLATE_DIR = "dsl/java/";

  public MustacheRepository() {}

  public void generate(ClassToGenerate classToGenerate) {
    Assert.notNull("classToGenerate", classToGenerate);
    try {
      MustacheFactory mf = new DefaultMustacheFactory();
      Mustache m = mf.compile(MUSTACHE_JAVA_TEMPLATE_DIR + classToGenerate.getType().key() + ".mustache");
      StringWriter writer = new StringWriter();
      Map<String, Object> context = new HashMap<>();
      addClassToContext(classToGenerate, context);
      addAllFieldsToContext(classToGenerate, context);

      m.execute(writer, context).flush();

      String content = writer.toString();
      System.out.println(content);

      Path pathFolder = classToGenerate.getFolder();
      File fileClass = new File(classToGenerate.getPathFile().toString());

      Files.createDirectories(pathFolder);
      System.out.println("generate class to " + fileClass.toString());
      FileUtils.write(fileClass, content, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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

    Collection<FieldsMustache> decoratedFields = new DecoratedCollection(fields);
    context.put("fields", fields);
    context.put("decoratedFields", decoratedFields);
  }
}
