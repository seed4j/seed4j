package tech.jhipster.lite.dsl.generator.clazz.infrastructure.secondary.roaster;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.dsl.generator.clazz.domain.ClassToGenerate;

@Repository
public class RoasterRepository {

  public static final String LF = "\n";
  private RoasterClassGenerator roasterClassGenerator;

  public RoasterRepository() {
    RoasterFieldGenerator roasterFieldGenerator = new RoasterFieldGenerator();
    this.roasterClassGenerator = new RoasterClassGenerator(roasterFieldGenerator);
  }

  public void generate(ClassToGenerate classToGenerate) {
    var base = roasterClassGenerator.generateClass(classToGenerate);

    String content = base.toString();
    System.out.println(content);

    Path pathFolder = classToGenerate.getFolder();
    File fileClass = new File(classToGenerate.getPathFile().toString());
    try {
      Files.createDirectories(pathFolder);
      System.out.println("generate class to " + fileClass.toString());
      FileUtils.write(fileClass, content, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
