package tech.jhipster.lite.error.infrastructure.primary;

import java.net.URI;

final class ErrorConstants {

  public static final String ERR_VALIDATION = "error.validation";
  public static final String PROBLEM_BASE_URL = "https://www.jhipster.tech/problem";
  public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");

  private ErrorConstants() {}
}
