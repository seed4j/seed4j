package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import tech.jhipster.lite.common.domain.Generated;

@Generated
public class ThrowingErrorListener extends BaseErrorListener {

  public static final ThrowingErrorListener INSTANCE = new ThrowingErrorListener();

  @Override
  public void syntaxError(
    Recognizer<?, ?> recognizer,
    Object offendingSymbol,
    int line,
    int charPositionInLine,
    String msg,
    RecognitionException e
  ) throws ParseCancellationException {
    throw new ParseCancellationException("line " + line + ":" + charPositionInLine + " " + msg);
  }
}
