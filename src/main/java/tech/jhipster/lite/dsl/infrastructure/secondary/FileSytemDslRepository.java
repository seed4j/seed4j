package tech.jhipster.lite.dsl.infrastructure.secondary;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import tech.jhipster.lite.dsl.DslLexer;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.domain.DslApplication;
import tech.jhipster.lite.error.domain.Assert;

public class FileSytemDslRepository {

  private static final String SLASH = "/";

  public DslApplication load(String file) {
    try {
      InputStream fileStream = getInputStream(file);
      DslLexer lexer = new DslLexer(CharStreams.fromStream(fileStream));

      lexer.removeErrorListeners();
      lexer.addErrorListener(ThrowingErrorListener.INSTANCE);
      DslParser parser = new DslParser(new CommonTokenStream(lexer));
      parser.setErrorHandler(new ExceptionErrorStrategy());

      DslFileVisitor.FileVisitor fileVisitor = new DslFileVisitor.FileVisitor();

      return fileVisitor.visitFile_(parser.file_());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private InputStream getInputStream(String path) {
    Assert.field("path", path).notBlank();

    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path.replace("\\", SLASH));
    if (stream == null) {
      try {
        stream = Files.newInputStream(Paths.get(path));
      } catch (IOException e) {
        return null;
      }
    }
    return stream;
  }
}
