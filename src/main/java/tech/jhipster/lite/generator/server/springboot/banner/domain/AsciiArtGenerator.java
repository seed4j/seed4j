package tech.jhipster.lite.generator.server.springboot.banner.domain;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import tech.jhipster.lite.error.domain.Assert;

class AsciiArtGenerator {

  private static final int IMAGE_WIDTH = 150;
  private static final int IMAGE_HEIGHT = 50;

  private static final String FONT_NAME = "Monospaced";

  private static final int FONT_SIZE = 14;

  private static final int EMPTY_COLOR_CODE = -16777216;

  private static final String NO_COLORED_PIXEL = " ";

  private static final String COLORED_PIXEL = "█";

  private AsciiArtGenerator() {}

  static String from(String text) {
    Assert.notBlank("text", text);

    BufferedImage image = buildImageFor(text);

    return IntStream
      .range(0, IMAGE_HEIGHT)
      .mapToObj(y -> buildLine(image, y))
      .filter(line -> !line.isBlank())
      .collect(Collectors.joining(System.lineSeparator()));
  }

  private static BufferedImage buildImageFor(String text) {
    BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
    Graphics graphics = image.getGraphics();
    graphics.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));

    Graphics2D graphics2D = (Graphics2D) graphics;
    graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    String truncatedText = text.substring(0, Math.min(text.length(), 17));
    graphics2D.drawString(truncatedText, 10, 20);

    return image;
  }

  private static String buildLine(BufferedImage image, int y) {
    return IntStream.range(0, IMAGE_WIDTH).mapToObj(x -> pixelDisplay(image.getRGB(x, y))).collect(Collectors.joining());
  }

  private static String pixelDisplay(int pixelColorCode) {
    if (pixelColorCode == EMPTY_COLOR_CODE) {
      return NO_COLORED_PIXEL;
    }

    return COLORED_PIXEL;
  }
}
