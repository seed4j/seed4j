package tech.jhipster.lite.common.domain;

import static tech.jhipster.lite.common.domain.WordUtils.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;

public class FileUtils {

  private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
  private static final String FILE_SEPARATOR = "/";

  public static final String REGEXP_PREFIX_MULTILINE = "(?m)";
  public static final String REGEXP_PREFIX_DOTALL = "(?s)";
  public static final String REGEXP_DOT_STAR = ".*";
  public static final String REGEXP_SPACE_STAR = "[ \t]*";
  public static final String REGEXP_DOT_PLUS_MIN = "(.+?)";

  public static final String FILENAME = "filename";

  private FileUtils() {}

  public static void createFolder(String path) throws IOException {
    Assert.notBlank("path", path);

    Files.createDirectories(Paths.get(path));
  }

  public static boolean exists(String path) {
    Assert.notBlank("path", path);

    return Files.exists(Path.of(path));
  }

  public static String tmpDir() {
    String tempDir = System.getProperty("java.io.tmpdir");
    String fileSeparator = FileSystems.getDefault().getSeparator();
    if (tempDir.endsWith(fileSeparator)) {
      return tempDir.substring(0, tempDir.length() - fileSeparator.length());
    }
    return tempDir;
  }

  public static String tmpDirForTest() {
    return getPath(tmpDir(), "jhlite-test", UUID.randomUUID().toString());
  }

  public static String getPath(String... paths) {
    return String.join(FILE_SEPARATOR, paths).replace("\\", FILE_SEPARATOR);
  }

  public static Path getPathOf(String... paths) {
    return Path.of(getPath(paths));
  }

  public static String read(String filename) throws IOException {
    return normalizeEndOfLine(Files.readString(getPathOf(filename), StandardCharsets.UTF_8));
  }

  public static void write(String filename, String text, String eol) throws IOException {
    Files.write(getPathOf(filename), transformEndOfLine(text, eol).getBytes());
  }

  public static Optional<String> getValueBetween(String filename, String prefix, String suffix) {
    Assert.notBlank(FILENAME, filename);
    Optional<String> matchingLine = readLine(filename, prefix);
    return matchingLine.map(line -> getValueFromLine(prefix, suffix, line)).orElse(Optional.empty());
  }

  public static Optional<String> getValueFromLine(String prefix, String suffix, String line) {
    Pattern pattern = Pattern.compile(prefix + REGEXP_DOT_PLUS_MIN + suffix, Pattern.DOTALL);
    Matcher matcher = pattern.matcher(line);
    if (matcher.find()) {
      return Optional.of(matcher.group(1));
    }
    return Optional.empty();
  }

  public static int getLine(String filename, String value) throws IOException {
    File file = new File(filename);
    int lineNumber = 0;
    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        lineNumber++;
        String line = scanner.nextLine();
        if (line.contains(value)) {
          return lineNumber;
        }
      }
    }
    return -1;
  }

  public static Optional<String> readLine(String filename, String value) {
    Assert.notBlank(FILENAME, filename);
    Assert.notNull("value", value);

    File file = new File(filename);
    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.contains(value)) {
          return Optional.of(line);
        }
      }
    } catch (FileNotFoundException e) {
      log.error("Can't readLine as the filename '{}' is not found", filename, e);
    }
    return Optional.empty();
  }

  public static Optional<String> readLineInClasspath(String filename, String value) {
    Assert.notBlank(FILENAME, filename);
    Assert.notNull("value", value);

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream(filename)))) {
      while (reader.ready()) {
        String line = reader.readLine();
        if (line.contains(value)) {
          return Optional.of(line);
        }
      }
    } catch (Exception e) {
      log.error("Can't readLine as the filename '{}' is not found", filename, e);
    }
    return Optional.empty();
  }

  public static List<String> readLinesInClasspath(String filename) {
    Assert.notBlank(FILENAME, filename);

    return new BufferedReader(new InputStreamReader(getInputStream(filename))).lines().toList();
  }

  private static InputStream getInputStream(String... paths) {
    InputStream in = FileUtils.class.getResourceAsStream(FILE_SEPARATOR + getPath(paths));

    if (in == null) {
      throw new GeneratorException("File '" + getPath(paths) + "' not found in classpath");
    }
    return in;
  }

  public static boolean containsLines(String filename, List<String> lines) {
    Assert.notBlank(FILENAME, filename);
    Assert.notEmpty("lines", lines);

    File file = new File(filename);
    int lineNumberToCheck = 0;
    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (!line.contains(lines.get(lineNumberToCheck))) {
          lineNumberToCheck = 0;
        } else if (lineNumberToCheck >= lines.size() - 1) {
          return true;
        } else {
          lineNumberToCheck++;
        }
      }
    } catch (IOException ex) {
      log.error("The file {} does not exist", filename);
    }
    return false;
  }

  public static boolean containsInLine(String filename, String value) {
    boolean findValue = false;
    try {
      findValue = getLine(filename, value) != -1;
    } catch (IOException ex) {
      log.error("The file {} does not exist", filename);
    }
    return findValue;
  }

  public static boolean containsRegexp(String text, String regexp) {
    return Pattern.compile(regexp).matcher(text).find();
  }

  public static long countsRegexp(String filename, String regexp) throws IOException {
    String text = read(filename);
    return Pattern.compile(regexp).matcher(text).results().count();
  }

  public static String replaceInFile(String filename, String regexp, String replacement) throws IOException {
    String text = read(filename);
    return replace(text, regexp, replacement);
  }

  public static String replace(String text, String regexp, String replacement) {
    return Pattern.compile(regexp).matcher(text).replaceAll(replacement);
  }

  public static boolean isPosix() {
    return FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
  }

  public static Optional<String> detectEndOfLine(String filename) throws IOException {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {
      char previousChar = 0;
      char currentChar;
      int read;
      while ((read = reader.read()) != -1) {
        currentChar = (char) read;
        if (currentChar == '\n') {
          if (previousChar == '\r') {
            return Optional.of(CRLF);
          }
          return Optional.of(LF);
        }
        previousChar = currentChar;
      }
    }
    return Optional.empty();
  }

  public static String normalizeEndOfLine(String text) {
    return transformEndOfLine(text, LF);
  }

  public static String transformEndOfLine(String text, String toEndOfLine) {
    if (LF.equals(toEndOfLine)) {
      return text.replace(CRLF, LF);
    }
    return text.replaceAll("([^\\r])\\n", "$1" + toEndOfLine);
  }

  public static void appendLines(String filePath, List<String> lines) throws IOException {
    Files.write(Path.of(filePath), lines, StandardOpenOption.APPEND);
  }

  public static void rename(String source, String sourceFilename, String destinationFilename) throws IOException {
    Files.move(getPathOf(source, sourceFilename), getPathOf(source, destinationFilename));
  }

  public static byte[] convertFileInTmpToByte(String path) throws IOException {
    try (InputStream inputStream = new FileInputStream(getPath(tmpDir(), path))) {
      return inputStream.readAllBytes();
    }
  }
}
