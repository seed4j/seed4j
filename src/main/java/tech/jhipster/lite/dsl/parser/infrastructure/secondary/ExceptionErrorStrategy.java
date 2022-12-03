package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.IntervalSet;

public class ExceptionErrorStrategy extends DefaultErrorStrategy {

  @Override
  public void recover(Parser recognizer, RecognitionException e) {
    throw e;
  }

  @Override
  protected void reportUnwantedToken(Parser recognizer) {
    this.beginErrorCondition(recognizer);
    Token t = recognizer.getCurrentToken();
    String tokenName = this.getTokenErrorDisplay(t);
    IntervalSet expecting = this.getExpectedTokens(recognizer);
    String msg = "extraneous input " + tokenName + " expecting " + expecting.toString(recognizer.getVocabulary());

    throw new RecognitionException(msg, recognizer, recognizer.getInputStream(), recognizer.getContext());
  }

  @Override
  public void reportInputMismatch(Parser recognizer, InputMismatchException e) throws RecognitionException {
    int line = e.getOffendingToken().getLine();
    int charPositionInLine = e.getOffendingToken().getCharPositionInLine();
    String msg = "Line " + line + ", position " + charPositionInLine + ": mismatched input " + getTokenErrorDisplay(e.getOffendingToken());
    msg += " expecting one of " + e.getExpectedTokens().toString(recognizer.getVocabulary());

    RecognitionException ex = new RecognitionException(msg, recognizer, recognizer.getInputStream(), recognizer.getContext());
    ex.initCause(e);
    throw ex;
  }

  @Override
  public void reportMissingToken(Parser recognizer) {
    beginErrorCondition(recognizer);
    Token t = recognizer.getCurrentToken();
    IntervalSet expecting = getExpectedTokens(recognizer);
    String msg = "missing " + expecting.toString(recognizer.getVocabulary()) + " at " + getTokenErrorDisplay(t);
    throw new RecognitionException(msg, recognizer, recognizer.getInputStream(), recognizer.getContext());
  }
}
