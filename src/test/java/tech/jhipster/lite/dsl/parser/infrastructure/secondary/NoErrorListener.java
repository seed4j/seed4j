package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import java.util.BitSet;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.Assert;
import tech.jhipster.lite.dsl.parser.infrastructure.secondary.ThrowingErrorListener;

public class NoErrorListener extends BaseErrorListener {

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
    Assert.fail();
  }

  public void reportAmbiguity(
    Parser recognizer,
    DFA dfa,
    int startIndex,
    int stopIndex,
    boolean exact,
    BitSet ambigAlts,
    ATNConfigSet configs
  ) {
    Assert.fail();
  }
}
