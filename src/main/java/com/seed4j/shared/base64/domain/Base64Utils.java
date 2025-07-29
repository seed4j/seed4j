package com.seed4j.shared.base64.domain;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.security.SecureRandom;
import java.util.Base64;

public final class Base64Utils {

  private static final SecureRandom RANDOM = new SecureRandom();

  private Base64Utils() {}

  public static String getBase64Secret() {
    return Base64.getEncoder().encodeToString(getRandomHexString(64).getBytes(UTF_8));
  }

  private static String getRandomHexString(int length) {
    StringBuilder result = new StringBuilder();

    while (result.length() < length) {
      result.append(Integer.toHexString(RANDOM.nextInt()));
    }

    return result.substring(0, length);
  }
}
