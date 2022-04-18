package tech.jhipster.lite.common.domain;

import java.security.SecureRandom;
import org.apache.tomcat.util.codec.binary.Base64;

public class Base64Utils {

  private static final SecureRandom random = new SecureRandom();

  private Base64Utils() {}

  public static String getBase64Secret() {
    return getBase64Secret(null);
  }

  public static String getBase64Secret(String value) {
    return getBase64Secret(value, 64);
  }

  public static String getBase64Secret(String value, int length) {
    return Base64.encodeBase64String(value != null ? value.getBytes() : getRandomHexString(length).getBytes());
  }

  public static String getRandomHexString(int length) {
    StringBuilder stringBuilder = new StringBuilder();
    while (stringBuilder.length() < length) {
      stringBuilder.append(Integer.toHexString(random.nextInt()));
    }
    return stringBuilder.substring(0, length);
  }
}
