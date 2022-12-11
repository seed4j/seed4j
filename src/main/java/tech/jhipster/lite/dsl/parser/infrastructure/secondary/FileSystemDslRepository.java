package tech.jhipster.lite.dsl.parser.infrastructure.secondary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.dsl.DslLexer;
import tech.jhipster.lite.dsl.DslParser;
import tech.jhipster.lite.dsl.common.domain.DslProperties;
import tech.jhipster.lite.dsl.parser.domain.DslApplication;
import tech.jhipster.lite.dsl.parser.domain.DslRepository;
import tech.jhipster.lite.dsl.parser.domain.JhipsterDslFileIdentifiant;
import tech.jhipster.lite.dsl.parser.domain.JhipsterDslFileToSave;
import tech.jhipster.lite.error.domain.Assert;

@Repository
public class FileSystemDslRepository implements DslRepository {

  private static final String SLASH = "/";

  public JhipsterDslFileIdentifiant createDslFile(JhipsterDslFileToSave file) {
    Assert.notNull("file", file);
    try {
      File fileToSave;
      if (SystemUtils.IS_OS_UNIX) {
        FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------"));
        fileToSave = Files.createTempFile("jhipster_dsl", file.getName(), attr).toFile();
      } else {
        fileToSave = Files.createTempFile("jhipster_dsl", file.getName()).toFile();
        boolean read = fileToSave.setReadable(true, true);
        boolean write = fileToSave.setWritable(true, true);
        boolean executable = fileToSave.setExecutable(true, true);
        if (!(read && write && executable)) {
          throw new DslFileIOException("Error with permission");
        }
      }

      fileToSave.deleteOnExit();

      try (FileOutputStream outputStream = new FileOutputStream(fileToSave)) {
        outputStream.write(file.getBytes());
      }
      return new JhipsterDslFileIdentifiant(fileToSave.toString());
    } catch (IOException e) {
      throw new DslFileIOException("Error when create temporary dsl file");
    }
  }

  public DslApplication parseDsl(JhipsterDslFileIdentifiant file, DslProperties properties) {
    Assert.notNull("file", file);
    Assert.notNull("properties", properties);
    try {
      InputStream fileStream = getInputStream(file.id());
      DslLexer lexer = new DslLexer(CharStreams.fromStream(fileStream));

      lexer.removeErrorListeners();
      lexer.addErrorListener(ThrowingErrorListener.INSTANCE);
      DslParser parser = new DslParser(new CommonTokenStream(lexer));
      parser.setErrorHandler(new ExceptionErrorStrategy());

      DslFileVisitor.FileVisitor fileVisitor = new DslFileVisitor.FileVisitor();
      DslApplication.DslApplicationBuilder builder = fileVisitor.visitFile_(parser.file_());

      if (!properties.projectFolder().get().isBlank()) {
        builder.overrideProjectFolder(properties.projectFolder());
      }
      return builder.build();
    } catch (IOException e) {
      throw new DslFileIOException("Error when parse temporary  dsl file");
    }
  }

  private InputStream getInputStream(String path) {
    Assert.field("path", path).notBlank();

    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path.replace("\\", SLASH));
    if (stream == null) {
      try {
        stream = Files.newInputStream(Paths.get(path));
      } catch (IOException e) {
        throw new DslFileIOException("Error when load dsl file");
      }
    }
    return stream;
  }
}
