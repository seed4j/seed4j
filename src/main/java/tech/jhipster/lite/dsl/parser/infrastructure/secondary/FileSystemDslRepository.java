package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.dsl.DslLexer;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.parser.domain.DslApplication;
import tech.jhipster.lite.dsl.parser.domain.DslRepository;
import tech.jhipster.lite.dsl.parser.domain.JhipsterDslFileIdentifiant;
import tech.jhipster.lite.dsl.parser.domain.JhipsterDslFileToSave;
import tech.jhipster.lite.error.domain.Assert;

@Repository
public class FileSystemDslRepository implements DslRepository {

  private static final String SLASH = "/";

  public JhipsterDslFileIdentifiant createDslFile(JhipsterDslFileToSave file) {
    Assert.notNull("file", file);
    try {
      Path fileToSave = Files.createTempFile("jhipster_dsl", file.getName());
      fileToSave.toFile().deleteOnExit();

      try (FileOutputStream outputStream = new FileOutputStream(fileToSave.toFile())) {
        outputStream.write(file.getBytes());
      }
      return new JhipsterDslFileIdentifiant(fileToSave.toString());
    } catch (IOException e) {
      throw new DslFileIOException("Error when create temporary dsl file");
    }
  }

  public DslApplication parseDsl(JhipsterDslFileIdentifiant file) {
    Assert.notNull("file", file);
    try {
      InputStream fileStream = getInputStream(file.id());
      DslLexer lexer = new DslLexer(CharStreams.fromStream(fileStream));

      lexer.removeErrorListeners();
      lexer.addErrorListener(ThrowingErrorListener.INSTANCE);
      DslParser parser = new DslParser(new CommonTokenStream(lexer));
      parser.setErrorHandler(new ExceptionErrorStrategy());

      DslFileVisitor.FileVisitor fileVisitor = new DslFileVisitor.FileVisitor();

      return fileVisitor.visitFile_(parser.file_());
    } catch (IOException e) {
      throw new DslFileIOException("Error when parse temporary  dsl file");
    }
  }

  private InputStream getInputStream(String path) {
    Assert.field("path", path).notBlank();

    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path.replace("\\", SLASH));
    if (stream == null) {
      try {
        stream = Files.newInputStream(Paths.get(path));
      } catch (IOException e) {
        throw new DslFileIOException("Error when load dsl file");
      }
    }
    return stream;
  }
}
