package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import tech.jhipster.lite.dsl.DslLexer;
import tech.jhipster.lite.dsl.DslParser;

public class AntlrUtils {

  private AntlrUtils() {}

  private static DslParser getParser(String txt) {
    try {
      InputStream inStream = new ByteArrayInputStream(txt.getBytes());
      DslLexer lexer = new DslLexer(CharStreams.fromStream(inStream));

      lexer.addErrorListener(new NoErrorListener()); // listener
      DslParser parser = new DslParser(new CommonTokenStream(lexer));
      parser.setErrorHandler(new ExceptionErrorStrategy());

      return parser;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static DslParser.BeforeClassContext getBeforeClassContextFromText(String txt) {
    return getParser(txt).beforeClass();
  }

  public static DslParser.ClassFieldContext getClassFieldContextFromText(String txt) {
    return getParser(txt).classField();
  }

  public static DslParser.ClassContext getClassContextFromText(String txt) {
    return getParser(txt).class_();
  }

  public static DslParser.DomainContext getDomainContextFromText(String txt) {
    return getParser(txt).domain();
  }

  public static DslParser.File_Context getFileContextFromText(String txt) {
    return getParser(txt).file_();
  }

  public static DslParser.ConfigbodyContext getConfigBodyFromText(String txt) {
    return getParser(txt).configbody();
  }

  public static DslParser.ContextContext getContextFromText(String txt) {
    return getParser(txt).context();
  }

  public static DslParser.MinMaxNumberValidatorContext getMinMaxNumberValidatorFromText(String txt) {
    return getParser(txt).minMaxNumberValidator();
  }

  public static List<Token> getTokensFromText(String txt) {
    try {
      InputStream inStream = new ByteArrayInputStream(txt.getBytes());
      DslLexer lexer = new DslLexer(CharStreams.fromStream(inStream));

      lexer.addErrorListener(new NoErrorListener()); // listener
      var tokenStream = new CommonTokenStream(lexer);
      tokenStream.fill();
      tokenStream.getTokens();

      return tokenStream.getTokens();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
